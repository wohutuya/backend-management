package com.hutuya.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hutuya.project.constant.RolesConstant;
import com.hutuya.project.entity.Roles;
import com.hutuya.project.entity.UserRoles;
import com.hutuya.project.exception.BizException;
import com.hutuya.project.mapper.RolesMapper;
import com.hutuya.project.mapper.UserRolesMapper;
import com.hutuya.project.mapper.UsersMapper;
import com.hutuya.project.model.dto.userrole.UserRolesUpdateDTO;
import com.hutuya.project.model.vo.role.RolesVO;
import com.hutuya.project.response.Result;
import com.hutuya.project.service.UserRolesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-03
 */
@Service
public class UserRolesServiceImpl extends ServiceImpl<UserRolesMapper, UserRoles> implements UserRolesService {

    @Resource
    private UsersMapper usersMapper;

    @Resource
    private RolesMapper rolesMapper;

    /**
     * 分配角色
     */
    @Override
    public Result assignRoles(UserRolesUpdateDTO dto) {

        if (dto.getUserId() == null) {
            throw new BizException("用户ID不能为空");
        }

        // 查询用户是否存在
        if (usersMapper.selectById(dto.getUserId()) == null) {
            throw new BizException("用户不存在");
        }

        // 删除用户所有现有角色
        this.lambdaUpdate()
                .eq(UserRoles::getUserId, dto.getUserId())
                .remove();


        // 解析 roleIds 字符串 → 列表
        List<Long> roleIdList = Arrays.stream(dto.getRoleIds().split(","))
                .filter(StrUtil::isNotBlank)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        // 循环添加新角色
        for (Long roleId : roleIdList) {
            if (this.lambdaQuery().eq(UserRoles::getUserId, dto.getUserId()).eq(UserRoles::getRoleId, roleId).exists()) {
                continue; // 跳过已存在
            }

            UserRoles newRelation = new UserRoles();
            newRelation.setUserId(dto.getUserId());
            newRelation.setRoleId(roleId);
            this.save(newRelation);
        }
        return Result.success("分配成功");
    }

    /**
     * 根据用户ID获取角色列表
     */
    @Override
    public Result getRolesByUserId(Long userId) {
        if (userId == null) {
            throw new BizException("用户ID不能为空");
        }

        // 1. 查询用户关联的所有角色ID
        List<Long> roleIdList = this.lambdaQuery()
                .eq(UserRoles::getUserId, userId)
                .list()
                .stream()
                .map(UserRoles::getRoleId)
                .collect(Collectors.toList());

        // 2. 如果没有角色，直接返回空列表
        if (CollectionUtil.isEmpty(roleIdList)) {
            return Result.success(Collections.emptyList());
        }

        // 3. 根据角色ID查询角色详情
        LambdaQueryWrapper<Roles> wrapper = Wrappers.lambdaQuery(Roles.class)
                .in(Roles::getId, roleIdList)
                .eq(Roles::getStatus, RolesConstant.STATUS_ENABLED)  // 只查启用的角色
                .eq(Roles::getIsDeleted, RolesConstant.NOT_DELETED);

        List<Roles> rolesList = rolesMapper.selectList(wrapper);

        // 4. 转为 VO
        List<RolesVO> resultVo = rolesList.stream()
                .map(role -> {
                    RolesVO vo = new RolesVO();
                    BeanUtil.copyProperties(role, vo);
                    vo.setStatusDesc(RolesConstant.isEnabled(role.getStatus()) ? "启用" : "禁用");
                    return vo;
                })
                .collect(Collectors.toList());
        return Result.success(resultVo);
    }
}
