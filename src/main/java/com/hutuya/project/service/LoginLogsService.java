package com.hutuya.project.service;

import com.hutuya.project.entity.LoginLogs;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 登录日志表 服务类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
public interface LoginLogsService extends IService<LoginLogs> {

    void saveLog(LoginLogs loginLogs);

}
