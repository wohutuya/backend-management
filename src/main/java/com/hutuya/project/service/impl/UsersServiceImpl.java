package com.hutuya.project.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutuya.project.constant.BizCodeEnum;
import com.hutuya.project.constant.UsersConstant;
import com.hutuya.project.entity.Users;
import com.hutuya.project.exception.BizException;
import com.hutuya.project.mapper.UsersMapper;
import com.hutuya.project.model.dto.user.UpdatePasswordDTO;
import com.hutuya.project.model.dto.user.UpdateStatusDTO;
import com.hutuya.project.model.dto.user.UserLoginDTO;
import com.hutuya.project.model.dto.user.UsersAddDTO;
import com.hutuya.project.model.dto.user.UsersPageDTO;
import com.hutuya.project.model.dto.user.UsersUpdateDTO;
import com.hutuya.project.model.vo.user.UsersVO;
import com.hutuya.project.response.Result;
import com.hutuya.project.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    /**
     * 登录
     */
    @Override
    public Result login(UserLoginDTO dto) {
        // 1. 根据用户名查询用户
        Users user = this.lambdaQuery()
                .eq(Users::getUsername, dto.getUsername())
                .eq(Users::getIsDeleted, UsersConstant.NOT_DELETED)
                .one();

        if (user == null) {
            throw new BizException("用户名或密码错误");
        }

        // 2. 校验密码（BCrypt 强加密）
        if (!BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
            throw new BizException("用户名或密码错误");
        }

        // 3. 校验账号状态
        if (UsersConstant.STATUS_DISABLED == user.getStatus()) {
            throw new BizException("账号已被禁用，请联系管理员");
        }

        // 4. 执行 Sa-Token 登录（用 user.id 作为登录标识）
        StpUtil.login(user.getId());

        // 5. 组装返回
        UsersVO vo = BeanUtil.copyProperties(user, UsersVO.class);
        vo.setToken(StpUtil.getTokenValue());

        return Result.success(vo);
    }

    /**
     * 添加用户
     */
    @Transactional(rollbackFor = Exception.class)
    public Result add(UsersAddDTO dto) {
        // 用户名查重
        if (this.lambdaQuery().eq(Users::getUsername, dto.getUsername()).exists()) {
            throw new BizException(BizCodeEnum.USERNAME_EXISTS);
        }

        // 邮箱查重（非空时）
        if (StrUtil.isNotBlank(dto.getEmail()) && this.lambdaQuery().eq(Users::getEmail, dto.getEmail()).exists()) {
            throw new BizException(BizCodeEnum.EMAIL_EXISTS);
        }

        // 添加数据
        Users user = new Users();
        //设置密码
        BeanUtil.copyProperties(dto, user, "password");
        user.setPassword(BCrypt.hashpw(dto.getPassword()));
        //设置基础信息
        user.setStatus(UsersConstant.STATUS_ENABLED);
        user.setIsSuperAdmin(UsersConstant.NOT_SUPER_ADMIN);
        user.setUserType(UsersConstant.USER);
        boolean saved = this.save(user);
        return saved ? Result.success("添加成功") : Result.fail(BizCodeEnum.FAILED.getMsg());
    }

    /**
     * 修改用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    public Result updateInfo(UsersUpdateDTO dto) {
        Users exist = this.getById(dto.getId());
        if (exist == null) {
            throw new BizException("用户不存在");
        }

        // 用户名查重
        if (StrUtil.isNotBlank(dto.getUsername()) && !dto.getUsername().equals(exist.getUsername())) {
            if (this.lambdaQuery().eq(Users::getUsername, dto.getUsername()).ne(Users::getId, dto.getId()).exists()) {
                throw new BizException(BizCodeEnum.USERNAME_EXISTS);
            }
        }

        // 邮箱查重
        if (StrUtil.isNotBlank(dto.getEmail()) && !dto.getEmail().equals(exist.getEmail())) {
            if (this.lambdaQuery().eq(Users::getEmail, dto.getEmail()).ne(Users::getId, dto.getId()).exists()) {
                throw new BizException(BizCodeEnum.EMAIL_EXISTS);
            }
        }

        // 更新
        Users update = new Users();
        BeanUtil.copyProperties(dto, update);
        boolean updated = this.updateById(update);
        return updated ? Result.success("更新成功") : Result.fail(BizCodeEnum.FAILED.getMsg());
    }

    /**
     * 删除用户
     */
    @Transactional(rollbackFor = Exception.class)
    public Result delete(Long id) {

        // 基本校验
        Users user = this.getById(id);
        if (user == null) {
            throw new BizException("用户不存在");
        }

        // 逻辑删除
        boolean deleted = this.lambdaUpdate()
                .eq(Users::getId, id)
                .set(Users::getIsDeleted, UsersConstant.DELETED)
                .update();

        return deleted ? Result.success("删除成功") : Result.fail(BizCodeEnum.FAILED.getMsg());
    }

    /**
     * 用户列表
     */
    public Result pageList(UsersPageDTO dto) {
        IPage<Users> page = new Page<>(dto.getPageNo(), dto.getPageSize());

        LambdaQueryWrapper<Users> wrapper = Wrappers.lambdaQuery(Users.class)
                .like(StrUtil.isNotBlank(dto.getName()), Users::getRealName, dto.getName())
                .eq(dto.getStatus() != null, Users::getStatus, dto.getStatus())
                .eq(Users::getIsDeleted, UsersConstant.NOT_DELETED)
                .orderByDesc(Users::getId);

        IPage<Users> result = this.page(page, wrapper);

        IPage<Object> resultVo = result.convert(entity -> {
            UsersVO vo = new UsersVO();
            BeanUtil.copyProperties(entity, vo);
            return vo;
        });
        return Result.success(resultVo);
    }

    /**
     * 修改用户状态
     */
    @Override
    public Result updateStatus(UpdateStatusDTO dto) {
        // 状态合法性
        if (UsersConstant.STATUS_DISABLED != dto.getStatus() && UsersConstant.STATUS_ENABLED != dto.getStatus()) {
            throw new BizException("状态值不合法");
        }

        Users user = this.getById(dto.getId());
        if (user == null) {
            throw new BizException("账号不存在");
        }

        // 超级管理员不能被禁用
        if (UsersConstant.SUPER_ADMIN == user.getIsSuperAdmin()) {
            throw new BizException("不允许禁用超级管理员");
        }

        //修改
        boolean update = this.lambdaUpdate()
                .eq(Users::getId, user)
                .set(Users::getStatus, user)
                .update();
        return update ? Result.success("修改成功") : Result.fail(BizCodeEnum.FAILED.getMsg());
    }

    /**
     * 修改密码
     */
    @Override
    public Result updatePassword(UpdatePasswordDTO dto) {
        Users user = this.getById(dto.getUserId());
        if (user == null) {
            throw new BizException("账号不存在");
        }

        // 校验旧密码
        if (!BCrypt.checkpw(dto.getOldPassword(), user.getPassword())) {
            throw new BizException("旧密码错误");
        }

        // 新密码加密保存
        boolean update = this.lambdaUpdate()
                .eq(Users::getId, dto.getUserId())
                .set(Users::getPassword, BCrypt.hashpw(dto.getNewPassword()))
                .update();
        return update ? Result.success("修改成功") : Result.fail(BizCodeEnum.FAILED.getMsg());
    }
}
