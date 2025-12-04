package com.hutuya.project.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.hutuya.project.exception.BizException;
import com.hutuya.project.response.Result;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * <p>
 * 全局异常处理器
 * </p>
 *
 * @author hutuya
 * @since 2025-12-03
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 1. 校验异常（@Valid + @Validated） */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Result handleValidException(Exception e) {
        String msg;
        if (e instanceof MethodArgumentNotValidException ex) {
            msg = ex.getBindingResult().getFieldErrors().stream()
                    .map(error -> error.getField() + error.getDefaultMessage())
                    .collect(Collectors.joining("; "));
        } else {
            msg = ((BindException) e).getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining("; "));
        }
        log.warn("参数校验失败: {}", msg);
        return Result.fail("参数错误：" + msg);
    }

    /** 2. 单参数校验异常（@PathVariable + @RequestParam） */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result handleConstraintViolation(ConstraintViolationException e) {
        String msg = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));
        log.warn("单参数校验失败: {}", msg);
        return Result.fail("参数错误：" + msg);
    }

    /** 3. Sa-Token 权限异常 */
    @ExceptionHandler(NotLoginException.class)
    public Result handleNotLogin(NotLoginException e) {
        log.warn("未登录: {}", e.getMessage());
        return Result.fail("请先登录");
    }

    @ExceptionHandler(NotPermissionException.class)
    public Result handleNotPermission(NotPermissionException e) {
        log.warn("无权限: {}", e.getMessage());
        return Result.fail("权限不足");
    }

    @ExceptionHandler(NotRoleException.class)
    public Result handleNotRole(NotRoleException e) {
        log.warn("无角色: {}", e.getMessage());
        return Result.fail("角色不足");
    }

    /** 4. 业务异常（推荐你抛 BizException） */
    @ExceptionHandler(BizException.class)
    public Result handleBizException(BizException e) {
        log.warn("业务异常: {}", e.getMessage());
        return Result.fail(e.getMessage());
    }

    /** 5. 未知异常兜底 */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("系统异常:{}", e.getMessage());
        return Result.fail(e.getMessage());
    }
}