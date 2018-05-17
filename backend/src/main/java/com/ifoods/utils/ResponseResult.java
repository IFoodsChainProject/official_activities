package com.ifoods.utils;

import java.io.Serializable;

/**
 * 
 * @author zhenghui.li
 * @date 2018年4月9日
 */
public class ResponseResult implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int code;
    private String data;
    
    public ResponseResult() {
        super();
        
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "ResponseResult [code=" + code + ", data=" + data + "]";
    }
    
}
