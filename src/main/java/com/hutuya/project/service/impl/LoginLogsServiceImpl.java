package com.hutuya.project.service.impl;

import com.hutuya.project.entity.LoginLogs;
import com.hutuya.project.mapper.LoginLogsMapper;
import com.hutuya.project.service.LoginLogsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录日志表 服务实现类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
@Service
public class LoginLogsServiceImpl extends ServiceImpl<LoginLogsMapper, LoginLogs> implements LoginLogsService {

    @Async
    @Override
    public void saveLog(LoginLogs loginLogs) {
        this.save(loginLogs);
    }
}
