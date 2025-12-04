package com.hutuya.project.aspect;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.context.model.SaRequest;
import cn.hutool.core.util.StrUtil;
import com.hutuya.project.entity.LoginLogs;
import com.hutuya.project.service.LoginLogsService;
import com.hutuya.project.utils.IpUtil;
import com.hutuya.project.utils.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

/**
 * <p>
 * 登录日志表 aop类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
@Aspect
@Component
@Slf4j
public class LoginLogAspect {

    private final LoginLogsService loginLogsService;

    public LoginLogAspect(LoginLogsService loginLogsService) {
        this.loginLogsService = loginLogsService;
    }

    // 系统用户登录 两个接口
    @Pointcut("execution(* com.hutuya.project.controller.UsersController.login(..))")
    public void loginPointcut() {
    }

    @Around("loginPointcut()")
    public Object aroundLogin(ProceedingJoinPoint point) throws Throwable {

        // 直接使用 Sa-Token 官方推荐的 SaRequest（不依赖 HttpServletRequest）
        SaRequest saRequest = SaHolder.getRequest();

        // ==================== 基础信息 ====================
        String ip = IpUtil.getIpAddr((saRequest)); // 已适配 SaRequest 版
        String userAgent = saRequest.getHeader("User-Agent");

        // 从参数里提取 username（兼容两种登录DTO）
        String username = null;
        Object[] args = point.getArgs();
        if (args.length > 0 && args[0] != null) {
            try {
                Field field = args[0].getClass().getDeclaredField("username");
                field.setAccessible(true);
                username = (String) field.get(args[0]);
            } catch (Exception e) {
                log.debug("无法提取用户名，忽略", e);
            }
        }

        LoginLogs log = new LoginLogs();
        log.setIp(ip);
        log.setUsername(username);
        log.setBrowser(UserAgentUtil.getBrowser(userAgent));
        log.setOs(UserAgentUtil.getOs(userAgent));
        log.setCreatedAt(LocalDateTime.now());

        try {
            // ==================== 执行登录方法 ====================
            Object result = point.proceed();

            // ==================== 登录成功 ====================
            Long userId = null;
            if (StpUtil.isLogin()) {
                userId = Long.valueOf(StpUtil.getLoginIdAsString());
            }

            log.setUserId(userId);
            log.setStatus((byte) 1);
            log.setMsg("登录成功");

            loginLogsService.saveLog(log);
            return result;

        } catch (Exception e) {
            // ==================== 登录失败 ====================
            log.setUserId(null);
            log.setStatus((byte) 0);

            String msg = e.getMessage();
            if (StrUtil.isNotBlank(msg)) {
                if (msg.contains("密码") || msg.contains("错误")) {
                    msg = "密码错误";
                } else if (msg.contains("禁用")) {
                    msg = "账号已被禁用";
                } else if (msg.contains("不存在")) {
                    msg = "用户名不存在";
                } else {
                    msg = "登录失败";
                }
            } else {
                msg = "登录失败";
            }
            log.setMsg(msg);

            loginLogsService.saveLog(log);
            throw e; // 继续抛出，让全局异常处理
        }
    }
}