package com.hutuya.project.service;

import com.hutuya.project.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutuya.project.model.dto.user.UpdatePasswordDTO;
import com.hutuya.project.model.dto.user.UpdateStatusDTO;
import com.hutuya.project.model.dto.user.UserLoginDTO;
import com.hutuya.project.model.dto.user.UsersAddDTO;
import com.hutuya.project.model.dto.user.UsersPageDTO;
import com.hutuya.project.model.dto.user.UsersUpdateDTO;
import com.hutuya.project.response.Result;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
public interface UsersService extends IService<Users> {

    //用户登录
    Result login(UserLoginDTO dto);

    // 添加用户
    Result add(UsersAddDTO dto);

    // 删除用户
    Result delete(Long id);

    // 修改用户
    Result updateInfo(UsersUpdateDTO dto);

    // 获取用户列表
    Result pageList(UsersPageDTO dto);

    //修改用户状态
    Result updateStatus(UpdateStatusDTO dto);

    //修改用户密码
    Result updatePassword(UpdatePasswordDTO dto);
}
