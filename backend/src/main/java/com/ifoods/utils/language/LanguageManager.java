package com.ifoods.utils.language;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ifoods.utils.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author zhenghui.li
 * @date 2018年5月13日
 * 
 * 国际化语言管理
 */
@Slf4j
@Component
public class LanguageManager {

    //private final static String DEFAULT_LANGTYPE = "zh_CN";//默认语言类型
    private final static String BASENAME = "i18n.language";
    private static Map<String, ResourceBundle> languageResource = new HashMap<String, ResourceBundle>();
    
    public static String getValue(String key) {
        return getValue(key, Constants.DEAULT_LANGUAGE);
    }
    
    /**
     * 获取指定key对应的语言资源
     * @param username
     * @param key
     * @return
     */
    public static String getValue(String key, String langType) {
        
        if(StringUtils.isEmpty(key) || StringUtils.isEmpty(langType)) {
            return null;
        }
        
        //默认语言初始化
        ResourceBundle bundle = null;
        if (languageResource.size() == 0) {
            setBundleByLangType(bundle, Constants.DEAULT_LANGUAGE);
        }
        
        bundle = languageResource.get(langType);
        if(bundle == null) {
            setBundleByLangType(bundle, langType);
            bundle = languageResource.get(langType)==null ? 
                    languageResource.get(Constants.DEAULT_LANGUAGE) : languageResource.get(langType);
        }
        try {
            return bundle.getString(key);
        }catch(MissingResourceException e) {
            log.info("key not find key={}", key);
            return null;
        }
    }
    
    private static void setBundleByLangType(ResourceBundle bundle, String langType) {
        if(StringUtils.isEmpty(langType)) {
            return;
        }
        try{
            bundle = ResourceBundle.getBundle(BASENAME, new Locale(langType));
            log.info("add new langType <---> {}", bundle);
            languageResource.put(langType, bundle);
        }catch(Exception e){
            log.error("error to i18n langType={} error={}", langType, e);
        }
    }
    

    public static void main(String[] args) {
        System.out.println(LanguageManager.getValue("NOT_IN_SHARE"));
        System.out.println("------test:"+LanguageManager.getValue("test", "Zh_cN"));
        ResourceBundle bundle = languageResource.get("dd");
        System.out.println(bundle);
    }
}
