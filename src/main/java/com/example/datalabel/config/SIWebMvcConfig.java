package com.example.datalabel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SIWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private SIApiAuthInterceptor siApiAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(siApiAuthInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/", "/logout", "/forbidden", "/error", "/css/**", "/js/**", "/images/**");
    }
}
