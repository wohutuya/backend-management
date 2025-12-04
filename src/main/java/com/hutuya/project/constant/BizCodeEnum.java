package com.hutuya.project.constant;
/**
 * <p>
 * 业务通用 枚举类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
public enum BizCodeEnum {

    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),

    USERNAME_EXISTS(1001, "用户名已存在"),
    EMAIL_EXISTS(1002, "邮箱已被注册"),
    REGISTER_FAILED(1003, "注册失败，请稍后重试");

    private final int code;
    private final String msg;

    BizCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() { return code; }
    public String getMsg() { return msg; }
}
