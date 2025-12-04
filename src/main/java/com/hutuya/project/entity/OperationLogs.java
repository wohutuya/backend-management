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
 * 操作日志表
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
@Getter
@Setter
@TableName("operation_logs")
public class OperationLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID，主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 操作模块名称
     */
    @TableField("module")
    private String module;

    /**
     * 操作类型，如新增、删除、修改等
     */
    @TableField("operation")
    private String operation;

    /**
     * HTTP请求方法，如GET、POST等
     */
    @TableField("method")
    private String method;

    /**
     * 请求URL
     */
    @TableField("url")
    private String url;

    /**
     * 请求参数，JSON格式
     */
    @TableField("params")
    private String params;

    /**
     * 操作状态：0-失败, 1-成功
     */
    @TableField("status")
    private Byte status;

    /**
     * 错误信息，操作失败时记录
     */
    @TableField("error_msg")
    private String errorMsg;

    /**
     * 操作时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
