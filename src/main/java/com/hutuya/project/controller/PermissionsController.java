package com.hutuya.project.controller;

import com.hutuya.project.model.dto.permission.PermissionsAddDTO;
import com.hutuya.project.model.dto.permission.PermissionsPageDTO;
import com.hutuya.project.model.dto.permission.PermissionsUpdateDTO;
import com.hutuya.project.response.Result;
import com.hutuya.project.service.PermissionsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
@Tag(name = "权限管理")
@RestController
@RequestMapping("/permissions")
public class PermissionsController {

    private final PermissionsService permissionsService;

    public PermissionsController(PermissionsService permissionsService) {
        this.permissionsService = permissionsService;
    }

    @Operation(summary = "新增权限")
    @PostMapping("/add")
    public Result add(@RequestBody PermissionsAddDTO dto) {
        return permissionsService.add(dto);
    }

    @Operation(summary = "权限分页列表")
    @PostMapping("/pageList")
    public Result pageList(@RequestBody PermissionsPageDTO dto) {
        return permissionsService.pageList(dto);
    }

    @Operation(summary = "修改权限信息")
    @PostMapping("/updateInfo")
    public Result updateInfo(@RequestBody PermissionsUpdateDTO dto) {
        return permissionsService.updateInfo(dto);
    }

    @Operation(summary = "删除权限信息")
    @PostMapping("/delete")
    public Result delete(Long id) {
        return permissionsService.delete(id);
    }
}
