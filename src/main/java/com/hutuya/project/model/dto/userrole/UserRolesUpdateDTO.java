package com.hutuya.project.model.dto.userrole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "用户角色关联修改参数")
public class UserRolesUpdateDTO implements Serializable {

    @NotNull(message = "关联ID不能为空")
    @Schema(description = "用户ID")
    private Long userId;

    @NotNull(message = "角色id不能为空")
    @Schema(description = "角色ID组")
    private String roleIds;
}
