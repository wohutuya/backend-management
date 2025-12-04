package com.hutuya.project.model.vo.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 管理员账号返回前端 VO（严格对应数据库字段，不做额外扩展）
 *
 * @author hutuya
 * @since 2025-12-01
 */
@Data
@Schema(description = "管理员账号信息返回对象")
public class AdminPageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "管理员ID")
    private Long id;

    @Schema(description = "管理员用户名")
    private String username;

    @Schema(description = "管理员昵称")
    private String nickname;

    @Schema(description = "电子邮箱")
    private String email;

    @Schema(description = "手机号码")
    private String phone;

    @Schema(description = "账号状态：0-禁用, 1-启用")
    private Byte status;

    @Schema(description = "是否超级管理员：0-否, 1-是")
    private Byte isSuperAdmin;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedAt;

}