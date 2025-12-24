package com.thanglong.shop_giay;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Áp dụng cho mọi đường dẫn
                .allowedOrigins("*") // Cho phép mọi nguồn (Frontend nào cũng vào được)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Cho phép các phương thức này
                .allowedHeaders("*"); // Cho phép mọi header
    }
}