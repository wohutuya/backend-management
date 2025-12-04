package com.hutuya.project.model.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "权限修改参数")
public class PermissionsUpdateDTO implements Serializable {

    @NotNull(message = "权限ID不能为空")
    @Schema(description = "权限ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Size(max = 100, message = "权限名称最多100字符")
    @Schema(description = "权限名称（可选，不传不改）")
    private String name;

    @Size(max = 100, message = "权限标识最多100字符")
    @Schema(description = "权限标识（可选，不传不改）")
    private String code;

    @Size(max = 255, message = "描述最多255字符")
    @Schema(description = "权限描述（可选）")
    private String description;

    @Min(value = 1, message = "类型必须是1-3")
    @Max(value = 3, message = "类型必须是1-3")
    @Schema(description = "类型：1-菜单, 2-按钮, 3-API（可选）")
    private Byte type;

    @Schema(description = "父权限ID（可选）")
    private Long parentId;

    @Size(max = 255, message = "路径最多255字符")
    @Schema(description = "菜单路径（可选）")
    private String path;

    @Min(0) @Max(1)
    @Schema(description = "状态：0-禁用, 1-启用（可选）")
    private Byte status;
}
