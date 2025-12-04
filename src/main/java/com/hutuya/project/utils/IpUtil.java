package com.hutuya.project.utils;

import cn.dev33.satoken.context.model.SaRequest;
import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * ip地址工具类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
public class IpUtil {

    public static String getIpAddr(SaRequest request) {
        if (request == null) {
            return "unknown";
        }

        // 优先级 header 数组（标准 IP 解析链）
        String[] headers = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR"
        };

        String ip = null;
        for (String header : headers) {
            ip = request.getHeader(header);
            if (StrUtil.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                break;  // 找到有效 IP 立即返回
            }
        }

        // 兜底：如果所有 header 都没 IP，用 unknown
        if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = "unknown";
        }

        // 处理多代理 IP（取第一个）
        if (StrUtil.isNotBlank(ip) && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        // 本地 IPv6 转 127.0.0.1
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }

        return ip;
    }

}