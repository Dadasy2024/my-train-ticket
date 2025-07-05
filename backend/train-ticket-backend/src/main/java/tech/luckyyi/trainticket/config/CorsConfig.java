package tech.luckyyi.trainticket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局跨域配置，允许前端本地开发环境访问后端接口
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 所有接口都允许跨域
                .allowedOriginPatterns("*") // 允许所有来源（开发阶段用，生产建议指定域名）
                .allowedMethods("*")        // 允许所有HTTP方法
                .allowedHeaders("*")        // 允许所有请求头
                .allowCredentials(true);    // 允许携带cookie
    }
}
