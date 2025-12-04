package com.hutuya.project.model.dto.permission;

import com.hutuya.project.model.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "权限分页查询参数")
public class PermissionsPageDTO extends BaseDTO {

    @Schema(description = "权限类型：1-菜单, 2-按钮, 3-API")
    private Byte type;

    @Schema(description = "状态：0-禁用, 1-启用")
    private Byte status;

    @Schema(description = "父权限ID")
    private Long parentId;
}
