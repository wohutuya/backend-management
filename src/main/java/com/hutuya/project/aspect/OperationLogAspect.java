package com.hutuya.project.aspect;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hutuya.project.constant.OperationLogsConstant;
import com.hutuya.project.entity.OperationLogs;
import com.hutuya.project.service.OperationLogsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <p>
 * 操作日志表 aop类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
@Aspect
@Component
@Slf4j
public class OperationLogAspect {

    private final OperationLogsService operationLogsService;

    public OperationLogAspect(OperationLogsService operationLogsService) {
        this.operationLogsService = operationLogsService;
    }

    // 定义切点：拦截所有 controller 方法（根据你的包调整）
    @Pointcut("execution(public * com.hutuya.project.controller..*.*(..))")
    public void logPointCut() {}

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        OperationLogs operationLog = new OperationLogs();

        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            operationLog.setMethod(request.getMethod());
            operationLog.setUrl(request.getRequestURI());

            // 参数 JSON 化
            String params = JSONUtil.toJsonStr(point.getArgs());
            operationLog.setParams(StrUtil.isBlank(params) ? "{}" : params);
        }

        // 操作模块：从类名或 URL 推导（e.g., "AdminUsers"）
        String module = point.getSignature().getDeclaringTypeName().replace("com.hutuya.project.controller.", "");
        operationLog.setModule(module);

        // 操作类型：方法名 (e.g., "addUser")
        operationLog.setOperation(point.getSignature().getName());

        // 用户ID：从 Sa-Token 获取
        if (StpUtil.isLogin()) {
            operationLog.setUserId(Long.parseLong(StpUtil.getLoginIdAsString()));
        }

        // 执行方法
        Object result = null;
        try {
            result = point.proceed();
            operationLog.setStatus(OperationLogsConstant.STATUS_SUCCESS);
            operationLog.setErrorMsg("");
        } catch (Exception e) {
            operationLog.setStatus(OperationLogsConstant.STATUS_FAILED);
            operationLog.setErrorMsg(e.getMessage());
            throw e;  // 继续抛出异常，让全局处理器处理
        } finally {
            // 保存日志
            operationLogsService.saveLog(operationLog);
        }

        return result;
    }
}