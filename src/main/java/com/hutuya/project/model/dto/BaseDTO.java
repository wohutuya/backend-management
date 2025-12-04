// 文件路径：com.hutuya.project.model.dto.BaseDTO.java
package com.hutuya.project.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * 通用查询 DTO（分页 + 模糊搜索）
 * 所有需要列表查询的接口 DTO 都可以继承这个类
 *
 * @author hutuya
 * @since 2025-12-01
 */
@Data
@Schema(description = "通用分页查询参数")
public class BaseDTO {

    @Schema(description = "页码，从1开始", example = "1")
    @Min(value = 1, message = "页码最小值为1")
    private Integer pageNo = 1;

    @Schema(description = "每页条数，最大100", example = "10")
    @Min(value = 1, message = "每页条数最小值为1")
    @Max(value = 100, message = "每页条数最大值为100")
    private Integer pageSize = 10;

    @Schema(description = "模糊搜索关键词（如用户名、昵称、手机号等）", example = "张三")
    private String name;
}