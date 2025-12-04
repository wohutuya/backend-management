package com.hutuya.project.service;

import com.hutuya.project.entity.Permissions;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutuya.project.model.dto.permission.PermissionsAddDTO;
import com.hutuya.project.model.dto.permission.PermissionsPageDTO;
import com.hutuya.project.model.dto.permission.PermissionsUpdateDTO;
import com.hutuya.project.response.Result;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
public interface PermissionsService extends IService<Permissions> {

    //新增权限
    Result add(PermissionsAddDTO dto);

    //修改权限
    Result updateInfo(PermissionsUpdateDTO dto);

    //逻辑删除权限
    Result delete(Long id);

    //分页查询权限列表
    Result pageList(PermissionsPageDTO dto);

}
