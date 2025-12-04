package com.hutuya.project.constant;

/**
 * <p>
 * 系统用户表 (users) 专用常量
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
public final class UsersConstant {

    private UsersConstant() {
    }

    /**
     * ==================== 账号状态 status ====================
     */
    public static final byte STATUS_DISABLED = 0;   // 禁用
    public static final byte STATUS_ENABLED = 1;   // 启用

    /**
     * ==================== 是否为超级管理员 is_super_admin ====================
     */
    public static final byte NOT_SUPER_ADMIN = 0;       // 非超级管理员
    public static final byte SUPER_ADMIN = 1;       // 超级管理员

    /**
     * ==================== 逻辑删除标志 is_deleted ====================
     */
    public static final byte NOT_DELETED = 0;       // 未删除
    public static final byte DELETED = 1;       // 已删除

    /**
     * ==================== 用户类型 user_type ====================
     */
    public static final String ADMIN = "admin";       // 管理员
    public static final String USER = "user";       // 普通用户
}
