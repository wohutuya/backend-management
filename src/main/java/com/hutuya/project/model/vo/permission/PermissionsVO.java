package com.hutuya.project.model.vo.permission;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "权限返回对象")
public class PermissionsVO {

    @Schema(description = "权限ID")
    private Long id;

    @Schema(description = "权限名称")
    private String name;

    @Schema(description = "权限标识")
    private String code;

    @Schema(description = "权限描述")
    private String description;

    @Schema(description = "权限类型：1-菜单, 2-按钮, 3-API")
    private Byte type;

    @Schema(description = "父权限ID")
    private Long parentId;

    @Schema(description = "菜单路径")
    private String path;

    @Schema(description = "状态：0-禁用, 1-启用")
    private Byte status;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}