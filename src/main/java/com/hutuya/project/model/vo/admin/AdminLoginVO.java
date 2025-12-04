package com.hutuya.project.model.vo.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "管理员登录成功返回信息")
public class AdminLoginVO {

    @Schema(description = "登录token")
    private String token;

    @Schema(description = "管理员ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "是否超级管理员")
    private Boolean isSuperAdmin;
}