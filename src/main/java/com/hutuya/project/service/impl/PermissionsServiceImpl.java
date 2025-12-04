package com.hutuya.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutuya.project.constant.BizCodeEnum;
import com.hutuya.project.constant.PermissionsConstant;
import com.hutuya.project.entity.Permissions;
import com.hutuya.project.exception.BizException;
import com.hutuya.project.mapper.PermissionsMapper;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.hutuya.project.model.dto.permission.PermissionsAddDTO;
import com.hutuya.project.model.dto.permission.PermissionsPageDTO;
import com.hutuya.project.model.dto.permission.PermissionsUpdateDTO;
import com.hutuya.project.model.vo.permission.PermissionsVO;
import com.hutuya.project.response.Result;
import com.hutuya.project.service.PermissionsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
@Service
public class PermissionsServiceImpl extends ServiceImpl<PermissionsMapper, Permissions> implements PermissionsService {

    /**
     * 新增权限
     */
    @Transactional(rollbackFor = Exception.class)
    public Result add(PermissionsAddDTO dto) {

        // 1. 名称查重
        if (this.lambdaQuery().eq(Permissions::getName, dto.getName()).exists()) {
            throw new BizException("名称不能重复");
        }

        // 2. 标识查重
        if (this.lambdaQuery().eq(Permissions::getCode, dto.getCode()).exists()) {
            throw new BizException("标识不能重复");
        }

        // 3. 组装实体
        Permissions permissions = new Permissions();
        BeanUtil.copyProperties(dto, permissions);
        boolean saved = this.save(permissions);
        return saved ? Result.success("添加成功") : Result.fail(BizCodeEnum.FAILED.getMsg());
    }

    /**
     * 修改权限
     */
    @Transactional(rollbackFor = Exception.class)
    public Result updateInfo(PermissionsUpdateDTO dto) {

        Permissions exist = this.getById(dto.getId());
        if (exist == null || PermissionsConstant.isDeleted(exist.getIsDeleted())) {
            throw new BizException("权限不存在");
        }

        // 1. 名称查重
        if (this.lambdaQuery().eq(Permissions::getName, dto.getName()).exists()) {
            throw new BizException("名称不能重复");
        }

        // 2. 标识查重
        if (this.lambdaQuery().eq(Permissions::getCode, dto.getCode()).exists()) {
            throw new BizException("标识不能重复");
        }

        // 安全更新
        Permissions permissions = BeanUtil.copyProperties(dto, Permissions.class);

        boolean updated = this.updateById(permissions);
        return updated ? Result.success("修改成功") : Result.fail(BizCodeEnum.FAILED.getMsg());
    }

    /**
     * 逻辑删除权限
     */
    @Transactional(rollbackFor = Exception.class)
    public Result delete(Long id) {
        Permissions exist = this.getById(id);
        if (exist == null) {
            throw new BizException("权限不存在");
        }

        boolean deleted = this.lambdaUpdate()
                .eq(Permissions::getId, id)
                .set(Permissions::getIsDeleted, PermissionsConstant.DELETED)
                .update();
        return deleted ? Result.success("删除成功") : Result.fail(BizCodeEnum.FAILED.getMsg());
    }

    /**
     * 分页查询权限列表
     */
    public Result pageList(PermissionsPageDTO dto) {

        IPage<Permissions> page = new Page<>(dto.getPageNo(), dto.getPageSize());

        LambdaQueryWrapper<Permissions> wrapper = Wrappers.lambdaQuery(Permissions.class)
                .like(StrUtil.isNotBlank(dto.getName()), Permissions::getName, dto.getName())
                .like(StrUtil.isNotBlank(dto.getName()), Permissions::getCode, dto.getName())
                .eq(dto.getType() != null, Permissions::getType, dto.getType())
                .eq(dto.getStatus() != null, Permissions::getStatus, dto.getStatus())
                .eq(dto.getParentId() != null, Permissions::getParentId, dto.getParentId())
                .eq(Permissions::getIsDeleted, PermissionsConstant.NOT_DELETED)
                .orderByDesc(Permissions::getId);

        IPage<Permissions> result = this.page(page, wrapper);

        IPage<PermissionsVO> resultVo = result.convert(entity -> {
            PermissionsVO vo = new PermissionsVO();
            BeanUtil.copyProperties(entity, vo);
            return vo;
        });
        return Result.success(resultVo);
    }

}