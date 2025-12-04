package com.hutuya.project.service;

import com.hutuya.project.entity.OperationLogs;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 操作日志表 服务类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
public interface OperationLogsService extends IService<OperationLogs> {

    // 保存日志
    void saveLog(OperationLogs operationLog);

}
