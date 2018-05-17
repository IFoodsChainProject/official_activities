package com.ifoods.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author zhenghui.li
 * @date 2018年4月14日
 */
@Slf4j
public class CommonTools {

    /**
     * 验证邮箱格式
     * 
     * 邮箱格式不正确返回false;
     */
    public static boolean emailFormat(String email) {
        boolean tag = true;
        if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
            tag = false;
        }
        return tag;
    }
    
    /**
     * 手机号验证
     * 
     * 格式不正确返回false
     */
    public static boolean telphoneFormat(String telphone) {
        boolean tag = true;
        if (!telphone.matches( "^[1][3,4,5,7,8][0-9]{9}$")) {
            tag = false;
        }
        return tag;
    }
    
    /**
     * 微信号, 验证
     * 暂时没有验证通过
     * @param args
     * ^[a-z_\d]+$
     */
    public static boolean wechatFormat(String telphone) {
        boolean tag = false;
        if (telphone.matches("^[a-z_d]+$")) {
            tag = true;
        }
        return tag;
    }
    
    /**
     * 整数验证
     * 没有验证 033 这种情况;
     * @param args
     */
    public static boolean intFormat(String intStr) {
        boolean tag = false;
        if(intStr.matches("^[0-9]+$")) {
            tag = true;
        }
        return tag;
    }
    
    /**
     * 验证 是否是正常的数字, 包括, 整数和小数
     * @param numberFormat
     * @return
     */
    public static boolean numberFormat(String numberFormat) {
        boolean tag = false;
        if(StringUtils.isEmpty(numberFormat)) {
            return tag;
        }
        //一位数字
        if(numberFormat.length() == 1 && numberFormat.matches("^[0-9]+$")) {
            return true;
        }
        
        //多位整数, 不包含 小数点 ., 不能以0开头
        if(numberFormat.length() > 1 
                && numberFormat.indexOf(".") < 0 
                && !numberFormat.startsWith("0")
                && numberFormat.matches("^[0-9]+$")) {
            return true;
        }
        
        /*
         * 包含小数点;
         *  小数点左侧 -> 没有任何数字
         *  小数点右侧 -> 可以是任何非空数字
         */
        
        /*
         * 包含小数点;
         *  小数点左侧 -> 不能是0开头, 
         *  小数点右侧 -> 可以是任何非空数字
         */
        if(numberFormat.length() > 1 && numberFormat.indexOf(".") > 0 && numberFormat.matches("[1-9]")) {
            //
            
        }
        
        return tag;
    }
    
    /**
     * 将emoji表情转换 utf8的格式
     * @param args
     */
    public static String conventEmoji2UTF(String str) {
        if(org.apache.commons.lang3.StringUtils.isBlank(str)) {
            return str;
        }
        String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        
        while(matcher.find()) {
            try {
                matcher.appendReplacement(
                sb,
                "[["
                + URLEncoder.encode(matcher.group(1),
                "UTF-8") + "]]");
            } catch(UnsupportedEncodingException e) {
                log.error("conventEmoji2UTF error. str={}, error={}", str, e);
            }
        }
        matcher.appendTail(sb);
        log.debug("emojiConvert {} to {} len: {}", str, sb.toString(), sb.length());
        return sb.toString();
    }
    
    /**
     * 将utf8字符串- 转换成 emoji 表情
     * @param args
     */
    public static String conventUTF2Emoji(String str) {
        if(org.apache.commons.lang3.StringUtils.isBlank(str)) {
            return str;
        }
        String patternString = "\\[\\[(.*?)\\]\\]";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);

        StringBuffer sb = new StringBuffer();
        while(matcher.find()) {
            try {
                matcher.appendReplacement(sb,
                URLDecoder.decode(matcher.group(1), "UTF-8"));
            } catch(Exception e) {
                log.error("conventUTF2Emoji error. str={}, error={}", str, e);
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    
    
    public static void main(String[] args) {
        String str = "18930294054";
        String s = "0";
        //System.out.println(telphoneFormat(str));
        System.out.println(intFormat(s));
    }
    
    
    
    
}
