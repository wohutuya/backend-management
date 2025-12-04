package com.hutuya.project.constant;

/**
 * <p>
 * 权限表 (permissions) 专用常量类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
public final class PermissionsConstant {

    private PermissionsConstant() {}

    /** ==================== 状态 status ==================== */
    public static final byte STATUS_DISABLED = 0;   // 禁用
    public static final byte STATUS_ENABLED  = 1;   // 启用

    /** ==================== 权限类型 type ==================== */
    public static final byte TYPE_MENU  = 1;       // 菜单
    public static final byte TYPE_BUTTON = 2;      // 按钮
    public static final byte TYPE_API   = 3;       // API

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

    public static boolean isMenu(Byte type) {
        return TYPE_MENU == (type != null ? type : -1);
    }

    public static boolean isButton(Byte type) {
        return TYPE_BUTTON == (type != null ? type : -1);
    }

    public static boolean isApi(Byte type) {
        return TYPE_API == (type != null ? type : -1);
    }

    public static boolean isDeleted(Byte isDeleted) {
        return DELETED == (isDeleted != null ? isDeleted : -1);
    }
}
