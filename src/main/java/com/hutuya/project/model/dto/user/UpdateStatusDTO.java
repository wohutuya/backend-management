package com.hutuya.project.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "修改密码请求")
public class UpdateStatusDTO {

    @NotNull(message = "用户ID不能为空")
    private Long id;

    @NotNull(message = "状态不能为空")
    private Byte status; // 0禁用 1启用
}
