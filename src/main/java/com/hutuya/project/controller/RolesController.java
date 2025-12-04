package com.hutuya.project.controller;

import com.hutuya.project.model.dto.role.RolesAddDTO;
import com.hutuya.project.model.dto.role.RolesPageDTO;
import com.hutuya.project.model.dto.role.RolesUpdateDTO;
import com.hutuya.project.response.Result;
import com.hutuya.project.service.RolesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
@Tag(name = "角色管理")
@RestController
@RequestMapping("/roles")
public class RolesController {


    private final RolesService rolesService;

    public RolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @Operation(summary = "新增角色")
    @PostMapping("/add")
    public Result add(@RequestBody RolesAddDTO dto) {
        return rolesService.add(dto);
    }

    @Operation(summary = "角色分页列表")
    @PostMapping("/pageList")
    public Result pageList(@RequestBody RolesPageDTO dto) {
        return rolesService.pageList(dto);
    }

    @Operation(summary = "修改角色信息")
    @PostMapping("/updateInfo")
    public Result updateInfo(@RequestBody RolesUpdateDTO dto) {
        return rolesService.updateInfo(dto);
    }

    @Operation(summary = "删除角色信息")
    @PostMapping("/delete")
    public Result delete(Long id) {
        return rolesService.delete(id);
    }

    @Operation(summary = "获取所有角色列表")
    @GetMapping("/getALL")
    public Result listAllEnabled() {
        return rolesService.listAllEnabled();
    }
}
