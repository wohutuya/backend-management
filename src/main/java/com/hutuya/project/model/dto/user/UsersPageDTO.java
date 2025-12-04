package com.hutuya.project.model.dto.user;

import com.hutuya.project.model.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统用户分页查询参数")
public class UsersPageDTO extends BaseDTO {

    @Schema(description = "账号状态：0-禁用, 1-启用")
    private Byte status;

    @Schema(description = "真实姓名模糊搜索")
    private String realName;
}