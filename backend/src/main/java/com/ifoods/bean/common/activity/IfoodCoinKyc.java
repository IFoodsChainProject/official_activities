package com.ifoods.bean.common.activity;

import java.io.Serializable;


/**
 * 
 * @author zhenghui.li
 * @date 2018年4月13日
 */
public class IfoodCoinKyc implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String email;
    private String password;
    private String ethAddress;
    private String neoAddress;
    private String ethAmount;
    private String telegram;
    
    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public IfoodCoinKyc() {
        super();
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEthAddress() {
        return ethAddress;
    }

    public void setEthAddress(String ethAddress) {
        this.ethAddress = ethAddress;
    }

    public String getNeoAddress() {
        return neoAddress;
    }

    public void setNeoAddress(String neoAddress) {
        this.neoAddress = neoAddress;
    }

    public String getEthAmount() {
        return ethAmount;
    }

    public void setEthAmount(String ethAmount) {
        this.ethAmount = ethAmount;
    }

    public IfoodCoinKyc(String email, String password, String ethAddress, String neoAddress, String ethAmount,
            String telegram) {
        super();
        this.email = email;
        this.password = password;
        this.ethAddress = ethAddress;
        this.neoAddress = neoAddress;
        this.ethAmount = ethAmount;
        this.telegram = telegram;
    }

    @Override
    public String toString() {
        return "IfoodCoinKyc [id=" + id + ", email=" + email + ", password=" + password + ", ethAddress=" + ethAddress
                + ", neoAddress=" + neoAddress + ", ethAmount=" + ethAmount + ", telegram=" + telegram + "]";
    }
    
}
