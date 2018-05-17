package com.ifoods.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.ifoods.utils.entity.HttpRequest;
import com.ifoods.utils.entity.IpAddressResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author zhenghui.li
 * @date 2018年4月9日
 */
@Slf4j
public class AddressUtils {
    
    private static String GETADDRESS_URL = "http://ip.taobao.com/service/getIpInfo.php";
    
    /**
     * 根据 域名 获得实际 ip
     * @param hostname
     * @return
     */
    public static String getIpByHost(String hostname) {
        InetAddress address = null;
        try {
            address = InetAddress.getByName(hostname);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("获取失败");
            return null;
        }
        return address.getHostAddress().toString();
    }
    
    /**
     * 以ip地址获取地理位置信息, 如果失败为null
     * @param ipAddress
     * @throws IOException 
     * 
     * */
    public static IpAddressResult getAddressByIp(String ipAddress){
        if(StringUtils.isEmpty(ipAddress)) {
            return null;
        }
        String param = "ip="+ipAddress;
        ResponseResult responseResult = null;
        IpAddressResult addressResult = null;
        try {
            responseResult = JSON.parseObject(HttpRequest.sendGet(GETADDRESS_URL, param), ResponseResult.class);
            addressResult = JSON.parseObject(responseResult.getData(), IpAddressResult.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        return addressResult;
    }
    
    /**
     * 通过host 获得地址
     * @param hostname
     * @return
     */
    public static IpAddressResult getAddressByhost(String hostname) {
        return getAddressByIp(getIpByHost(hostname));
    }
    
    /**
     * TODO 暂时取消
     * 限制 中国, 美国 地址 的请求
     * 不限制返回 - true
     * 限制返回 - false
     */
    public static boolean limitCNAndUS(String ipStr) {
        try {
            IpAddressResult addressResult = getAddressByIp(ipStr);
            String countryId = addressResult.getCountry_id();
            if("cn1".equalsIgnoreCase(countryId) || "us1".equalsIgnoreCase(countryId)) {
                return false;
            }
        }catch(Exception e) {
            log.error("limit ip error. ip={} error={}", ipStr, e);
        }
        return true;
    }
    
    /**
     * 限制 中国, 美国 地址 的请求
     * 不限制返回 - true
     * 限制返回 - false
     * @param request
     */
    public static boolean limitCNAndUS(HttpServletRequest request) {
       return limitCNAndUS(getIp(request));
    }
    
    /**
     * @param request
     * @return
     */
    public static IpAddressResult getAddressByIp(HttpServletRequest request) {
        return getAddressByIp(getIp(request));
    }
    
    /**
     * 返回国家代号, 小写
     * 
     * 中国-cn
     * 美国-us
     * 
     * @param request
     * @return
     */
    public static String getCountryId(HttpServletRequest request) {
        return getCountryId(getIp(request));
    }
    
    /**
     * 返回国家代号, 小写
     * 
     * 中国-cn
     * 美国-us
     * 
     * @param request
     * @return
     */
    public static String getCountryId(String ipStr) {
        return getAddressByIp(ipStr).getCountry_id().toLowerCase();
    }
    
    /**
     * @param request
     * @return
     */
    private static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("x-forwarded-for");  
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;
    }

}
