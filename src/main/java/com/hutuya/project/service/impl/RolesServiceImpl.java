package com.hutuya.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutuya.project.constant.BizCodeEnum;
import com.hutuya.project.constant.RolesConstant;
import com.hutuya.project.entity.Roles;
import com.hutuya.project.exception.BizException;
import com.hutuya.project.mapper.RolesMapper;
import com.hutuya.project.model.dto.role.RolesAddDTO;
import com.hutuya.project.model.dto.role.RolesPageDTO;
import com.hutuya.project.model.dto.role.RolesUpdateDTO;
import com.hutuya.project.model.vo.role.RolesVO;
import com.hutuya.project.response.Result;
import com.hutuya.project.service.RolesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
@Service
public class RolesServiceImpl extends ServiceImpl<RolesMapper, Roles> implements RolesService{

    /**
     * 新增角色
     */
    @Transactional(rollbackFor = Exception.class)
    public Result add(RolesAddDTO dto) {

        // 名称查重
        if (this.lambdaQuery().eq(Roles::getName, dto.getName()).exists()) {
            throw new BizException("名称已经存在");
        }

        // 组装实体
        Roles roles = new Roles();
        BeanUtil.copyProperties(dto, roles);
        boolean saved = this.save(roles);
        return saved ? Result.success("添加成功") : Result.fail(BizCodeEnum.FAILED.getMsg());
    }

    /**
     * 修改角色
     */
    @Transactional(rollbackFor = Exception.class)
    public Result updateInfo(RolesUpdateDTO dto) {

        Roles exist = this.getById(dto.getId());
        if (exist == null || RolesConstant.isDeleted(exist.getIsDeleted())) {
            throw new BizException("角色不存在");
        }

        // 名称唯一性
        if (StrUtil.isNotBlank(dto.getName()) && !dto.getName().equals(exist.getName())) {
            if (this.lambdaQuery().eq(Roles::getName, dto.getName()).ne(Roles::getId, dto.getId()).exists()) {
                throw new BizException("名称已经存在");
            }
        }

        // 更新
        Roles roles = BeanUtil.copyProperties(dto, Roles.class);

        boolean updated = this.updateById(roles);
        return updated ? Result.success("修改成功") : Result.fail(BizCodeEnum.FAILED.getMsg());
    }

    /**
     * 逻辑删除角色
     */
    @Transactional(rollbackFor = Exception.class)
    public Result delete(Long id) {
        Roles exist = this.getById(id);
        if (exist == null) {
            throw new BizException("角色不存在");
        }

        boolean update = this.lambdaUpdate()
                .eq(Roles::getId, id)
                .set(Roles::getIsDeleted, RolesConstant.DELETED)
                .update();
        return update ? Result.success("删除成功") : Result.fail(BizCodeEnum.FAILED.getMsg());
    }

    /**
     * 获取所有启用的角色
     */
    @Override
    public Result listAllEnabled() {
        List<RolesVO> result = this.lambdaQuery()
                .eq(Roles::getStatus, RolesConstant.STATUS_ENABLED)
                .eq(Roles::getIsDeleted, RolesConstant.NOT_DELETED)
                .list()
                .stream()
                .map(entity -> {
                    RolesVO vo = new RolesVO();
                    BeanUtil.copyProperties(entity, vo);
                    return vo;
                })
                .collect(Collectors.toList());
        return Result.success(result);
    }

    /**
     * 分页查询角色列表
     */
    public Result pageList(RolesPageDTO dto) {

        IPage<Roles> page = new Page<>(dto.getPageNo(), dto.getPageSize());

        LambdaQueryWrapper<Roles> wrapper = Wrappers.lambdaQuery(Roles.class)
                .like(StrUtil.isNotBlank(dto.getName()), Roles::getName, dto.getName())
                .eq(dto.getStatus() != null, Roles::getStatus, dto.getStatus())
                .eq(Roles::getIsDeleted, RolesConstant.NOT_DELETED)
                .orderByDesc(Roles::getId);

        IPage<Roles> result = this.page(page, wrapper);

        IPage<RolesVO> resultVo = result.convert(entity -> {
            RolesVO vo = new RolesVO();
            BeanUtil.copyProperties(entity, vo);
            vo.setStatusDesc(RolesConstant.isEnabled(entity.getStatus()) ? "启用" : "禁用");
            return vo;
        });
        return Result.success(resultVo);
    }
}
