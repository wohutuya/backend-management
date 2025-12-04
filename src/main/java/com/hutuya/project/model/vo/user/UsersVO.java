package com.hutuya.project.model.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "系统用户返回对象")
public class UsersVO {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "电子邮箱")
    private String email;

    @Schema(description = "手机号码")
    private String phone;

    @Schema(description = "登录token")
    private String token;

    @Schema(description = "账号状态：0-禁用, 1-启用")
    private Byte status;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
