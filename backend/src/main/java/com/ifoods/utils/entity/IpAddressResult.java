package com.ifoods.utils.entity;

import java.io.Serializable;

/**
 * 
 * @author zhenghui.li
 * @date 2018年4月9日
 */
public class IpAddressResult implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String ip;
    private String country;     //国家
    private String region;      //地区, 省
    private String city;        //城市
    private String isp;         //运营商, 阿里巴巴, 电信, 联通
    private String country_id;  //城市id, CN, TW, US
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getIsp() {
        return isp;
    }
    public void setIsp(String isp) {
        this.isp = isp;
    }
    public String getCountry_id() {
        return country_id;
    }
    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }
    public IpAddressResult() {
        super();
    }
    @Override
    public String toString() {
        return "IpAddressResult [ip=" + ip + ", country=" + country + ", region=" + region + ", city=" + city + ", isp="
                + isp + ", country_id=" + country_id + "]";
    }
    
}
