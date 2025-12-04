package com.hutuya.project.controller;

import com.hutuya.project.model.dto.user.*;
import com.hutuya.project.response.Result;
import com.hutuya.project.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/users")
public class UsersController {


    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO dto) {
        return usersService.login(dto);
    }

    @Operation(summary = "用户分页列表")
    @PostMapping("/pageList")
    public Result register(@RequestBody UsersPageDTO dto) {
        return usersService.pageList(dto);
    }

    @Operation(summary = "新增用户")
    @PostMapping("/register")
    public Result register(@RequestBody UsersAddDTO dto) {
        return usersService.add(dto);
    }

    @Operation(summary = "修改用户信息")
    @PostMapping("/updateInfo")
    public Result register(@RequestBody UsersUpdateDTO dto) {
        return usersService.updateInfo(dto);
    }

    @Operation(summary = "删除用户信息")
    @GetMapping("/deleteById")
    public Result deleteById(Long id) {
        return usersService.delete(id);
    }

    @Operation(summary = "修改用户状态")
    @PostMapping("/updateStatus")
    public Result updateStatus(@RequestBody UpdateStatusDTO dto) {
        return usersService.updateStatus(dto);
    }

    @Operation(summary = "修改用户密码")
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody UpdatePasswordDTO dto) {
        return usersService.updatePassword(dto);
    }
}
