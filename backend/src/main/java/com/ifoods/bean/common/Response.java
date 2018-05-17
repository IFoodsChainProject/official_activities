package com.ifoods.bean.common;

import com.alibaba.fastjson.JSON;
import com.ifoods.utils.ThreadLocalManager;
import com.ifoods.utils.language.LanguageManager;

/**
 * 
 * @author zhenghui.li
 * @date 2018年4月17日
 */
public class Response {
    /**
     * 返回码
     */
    private String code;

    /**
     * 返回描述
     */
    private String msg;

    /**
     * 返回请求数据
     */
    private Object data;

    public Response() {
        code = CodeMsg.SUCCESS.key();
        msg = CodeMsg.SUCCESS.value();
        data = "";
    }
    
    /**
     * @param CodeMsg
     */
    public Response(CodeMsg codeMsg) {
        code = codeMsg.key();
        msg = getMsgByType(codeMsg.value());
        data = "";
    }

    public Response(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * @param string
     */
    public Response(String msg) {
        this.code = CodeMsg.SUCCESS.key();
        this.msg = msg;
        this.data = "";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
    
    public void setCode(CodeMsg codeMsg) {
        this.code = codeMsg.key();
        this.msg = getMsgByType(codeMsg.value());
    }
    
    /**
     * @param value
     * @return
     * 返回国际化的值
     */
    private String getMsgByType(String key) {
        return LanguageManager.getValue(key, ThreadLocalManager.getConsoleContext().getLanguageType());
    }
}
