package com.hutuya.project.model.dto.rolepermission;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "角色权限分配参数")
public class RolePermissionsUpdateDTO {

    @NotNull(message = "角色ID不能为空")
    @Schema(description = "角色ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long roleId;

    @Schema(description = "权限ID字符串，如'1,2,3'（为空时清空权限）")
    private String permissionIds;
}
