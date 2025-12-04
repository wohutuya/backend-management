package com.hutuya.project.constant;

/**
 * <p>
 * 操作日志表 (operation_logs) 专用常量类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
public final class OperationLogsConstant {

    private OperationLogsConstant() {}

    /** ==================== 操作状态 status ==================== */
    public static final byte STATUS_FAILED = 0;   // 失败
    public static final byte STATUS_SUCCESS = 1;  // 成功

    /** ==================== 常用判断方法 ==================== */
    public static boolean isSuccess(Byte status) {
        return STATUS_SUCCESS == (status != null ? status : -1);
    }

    public static boolean isFailed(Byte status) {
        return STATUS_FAILED == (status != null ? status : -1);
    }
}