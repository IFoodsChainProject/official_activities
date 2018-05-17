package com.ifoods.config.mvc;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.ifoods.interceptor.ForbidInterceptor;
import com.ifoods.interceptor.TestSecondInterceptor;


/**
 * 
 * @author zhenghui.li
 * @date 2018年4月12日
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {
    
    @Autowired
    private ForbidInterceptor forbidInterceptor;

    @Autowired
    private TestSecondInterceptor testSecondInterceptor;

    /**
     * 添加 -> 自定义参数解析器
     *//*
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(argumentResolver);

    }*/
    
    /**
     * 拦截器
     * 
     * 有两个, 
     *  1. passportInterceptor, 单点登录, 的校验拦截;
     *  2. atomicInterceptor, 不知道什么拦截器;
     * 
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*registry.addInterceptor(passportInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/shopcenter/**").excludePathPatterns("/platformSupplier/**").excludePathPatterns("/platformSupplierInfo/**")
                .excludePathPatterns("/file/**");*/
        registry.addInterceptor(forbidInterceptor).addPathPatterns("/candy/**").addPathPatterns("/0505/**");
        //registry.addInterceptor(testSecondInterceptor).addPathPatterns("/**");
    }
    /**
     * 静态文件过滤
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/app/**").addResourceLocations("classpath:/WEB-INF/app/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/WEB-INF/static/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/WEB-INF/");
    }

    /**
     * 视图层
     * @return
     */
    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setSuffix(".html");
        return resolver;
    }

    /**
     * 编码
     * @return
     */
    @Bean
    public Filter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter =new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }
}
