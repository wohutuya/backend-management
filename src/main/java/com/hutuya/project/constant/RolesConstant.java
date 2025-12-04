package com.hutuya.project.constant;

/**
 * <p>
 * 角色表 (roles) 专用常量类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
public final class RolesConstant {

    private RolesConstant() {}

    /** ==================== 状态 status ==================== */
    public static final byte STATUS_DISABLED = 0;   // 禁用
    public static final byte STATUS_ENABLED  = 1;   // 启用

    /** ==================== 逻辑删除 is_deleted ==================== */
    public static final byte NOT_DELETED = 0;      // 未删除
    public static final byte DELETED     = 1;      // 已删除

    /** ==================== 常用判断方法 ==================== */
    public static boolean isEnabled(Byte status) {
        return STATUS_ENABLED == (status != null ? status : -1);
    }

    public static boolean isDisabled(Byte status) {
        return STATUS_DISABLED == (status != null ? status : -1);
    }

    public static boolean isDeleted(Byte isDeleted) {
        return DELETED == (isDeleted != null ? isDeleted : -1);
    }
}