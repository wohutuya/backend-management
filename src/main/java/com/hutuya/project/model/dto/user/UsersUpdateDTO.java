package com.hutuya.project.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "系统用户修改参数")
public class UsersUpdateDTO {

    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Size(min = 4, max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "用户名只能包含字母、数字、下划线和中划线")
    @Schema(description = "用户名（可选）")
    private String username;

    @Size(max = 50)
    @Schema(description = "真实姓名（可选）")
    private String realName;

    @Email(message = "邮箱格式不正确")
    @Size(max = 100)
    @Schema(description = "电子邮箱（可选）")
    private String email;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @Schema(description = "手机号码（可选）")
    private String phone;

}
