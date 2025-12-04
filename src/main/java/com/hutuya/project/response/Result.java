package com.hutuya.project.response;

import lombok.Data;

import java.time.Instant;

/**
 * <p>
 * 统一API响应类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-03
 */
@Data
public class Result<T> {

    private int code = SUCCESS_CODE;
    private String message = SUCCESS_MESSAGE;
    private T data;
    private long timestamp; // 新增时间戳字段

    private static final int FAIL_CODE = 500;
    private static final int SUCCESS_CODE = 200;

    private static final String SUCCESS_MESSAGE = "success";
    private static final String FAIL_MESSAGES = "fail";

    // 所有构造方法都初始化时间戳
    public Result() {
        this.timestamp = Instant.now().toEpochMilli();
    }

    public Result(boolean isSuccess) {
        this();
        if (!isSuccess) {
            this.code = FAIL_CODE;
            this.message = FAIL_MESSAGES;
        }
    }

    public Result(boolean isSuccess, int code) {
        this();
        if (!isSuccess) {
            this.message = FAIL_MESSAGES;
        }
        this.code = code;
    }

    public Result(boolean isSuccess, String message) {
        this();
        if (!isSuccess) {
            this.code = FAIL_CODE;
        }
        this.message = message;
    }

    public Result(boolean isSuccess, T data) {
        this();
        if (!isSuccess) {
            this.code = FAIL_CODE;
            this.message = FAIL_MESSAGES;
        }
        this.data = data;
    }

    public Result(boolean isSuccess, String message, T data) {
        this();
        if (!isSuccess) {
            this.code = FAIL_CODE;
        }
        this.message = message;
        this.data = data;
    }

    public Result(int code, String message, T data) {
        this();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 静态工厂方法也需要创建新实例以生成时间戳
    public static <T> Result<T> success() {
        return new Result<>(true);
    }

    public static <T> Result<T> fail() {
        return new Result<>(false);
    }

    public static <T> Result<T> success(int code) {
        return new Result<>(true, code);
    }

    public static <T> Result<T> fail(int code) {
        return new Result<>(false, code);
    }

    public static <T> Result<T> success(String message) {
        return new Result<>(true, message);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(false, message);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(true, data);
    }

    public static <T> Result<T> fail(T data) {
        return new Result<>(false, data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(true, message, data);
    }

    public static <T> Result<T> fail(String message, T data) {
        return new Result<>(false, message, data);
    }

    public static <T> Result<T> success(int code, String message, T data) {
        return new Result<>(code, message, data);
    }

    public static <T> Result<T> fail(int code, String message, T data) {
        return new Result<>(code, message, data);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(false, msg);
    }
}