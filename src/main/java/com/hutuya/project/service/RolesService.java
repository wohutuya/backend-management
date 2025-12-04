package com.hutuya.project.service;

import com.hutuya.project.entity.Roles;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutuya.project.model.dto.role.RolesAddDTO;
import com.hutuya.project.model.dto.role.RolesPageDTO;
import com.hutuya.project.model.dto.role.RolesUpdateDTO;
import com.hutuya.project.response.Result;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
public interface RolesService extends IService<Roles> {

    //新增角色
    Result add(RolesAddDTO dto);

    //角色列表
    Result pageList(RolesPageDTO dto);

    //修改角色信息
    Result updateInfo(RolesUpdateDTO dto);

    //删除角色
    Result delete(Long id);

    //查询全部角色
    Result listAllEnabled();
}
