package com.ifoods.bean.common.activity;

import java.io.Serializable;

/**
 * 
 * @author zhenghui.li
 * @date 2018年4月17日
 */
public class Activity0505 implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private String email;
    private String telephone;
    private String wechatName;
    private String ethAddress;
    private String createTime;
    
    public String getWechatName() {
        return wechatName;
    }
    public void setWechatName(String wechatName) {
        this.wechatName = wechatName;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getEthAddress() {
        return ethAddress;
    }
    public void setEthAddress(String ethAddress) {
        this.ethAddress = ethAddress;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public Activity0505() {
        super();
        
    }
    public Activity0505(String name, String email, String telephone, String ethAddress) {
        super();
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.ethAddress = ethAddress;
    }
    @Override
    public String toString() {
        return "Activity0505 [id=" + id + ", name=" + name + ", email=" + email + ", telephone=" + telephone
                + ", wechatName=" + wechatName + ", ethAddress=" + ethAddress + ", createTime=" + createTime + "]";
    }
    
}
