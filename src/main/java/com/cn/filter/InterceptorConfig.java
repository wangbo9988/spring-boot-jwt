package com.cn.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description 注册拦截器
 * @Author Wangbo
 * @Date 2019/11/21
 * @Version V1.0
 **/
//配置类上添加了注解@Configuration，标明了该类是一个配置类并且会将该类作为一个SpringBean添加到IOC容器内
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //  设置拦截路径和放行路径
        registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/", "/login", "/css/**", "/js/**", "/fonts/**", "icons-reference", "/img/**", "/vendor/**");

    }

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

}
