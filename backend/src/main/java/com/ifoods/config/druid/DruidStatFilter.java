package com.ifoods.config.druid;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;

/**
 * 
 * @author zhenghui.li
 * @date 2018年4月19日
 * 拦截druid监控请求
 */
@WebFilter(filterName="druidWebStatFilter",
            urlPatterns="/*",
            initParams={@WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")})// 忽略资源
public class DruidStatFilter extends WebStatFilter{
    
}
