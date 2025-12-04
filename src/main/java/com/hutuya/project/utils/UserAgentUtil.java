package com.hutuya.project.utils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * <p>
 * 获取登录信息的工具类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
public class UserAgentUtil {

    /**
     * 获取浏览器名称
     */
    public static String getBrowser(String userAgentStr) {
        if (userAgentStr == null) {
            return "未知";
        }
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
        Browser browser = userAgent.getBrowser();
        return browser == null ? "未知" : browser.getName();
    }

    /**
     * 获取操作系统名称
     */
    public static String getOs(String userAgentStr) {
        if (userAgentStr == null) {
            return "未知";
        }
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
        OperatingSystem os = userAgent.getOperatingSystem();
        return os == null ? "未知" : os.getName();
    }
}