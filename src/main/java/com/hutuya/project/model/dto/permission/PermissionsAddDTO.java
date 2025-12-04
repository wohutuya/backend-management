package com.hutuya.project.model.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "权限新增参数")
public class PermissionsAddDTO implements Serializable {

    @NotBlank(message = "权限名称不能为空")
    @Size(max = 100, message = "权限名称最多100字符")
    @Schema(description = "权限名称（唯一）", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "权限标识不能为空")
    @Size(max = 100, message = "权限标识最多100字符")
    @Schema(description = "权限标识（如 admin:user:list, 唯一）", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @Size(max = 255, message = "描述最多255字符")
    @Schema(description = "权限描述")
    private String description;

    @NotNull(message = "权限类型不能为空")
    @Min(value = 1, message = "类型必须是1-3")
    @Max(value = 3, message = "类型必须是1-3")
    @Schema(description = "类型：1-菜单, 2-按钮, 3-API", requiredMode = Schema.RequiredMode.REQUIRED)
    private Byte type;

    @Schema(description = "父权限ID（0为一级权限）")
    private Long parentId = 0L;

    @Size(max = 255, message = "路径最多255字符")
    @Schema(description = "菜单路径（菜单类型必填）")
    private String path;

    @NotNull(message = "状态不能为空")
    @Min(0) @Max(1)
    @Schema(description = "状态：0-禁用, 1-启用", requiredMode = Schema.RequiredMode.REQUIRED)
    private Byte status;
}