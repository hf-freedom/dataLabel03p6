package com.example.datalabel.config;

import com.example.datalabel.interceptor.SIApiInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SIWebConfig implements WebMvcConfigurer {
    
    @Autowired
    private SIApiInterceptor siApiInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(siApiInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/menu/user", "/login", "/logout", "/main", "/", "/api/api/**");
    }
}
