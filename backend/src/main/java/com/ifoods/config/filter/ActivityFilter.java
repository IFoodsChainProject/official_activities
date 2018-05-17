package com.ifoods.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author zhenghui.li
 * @date 2018年4月18日
 * 
 * 活动的过滤器, 允许跨域访问 
 * 
 */
public class ActivityFilter implements Filter {

    private String holdPlace = "*";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse response = (HttpServletResponse) res;
        String origin = req.getHeader("Origin");
        if(origin == null) {
            origin = req.getHeader("Referer");
        }
        origin = holdPlace;
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        response.setHeader("Access-Control-Max-Age", "0");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, X-Auth-Token, X-Auth-DEVICE, i-msg-type");
        response.setHeader("X-Frame-Options", "ALLOW-FROM"); // 允许frame跨域

        //session共享, cookie跨域共享
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
        String requestURI = req.getRequestURI();    // /index
        String requestURL = req.getRequestURL().toString(); //http://localhost/favicon.ico
        String contextPath = requestURL.substring(0, requestURL.length() - requestURI.length());    //http://localhost
        contextPath = contextPath + req.getContextPath();
        request.setAttribute("contextPath", contextPath);
        chain.doFilter(req, res);
    }
}
