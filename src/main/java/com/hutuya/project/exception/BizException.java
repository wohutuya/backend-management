package com.hutuya.project.exception;

import com.hutuya.project.constant.BizCodeEnum;
import lombok.Getter;

/**
 * <p>
 * 业务异常（Service 层统一抛这个）
 * </p>
 *
 * @author hutuya
 * @since 2025-12-03
 */
@Getter
public class BizException extends RuntimeException {

    private final int code;

    public BizException(String message) {
        super(message);
        this.code = 500;
    }

    public BizException(BizCodeEnum bizCode) {
        super(bizCode.getMsg());
        this.code = bizCode.getCode();
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }
}