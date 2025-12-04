package com.hutuya.project.controller;

import com.hutuya.project.model.dto.rolepermission.RolePermissionsUpdateDTO;
import com.hutuya.project.response.Result;
import com.hutuya.project.service.RolePermissionsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色权限关联表 前端控制器
 * </p>
 *
 * @author hutuya
 * @since 2025-12-03
 */
@Tag(name = "角色权限管理")
@RestController
@RequestMapping("/rolePermissions")
public class RolePermissionsController {

    private final RolePermissionsService rolePermissionsService;

    public RolePermissionsController(RolePermissionsService rolePermissionsService) {
        this.rolePermissionsService = rolePermissionsService;
    }

    @Operation(summary = "分配权限给角色")
    @PostMapping("/assign")
    public Result assign(@RequestBody RolePermissionsUpdateDTO dto) {
        return rolePermissionsService.assignPermissions(dto);
    }

    @Operation(summary = "根据角色ID获取权限列表")
    @GetMapping("/getPermissionsByRoleId")
    public Result getPermissionsByRoleId(@RequestParam Long roleId) {
        return rolePermissionsService.getPermissionsByRoleId(roleId);
    }
}
