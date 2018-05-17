package com.ifoods.config.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 
 * @author zhenghui.li
 * @date 2018年4月18日
 */
@Configuration
public class FilterConfiguration {

    /**
     * 跨域设置
     * @return
     */
    @Bean
    public FilterRegistrationBean corsFilter() {
        ActivityFilter activityFilter = new ActivityFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("activityFilter");
        registrationBean.setFilter(activityFilter);
        registrationBean.setOrder(1);

        List<String> urlList = new ArrayList<>();
        //urlList.add("/candy/*");
        //urlList.add("/0505/*");
        urlList.add("/kyc/*");
        registrationBean.setUrlPatterns(urlList);

        return registrationBean;
    }
    
    /**
     * 添加语言
     */
    @Bean
    public FilterRegistrationBean langFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        
        LanguageFilter languageFilter = new LanguageFilter();
        registrationBean.setName("languageFilter");
        registrationBean.setFilter(languageFilter);
        registrationBean.setOrder(2);
        
        registrationBean.addUrlPatterns("/*");
        
        return registrationBean;
    }
}
