package com.ifoods.controller.activity;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifoods.bean.common.CodeMsg;
import com.ifoods.bean.common.Response;
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
@RequestMapping("/candy")
public class CandyActivityController {
    
    private static final String sessionToken = "xton";
    private static final String cookieToken = "iucu";
    
    @Autowired
    private CandyActivityService candyActivityService;
    
    /**
     * 获得token
     * 
     * 
     */
    @PostMapping("/token")
    @ResponseBody
    public Response getToken(HttpServletRequest request, String token) {
        HttpSession session = request.getSession();
        //String token = session.getAttribute("token").toString();
        if(StringUtils.isEmpty(token)) {
            return new Response(CodeMsg.PARAMS_ISEMPTY);
        }
        log.info("token:{}",token);
        
        String md5 = Md5tools.md5(token);
        session.setAttribute(sessionToken, md5.substring(3, 10));
        
        return new Response();
    }
    
    
    /**
     * 提交, 保存
     * 邮箱校验, 邮箱非空
     * 
     */
    @PostMapping("/save/info")
    @ResponseBody
    public Response save(HttpServletRequest request, String name, String email, String telephone, String ethAddress, String wechatName) {
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
        
        //验证token
        HttpSession session = request.getSession();
        Object sessionObj = session.getAttribute(sessionToken);
        String cookieStr = null;
        if(StringUtils.isEmpty(sessionObj)) {
            log.info("sessionObj is empty: {} ", sessionObj);
            return new Response("90001", "请不要重复提交");
        }
        //验证cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            String cookieName = cookie.getName();
            if(cookieName == cookieToken) {
                cookieStr = cookie.getValue();
            }
        }
        if(StringUtils.isEmpty(cookieStr)) {
            log.info("cookieStr is empty: {} ", sessionObj);
            return new Response("90001", "请不要重复提交");
        }
        
        //token值得判断
        if(!cookieStr.equalsIgnoreCase(sessionObj.toString())) {
            log.info("token is not equals cookieStr: {},  sessionObj:{}", cookieStr, sessionObj);
            return new Response("90001", "请不要重复提交");
        }
        
        log.info("save candy name: {} | email: {}", name, email);
        CandyActivity candyActivity = new CandyActivity();
        candyActivity.setName(name);
        candyActivity.setEmail(email);
        candyActivity.setTelephone(telephone);
        candyActivity.setEthAddress(ethAddress);
        try {
            candyActivityService.save(candyActivity);
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
            List<CandyActivity> candyList = candyActivityService.findAllByEmail(email);
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
