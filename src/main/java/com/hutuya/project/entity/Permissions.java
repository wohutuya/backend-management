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
 * 权限表
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
@Getter
@Setter
@TableName("permissions")
public class Permissions implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID，主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 权限名称，唯一
     */
    @TableField("name")
    private String name;

    /**
     * 权限标识符，用于权限验证，唯一
     */
    @TableField("code")
    private String code;

    /**
     * 权限描述
     */
    @TableField("description")
    private String description;

    /**
     * 权限类型：1-菜单, 2-按钮, 3-API
     */
    @TableField("type")
    private Byte type;

    /**
     * 父权限ID，0表示一级权限
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 菜单访问路径
     */
    @TableField("path")
    private String path;

    /**
     * 状态：0-禁用, 1-启用
     */
    @TableField("status")
    private Byte status;

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
