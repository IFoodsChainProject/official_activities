package com.ifoods.controller.activity;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ifoods.bean.common.CodeMsg;
import com.ifoods.bean.common.Response;
import com.ifoods.bean.common.activity.IfoodCoinKyc;
import com.ifoods.config.redis.RedisKeyConstants;
import com.ifoods.service.CacheService;
import com.ifoods.service.EmailService;
import com.ifoods.service.IfoodCoinKycService;
import com.ifoods.utils.AddressUtils;
import com.ifoods.utils.CommonTools;
import com.ifoods.utils.Md5tools;
import com.ifoods.utils.VerifyCodeUtils;
import com.ifoods.utils.redis.RedisUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author zhenghui.li
 * @date 2018年4月13日
 */
@RestController
@RequestMapping("/kyc")
@Slf4j
public class KycController {

    //public static final String redisTip = "iFoodCache_kyc:";
    
    public static final Integer time_kyc_email_cache = 60*15;
    
    @Autowired
    private EmailService emailService;
    @Autowired
    private IfoodCoinKycService ifoodCoinKycService;
    
    /**
     * 验证kyc 用户, 是否已经存在
     */
    @RequestMapping("/mail/exist")
    @ResponseBody
    public Response mailExist(String email) {
        Response response = new Response();
        //非空
        if(StringUtils.isEmpty(email)) {
            log.error("params is empty: {}", email);
            return new Response(CodeMsg.PARAMS_ISEMPTY);
        }
        int count = ifoodCoinKycService.countByEmail(email);
        if(count > 0) {
            response.setCode(CodeMsg.MAIL_EXIST);
        }
        response.setData(count);
        return response;
    }
    
    /**
     * kyc 首次注册, 发送 邮箱验证码 信息
     * @return
     */
    @RequestMapping("/mail/code")
    @ResponseBody
    public Response sendKycEmailCode(String email, HttpServletRequest request) {
        if(StringUtils.isEmpty(email)) {
            log.info("params is empty: {}", email);
            return new Response(CodeMsg.PARAMS_ISEMPTY);
        }
        //校验ip, 限制cn和us
        if(!AddressUtils.limitCNAndUS(request)) {
            log.info("limit address {}", AddressUtils.getCountryId(request));
            return new Response(CodeMsg.LIMIT_ADDRESS);
        }
        
        //校验邮箱
        boolean emailFormat = CommonTools.emailFormat(email);
        if(!emailFormat) {
            return new Response(CodeMsg.MAIL_FORMATE);
        }
        String mailCode;
        // 生成随机字串短信码 (6位)
        try {
            mailCode = VerifyCodeUtils.generateVerifyCode(6);
            //TODO 缓存
            //cacheService.saveAndUpdateCache(redisTip + email, mailCode.toLowerCase());
            RedisUtils.set(RedisKeyConstants.KYC_EMAIL_CACHE + email, mailCode.toLowerCase(), time_kyc_email_cache);
            log.info("kyc send mail. email is {}, mailCode is {}", email, mailCode);
        }catch(Exception e) {
            log.error("send mail code -> get emailCode error. {}",e);
            return new Response(CodeMsg.GET_EMAIL_CODE_FAIL);
        }

        // 发送邮件
        StringBuilder text = new StringBuilder();
        text.append("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>IFOODSCHAIN</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div style=\"width: 660px;margin:0 auto;border:1px solid #dedede;\">\n" +
                " <div style=\"font-family: 'Baghdad';font-size: 24px;line-height: 70px;font-weight: 600;color: #fff;background-color: #598fdb;padding-left: 60px;\">IFOODSCHAIN.</div>\n" +
                " <div style=\"width: 540px;height: 380px;margin:0 auto;margin-top: 20px;\">\n" +
                "  <div style=\"font-family: 'Baghdad';font-size: 22px;line-height: 48px;\">" +
                "您好！</div>\n" +
                "  <div style=\"font-family: 'Baghdad';line-height: 1.5;color: #666666;\">" +
                "您本次操作获得的验证码15分钟内有效，如非本人操作，请忽略此邮件。</div>\n" +
                "  <div style=\"font-family: 'Baghdad';line-height: 1.5;color: #666666;\">验证码：" +  mailCode + "</div>\n" +
                "  <div style=\"position:relative;text-align: center;\">\n" +
                "   <div style=\"font-family: 'Baghdad';position:absolute;width: 200px;height: 40px;top: 65px;left: 50%;margin-left: -100px;line-height: 40px;font-size: 24px;letter-spacing: 0.1em;font-weight: 600;color: #578fdb;\">" + mailCode + "</div>\n" +
                "   <img style=\"margin:0 auto;margin-top:30px;width:200px;height:auto;\" src=\"https://lbcy.oss-cn-zhangjiakou.aliyuncs.com/mail.png\"/>\n" +
                "  </div>\n" +
                " </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
        
        //text.append("您本次操作获得的验证码15分钟内有效，如非本人操作，请忽略此邮件。").append("验证码：" +  mailCode);
        
        boolean flag = false;
        try {
            flag = emailService.send("kyc活动", text.toString(), email);
        } catch (Exception e) {
            //TODO 缓存
            //cacheService.delete(redisTip+email);
            RedisUtils.del(RedisKeyConstants.KYC_EMAIL_CACHE+email);
            log.error("kyc send emailCode fail. error:{}",e);
        }
        
        if (!flag) {
            log.error("kyc send emailCode fail mail {}, CodeMsg {}", email, CodeMsg.MAIL_CODE_SEND_FAIL.value());
            RedisUtils.del(RedisKeyConstants.KYC_EMAIL_CACHE+email);
            return new Response(CodeMsg.MAIL_CODE_SEND_FAIL);
        }

        return new Response("发送成功");
    }
    
    /**
     * 验证邮箱 和 邮箱验证码, 不删除验证码缓存
     */
    @RequestMapping(value = "/email/verify")
    @ResponseBody
    public Response verifyEmailCode(String email, String emailCode) {
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(emailCode)) {
            log.info("params is empty: {} | {} ", email, emailCode);
            return new Response(CodeMsg.PARAMS_ISEMPTY);
        }
        
        //邮箱验证码校验
        //TODO 缓存
        //String value = cacheService.getCache(redisTip+email);
        String value = RedisUtils.getStr(RedisKeyConstants.KYC_EMAIL_CACHE+email);
        if(StringUtils.isEmpty(value)) {
            return new Response(CodeMsg.MAIL_CODE_FAIL);
        }
        if(!value.equalsIgnoreCase(emailCode)) {
            return new Response(CodeMsg.MAIL_CODE_FAIL);
        }
        
        return new Response("邮箱验证码校验成功");
    }
    
    
    /**
     * 提交保存 kyc 用户信息
     * 添加 电报群名称 - 微信群名称
     */
    @RequestMapping(value = "/info/save")
    @ResponseBody
    public Response saveCoinKyc(String email, String password, String emailCode, String ethAddress, 
                                String neoAddress, String ethAmount, String telegram, HttpServletRequest request) {
        Response response = new Response();
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password) || StringUtils.isEmpty(emailCode) || StringUtils.isEmpty(ethAddress)) {
            log.info("params is empty: {} | {} | {} | {}", email, password, emailCode, ethAddress);
            return new Response(CodeMsg.PARAMS_ISEMPTY);
        }
        //校验ip, 限制cn和us
        if(!AddressUtils.limitCNAndUS(request)) {
            log.info("limit address {}", AddressUtils.getCountryId(request));
            return new Response(CodeMsg.LIMIT_ADDRESS);
        }
        if(StringUtils.isEmpty(ethAmount)) {
            ethAmount = "0";
        }
        //数字长度不超过11
        if(ethAmount.length() > 11 ){
            return new Response(CodeMsg.ETHAMOUNT_LENGTH_BEYOND);
        }
        
        String telegramStr = CommonTools.conventEmoji2UTF(telegram);
        
        //TODO 验证数字格式是不是正确;
        
        
        log.info("save email ethAddress:{} | neoAddress:{}",email, ethAddress, neoAddress);
        
        //邮箱已经存在
        int count = ifoodCoinKycService.countByEmail(email);
        if(count>0) {
            return new Response(CodeMsg.MAIL_EXIST);
        }
        
        //邮箱验证码校验
        String value = RedisUtils.getStr(RedisKeyConstants.KYC_EMAIL_CACHE+email);
        //String value = cacheService.getCache(redisTip+email);
        if(!emailCode.equalsIgnoreCase(value)) {
            return new Response(CodeMsg.MAIL_CODE_FAIL);
        }
        
        try {
            ifoodCoinKycService.save(new IfoodCoinKyc(email, Md5tools.md5(password), ethAddress, neoAddress, ethAmount, telegramStr));
        }catch(Exception e) {
            log.error("save ifood_coin_kys error: {}", e);
            return new Response(CodeMsg.SAVE_ERROR);
        }
        
        try {
            //TODO 缓存
            //cacheService.delete(redisTip+email);
            RedisUtils.del(RedisKeyConstants.KYC_EMAIL_CACHE+email);
        }catch(Exception e) {
            log.error("delete key fail. key:{}", RedisKeyConstants.KYC_EMAIL_CACHE+email);
        }
        
        return new Response("0000", "保存成功");
        
        
    }
    
    /**
     * 根据 用户名 和 密码 查询用户的kyc信息
     */
    @RequestMapping("/mail/find")
    @ResponseBody
    public Response findByEmailAndPwd(String email, String password, HttpServletRequest request) {
        Response response = new Response();
        
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            log.info("params is empty: {} | {}", email, password);
            return new Response(CodeMsg.PARAMS_ISEMPTY);
        }

        //校验ip, 限制cn和us
        if(!AddressUtils.limitCNAndUS(request)) {
            log.info("limit address {}", AddressUtils.getCountryId(request));
            return new Response(CodeMsg.LIMIT_ADDRESS);
        }
        
        IfoodCoinKyc kycInfo = ifoodCoinKycService.findByEmail(email);
        if(kycInfo == null) {
            log.info("find kyc by email is empty: {}", email);
            return new Response(CodeMsg.EMAIL_PWD_FAIL);
        }
        if(!kycInfo.getPassword().equalsIgnoreCase(Md5tools.md5(password))) {
            return new Response(CodeMsg.EMAIL_PWD_FAIL);
        }
        
        kycInfo.setTelegram(CommonTools.conventUTF2Emoji(kycInfo.getTelegram()));
        
        response.setData(kycInfo);
        
        return response;
    }
}
