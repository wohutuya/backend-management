package com.hutuya.project.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 登录日志表
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
@Getter
@Setter
@TableName("login_logs")
public class LoginLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID，主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 登录用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 登录用户名
     */
    @TableField("username")
    private String username;

    /**
     * 登录IP地址
     */
    @TableField("ip")
    private String ip;

    /**
     * 浏览器类型
     */
    @TableField("browser")
    private String browser;

    /**
     * 操作系统
     */
    @TableField("os")
    private String os;

    /**
     * 登录状态：0-失败, 1-成功
     */
    @TableField("status")
    private Byte status;

    /**
     * 提示消息
     */
    @TableField("msg")
    private String msg;

    /**
     * 登录时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
