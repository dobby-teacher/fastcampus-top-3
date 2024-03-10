package com.fastcampus.demographql.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/voyager.html")
                .addResourceLocations("classpath:/static/voyager/");

        registry.addResourceHandler("/playground.html")
                .addResourceLocations("classpath:/static/playground/");
    }
}