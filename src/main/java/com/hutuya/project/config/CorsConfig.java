package com.hutuya.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * 跨域 配置类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许跨域访问的路径
                .allowedOrigins("*")  // 允许跨域访问的源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许请求方法
                .allowedHeaders("*") // 允许请求头
                .allowCredentials(false) // 是否允许证书
                .maxAge(3600); // 预检间隔时间
    }

}

