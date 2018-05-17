package com.ifoods.bean.common;

/**
 */
public enum CodeMsg {

    // --
    SUCCESS("0000", "SUCCESS"),
    /*参数不能为空*/
    PARAMS_ISEMPTY("0001", "PARAMS_ISEMPTY"),
    /*邮箱不能为空*/
    EMAIL_ISEMPTY("0002", "EMAIL_ISEMPTY"),
    /*服务器异常*/
    SYSTEM_ERROR("0003", "SYSTEM_ERROR"),
    /*邮箱格式错误*/
    MAIL_FORMATE("0004", "MAIL_FORMATE"),
    /*邮箱验证码发送失败*/
    MAIL_CODE_SEND_FAIL("0005", "MAIL_CODE_SEND_FAIL"),
    /*邮箱验证码错误*/
    MAIL_CODE_FAIL("0006", "MAIL_CODE_FAIL"),
    /*该邮箱已存在*/
    MAIL_EXIST("0007", "MAIL_EXIST"),
    /*保存失败,请稍候重试*/
    SAVE_ERROR("0008", "SAVE_ERROR"),
    /** 该邮箱下未查询到相关用户信息*/
    EMAIL_NOT_EXIST("0009", "EMAIL_NOT_EXIST"),
    /** 用户名或密码错误*/
    LOGIN_FAIL("0010", "LOGIN_FAIL"),
    /** 您所在的地区被限制访问此网页 */
    LIMIT_ADDRESS("0011", "LIMIT_ADDRESS"),
    
    
    /** kyc activity */
    /*获得邮箱验证码失败, 请稍候重试*/
    GET_EMAIL_CODE_FAIL("10001", "GET_EMAIL_CODE_FAIL"),
    /*邮箱或密码错误*/
    EMAIL_PWD_FAIL("10002", "EMAIL_PWD_FAIL"),
    /*数量超出限制*/
    ETHAMOUNT_LENGTH_BEYOND("10003", "ETHAMOUNT_LENGTH_BEYOND"),
    
    ;

    private String key;
    private String value;

    private CodeMsg(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String value() {
        return value;
    }

    public String key() {
        return key;
    }

    public static CodeMsg getErrorTypeEnumByKey(String key) {
        for (CodeMsg status : CodeMsg.values()) {
            if (status.key.equals(key)) {
                return status;
            }
        }
        return null;
    }

    public static String getValueByKey(String key) {
        String value = "";
        CodeMsg errorTypeEnum = getErrorTypeEnumByKey(key);
        if (null != errorTypeEnum) {
            value = errorTypeEnum.value();
        }
        return value;
    }
}
