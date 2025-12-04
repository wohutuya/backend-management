package com.hutuya.project.service.impl;

import com.hutuya.project.entity.OperationLogs;
import com.hutuya.project.mapper.OperationLogsMapper;
import com.hutuya.project.service.OperationLogsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志表 服务实现类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
@Service
public class OperationLogsServiceImpl extends ServiceImpl<OperationLogsMapper, OperationLogs> implements OperationLogsService {

    /*
     * 异步保存日志
     */
    @Override
    @Async
    public void saveLog(OperationLogs operationLog) {
        this.save(operationLog);
    }
}
