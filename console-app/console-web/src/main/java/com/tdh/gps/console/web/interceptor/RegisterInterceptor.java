package com.tdh.gps.console.web.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class RegisterInterceptor extends WebMvcConfigurerAdapter {

    @Bean
    @Order(2)
    public AuthenticationTokenInterceptor authInterceptor() {
        return new AuthenticationTokenInterceptor();
    }

    @Bean
    @Order(1)
    public UsernamePasswordInterceptor usernamePasswordInterceptor() {
        return new UsernamePasswordInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(usernamePasswordInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(authInterceptor()).addPathPatterns("/**");

    }
}
