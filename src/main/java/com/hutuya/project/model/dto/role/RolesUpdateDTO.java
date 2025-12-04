package com.hutuya.project.model.dto.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "角色修改参数")
public class RolesUpdateDTO implements Serializable {

    @NotNull(message = "角色ID不能为空")
    @Schema(description = "角色ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Size(max = 100, message = "角色名称最多100字符")
    @Schema(description = "角色名称（可选，不传不改）")
    private String name;

    @Size(max = 255, message = "描述最多255字符")
    @Schema(description = "角色描述（可选）")
    private String description;

    @Min(0) @Max(1)
    @Schema(description = "状态：0-禁用, 1-启用（可选）")
    private Byte status;
}
