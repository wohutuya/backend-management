package com.hutuya.project.controller;

import com.hutuya.project.model.dto.userrole.UserRolesUpdateDTO;
import com.hutuya.project.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import com.hutuya.project.service.UserRolesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户角色关联表 前端控制器
 * </p>
 *
 * @author hutuya
 * @since 2025-12-03
 */
@Tag(name = "用户角色管理")
@RestController
@RequestMapping("/userRoles")
public class UserRolesController {

    private final UserRolesService userRolesService;

    public UserRolesController(UserRolesService userRolesService) {
        this.userRolesService = userRolesService;
    }

    @Operation(summary = "分配角色给用户")
    @PostMapping("/assign")
    public Result assign(@RequestBody UserRolesUpdateDTO dto) {
        return userRolesService.assignRoles(dto);
    }

    @Operation(summary = "根据用户ID获取角色列表")
    @GetMapping("/getRolesByUserId")
    public Result getRolesByUserId(@RequestParam Long userId) {
        return userRolesService.getRolesByUserId(userId);
    }
}
