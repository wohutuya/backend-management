package com.hutuya.project.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * <p>
 * sa-token 配置类
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handle -> {
            // 所有接口都要登录
            SaRouter.match("/**")
                    .notMatch("/admin/login")  // 排除登录接口
                    .check(StpUtil::checkLogin);
        })).addPathPatterns("/**").excludePathPatterns(
                "/doc.html",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/webjars/**",
                "/favicon.ico",
                "/error"
        );
    }

}