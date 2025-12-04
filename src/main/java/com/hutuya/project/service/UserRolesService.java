package com.hutuya.project.service;

import com.hutuya.project.entity.UserRoles;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutuya.project.model.dto.userrole.UserRolesUpdateDTO;
import com.hutuya.project.response.Result;

/**
 * <p>
 * 用户角色关联表 服务类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-03
 */
public interface UserRolesService extends IService<UserRoles> {

    // 分配角色
    Result assignRoles(UserRolesUpdateDTO dto);

    // 根据用户ID获取角色列表
    Result getRolesByUserId(Long userId);
}
