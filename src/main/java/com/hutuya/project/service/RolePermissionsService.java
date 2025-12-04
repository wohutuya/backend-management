package com.hutuya.project.service;

import com.hutuya.project.entity.RolePermissions;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutuya.project.model.dto.rolepermission.RolePermissionsUpdateDTO;
import com.hutuya.project.response.Result;

/**
 * <p>
 * 角色权限关联表 服务类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-03
 */
public interface RolePermissionsService extends IService<RolePermissions> {

    // 分配权限
    Result assignPermissions(RolePermissionsUpdateDTO dto);

    // 根据角色ID获取权限列表
    Result getPermissionsByRoleId(Long roleId);
}
