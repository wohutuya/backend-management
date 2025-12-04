package com.hutuya.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hutuya.project.constant.PermissionsConstant;
import com.hutuya.project.entity.Permissions;
import com.hutuya.project.entity.RolePermissions;
import com.hutuya.project.entity.Roles;
import com.hutuya.project.exception.BizException;
import com.hutuya.project.mapper.PermissionsMapper;
import com.hutuya.project.mapper.RolePermissionsMapper;
import com.hutuya.project.mapper.RolesMapper;
import com.hutuya.project.model.dto.rolepermission.RolePermissionsUpdateDTO;
import com.hutuya.project.model.vo.permission.PermissionsVO;
import com.hutuya.project.response.Result;
import com.hutuya.project.service.RolePermissionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色权限关联表 服务实现类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-03
 */
@Service
public class RolePermissionsServiceImpl extends ServiceImpl<RolePermissionsMapper, RolePermissions> implements RolePermissionsService {

    @Resource
    private RolesMapper rolesMapper;

    @Resource
    private PermissionsMapper permissionsMapper;

    /**
     * 分配权限
     */
    @Override
    public Result assignPermissions(RolePermissionsUpdateDTO dto) {
        if (dto.getRoleId() == null) {
            throw new BizException("角色ID不能为空");
        }

        // 查询角色是否存在（类似用户校验）
        Roles existRole = rolesMapper.selectById(dto.getRoleId());
        if (existRole == null) {
            throw new BizException("角色不存在或已被删除");
        }

        // 1. 删除角色所有现有权限
        this.lambdaUpdate()
                .eq(RolePermissions::getRoleId, dto.getRoleId())
                .remove();

        // 2. 如果 permissionIds 为空，直接返回（清空权限）
        if (StrUtil.isBlank(dto.getPermissionIds())) {
            return Result.success("权限分配成功");
        }

        // 3. 解析 permissionIds 字符串 → 列表
        List<Long> permissionIdList = Arrays.stream(dto.getPermissionIds().split(","))
                .filter(StrUtil::isNotBlank)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        // 4. 循环添加新权限（防重）
        for (Long permissionId : permissionIdList) {
            if (this.lambdaQuery().eq(RolePermissions::getRoleId, dto.getRoleId()).eq(RolePermissions::getPermissionId, permissionId).exists()) {
                continue; // 跳过已存在
            }

            RolePermissions newRelation = new RolePermissions();
            newRelation.setRoleId(dto.getRoleId());
            newRelation.setPermissionId(permissionId);
            this.save(newRelation);
        }
        return Result.success("权限分配成功");
    }

    /**
     * 根据角色ID获取权限列表
     */
    @Override
    public Result getPermissionsByRoleId(Long roleId) {
        if (roleId == null) {
            throw new BizException("角色ID不能为空");
        }

        // 1. 查询角色关联的所有权限ID
        List<Long> permissionIdList = this.lambdaQuery()
                .eq(RolePermissions::getRoleId, roleId)
                .list()
                .stream()
                .map(RolePermissions::getPermissionId)
                .collect(Collectors.toList());

        // 2. 如果没有权限，直接返回空列表
        if (CollectionUtil.isEmpty(permissionIdList)) {
            return Result.success(Collections.emptyList());
        }

        // 3. 根据权限ID查询权限详情
        LambdaQueryWrapper<Permissions> queryWrapper = Wrappers.lambdaQuery(Permissions.class)
                .in(Permissions::getId, permissionIdList)
                .eq(Permissions::getStatus, PermissionsConstant.STATUS_ENABLED)  // 只查启用的权限
                .eq(Permissions::getIsDeleted, PermissionsConstant.NOT_DELETED);

        List<Permissions> permissionsList = permissionsMapper.selectList(queryWrapper);

        // 4. 转为 VO
        List<PermissionsVO> resultVo = permissionsList.stream()
                .map(permission -> {
                    PermissionsVO vo = new PermissionsVO();
                    BeanUtil.copyProperties(permission, vo);
                    return vo;
                })
                .collect(Collectors.toList());
        return Result.success(resultVo);
    }
}
