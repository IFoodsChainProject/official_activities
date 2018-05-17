package com.ifoods.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author zhenghui.li
 * @date 2018年5月4日
 * 
 * 过期活动拦截器
 */
@Component
@Slf4j
public class ForbidInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //log.info("forbid interceptor");
        log.info("request header: {}", JSON.toJSONString(request.getHeaderNames()));
        return false;
    }
    
}
