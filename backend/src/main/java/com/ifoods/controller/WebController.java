package com.ifoods.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifoods.bean.common.CodeMsg;
import com.ifoods.bean.common.Response;
import com.ifoods.config.redis.RedisKeyConstants;
import com.ifoods.utils.AddressUtils;
import com.ifoods.utils.entity.IpAddressResult;
import com.ifoods.utils.redis.RedisUtils;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class WebController {
    
    /**
     * 测试redis
     */
    @RequestMapping("/testRedis")
    @ResponseBody
    public Response testRedis(String value) {
        log.info("value:{}", value);
        if(value==null)value="3";
        RedisUtils.set(RedisKeyConstants.ACTIVITY_TEST, value, 10);
        
        return new Response(CodeMsg.SUCCESS);
    }
    
    @RequestMapping("/")
    public String index() {
        log.info("to index");
        return "index";
    }
    
    @RequestMapping("/getcountry")
    @ResponseBody
    public IpAddressResult getCountry(HttpServletRequest request) {
        IpAddressResult addressByIp = AddressUtils.getAddressByIp(request);
        log.info("address = {}", addressByIp);
        return addressByIp;
    }

}
