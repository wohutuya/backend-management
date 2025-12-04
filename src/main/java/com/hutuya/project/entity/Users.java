package com.hutuya.project.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
@Getter
@Setter
@TableName("users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID，主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名，唯一
     */
    @TableField("username")
    private String username;

    /**
     * 加密后的密码
     */
    @TableField("password")
    private String password;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 电子邮箱，唯一
     */
    @TableField("email")
    private String email;

    /**
     * 手机号码
     */
    @TableField("phone")
    private String phone;

    /**
     * 账号状态：0-禁用, 1-启用
     */
    @TableField("status")
    private Byte status;

    /**
     * 用户类型：admin-管理员,user-普通用户
     */
    @TableField("user_type")
    private String userType;

    /**
     * 是否超级管理员：0-否, 1-是
     */
    @TableField("is_super_admin")
    private Byte isSuperAdmin;

    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /**
     * 删除标志：0-未删除, 1-已删除
     */
    @TableField("is_deleted")
    @TableLogic
    private Byte isDeleted;
}
