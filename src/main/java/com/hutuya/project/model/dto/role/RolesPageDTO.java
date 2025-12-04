package com.hutuya.project.model.dto.role;

import com.hutuya.project.model.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "角色分页查询参数")
public class RolesPageDTO extends BaseDTO {

    @Schema(description = "状态：0-禁用, 1-启用")
    private Byte status;
}
