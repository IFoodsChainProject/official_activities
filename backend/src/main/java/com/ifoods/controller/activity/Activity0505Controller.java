package com.ifoods.controller.activity;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifoods.bean.common.CodeMsg;
import com.ifoods.bean.common.Response;
import com.ifoods.bean.common.activity.Activity0505;
import com.ifoods.bean.common.activity.CandyActivity;
import com.ifoods.service.CandyActivityService;
import com.ifoods.utils.CommonTools;
import com.ifoods.utils.Md5tools;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author zhenghui.li
 * @date 2018年4月17日
 */
@Controller
@Slf4j
@RequestMapping("/0505")
public class Activity0505Controller {
    
    private static final String random = "iucu";
    private static final String randomPwd = "xton";
    
    @Autowired
    private CandyActivityService candyActivityService;
    
    /**
     * 获得token
     * 
     * 
     */
    @PostMapping("/act")
    @ResponseBody
    public Response getToken(HttpServletRequest request, HttpServletResponse response, String act) {
        HttpSession session = request.getSession();
        //String token = session.getAttribute("token").toString();
        if(StringUtils.isEmpty(act)) {
            return new Response(CodeMsg.PARAMS_ISEMPTY);
        }
        log.info("token:{}",act);
        
        //验证cookie
        String md5 = Md5tools.md5(act);
        session.setAttribute(randomPwd, md5.substring(5, 15));
        log.info("md5={}", session.getAttribute(randomPwd));
        return new Response();
    }
    
    
    /**
     * 提交, 保存
     * 邮箱校验, 邮箱非空
     * iucu未加密
     * 
     */
    @PostMapping("/save/info")
    @ResponseBody
    public Response save(HttpServletRequest request, String name, String email, String telephone, String wechatName, String ethAddress, String iucu , String xton) {
        //非空
        if(StringUtils.isEmpty(email)) {
            log.info("email is empty: {} ", email);
            return new Response(CodeMsg.EMAIL_ISEMPTY);
        }
        //邮箱验证
        boolean emailFormat = CommonTools.emailFormat(email);
        if(!emailFormat) {
            return new Response(CodeMsg.MAIL_FORMATE);
        }
        
        //============= begin ==============
        //cookie
        //验证cookie
        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                log.info("cookie name={} , value={}",cookie.getName(),cookie.getValue());
                if(cookie.getName().equals(random)) {
                    iucu = cookie.getValue();
                }
                if(cookie.getName().equals(randomPwd)) {
                    xton = cookie.getValue();
                }
            }
        }else {
            log.info("cookies is null");
        }
        
        //=============== end ===============
        
        
        if(StringUtils.isEmpty(iucu) || StringUtils.isEmpty(xton)) {
            log.info("token 没有 {}|{} ", iucu, xton);
            return new Response("90001", "请正确提交信息");
        }
        
        String cookieStr = Md5tools.md5(iucu).substring(5, 15);
        log.info("token cookie:{}, xton:{}", cookieStr, xton);
        
        if(!cookieStr.equalsIgnoreCase(xton)) {
            log.info("token is not equals cookieStr: {},  sessionObj:{}", cookieStr,xton);
            return new Response("90001", "请正确提交信息");
        }
        
        /*//验证token
        
        if(StringUtils.isEmpty(cookieStr)) {
            log.info("cookieStr is empty: {} ", cookieStr);
            return new Response("90001", "请正确提交信息");
        }
        
        //token值得判断
        */
        
        
        log.info("save candy name: {} | email: {} | telephone:{}", name, email, telephone);
        Activity0505 activity0505 = new Activity0505();
        activity0505.setName(name);
        activity0505.setEmail(email);
        activity0505.setTelephone(telephone);
        activity0505.setWechatName(wechatName);
        activity0505.setEthAddress(ethAddress);
        try {
            candyActivityService.save(activity0505);
            return new Response("保存成功");
        }catch(Exception e) {
            log.error("save candy info error:{}",e);
            return new Response(CodeMsg.SYSTEM_ERROR);
        }
    }
    
    /**
     * 根据邮箱查询所有的 信息
     */
    @PostMapping("/find/mail")
    @ResponseBody
    public Response findByEmail(String email) {
        //非空
        if(StringUtils.isEmpty(email)) {
            log.info("email is empty: {} ", email);
            return new Response(CodeMsg.EMAIL_ISEMPTY);
        }
        try {
            List<Activity0505> candyList = candyActivityService.findAllByEmail0505(email);
            log.info("find candy email:{} | num:{} ", email, candyList.size());
            Response response = new Response();
            response.setData(candyList);
            
            return response;
        }catch(Exception e) {
            log.error("save candy info error:{}",e);
            return new Response(CodeMsg.SYSTEM_ERROR);
        }
    }
    
}
