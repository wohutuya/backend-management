package com.hutuya.project.model.dto.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "角色新增参数")
public class RolesAddDTO implements Serializable {

    @NotBlank(message = "角色名称不能为空")
    @Size(max = 100, message = "角色名称最多100字符")
    @Schema(description = "角色名称（唯一）", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Size(max = 255, message = "描述最多255字符")
    @Schema(description = "角色描述")
    private String description;

    @NotNull(message = "状态不能为空")
    @Min(0) @Max(1)
    @Schema(description = "状态：0-禁用, 1-启用", requiredMode = Schema.RequiredMode.REQUIRED)
    private Byte status;
}