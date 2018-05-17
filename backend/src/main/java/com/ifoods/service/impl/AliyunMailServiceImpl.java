package com.ifoods.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ifoods.service.EmailService;

/**
 * Created by rzc on 2018/3/8.
 */
@Service("emailService")
public class AliyunMailServiceImpl implements EmailService {

    private String from = "admin@kycposter.ifoodschain.com";
    private String fromNick = "超级探针";
    private String replyAddress = "chaojitanzhen@ifoodschain.com";
    private int addressType = 1;

    @Override
    public boolean send(String subject, String text, String to) throws Exception {
        if (!emailFormat(to)) {
            return false;
        }

        // 如果是除杭州region外的其它region（如新加坡、澳洲Region），需要将下面的"cn-hangzhou"替换为"ap-southeast-1"、或"ap-southeast-2"。
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIhfou13xPOSlE", "55ZhYQQ5zNLVoGOzYLH8ZOZnv5WoSn");

        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            //request.setVersion("2017-06-22");// 如果是除杭州region外的其它region（如新加坡region）,必须指定为2017-06-22
            request.setAccountName(from); // "控制台创建的发信地址"
            request.setFromAlias(fromNick); // "发信人昵称"
            request.setAddressType(addressType);
            request.setTagName(fromNick); // "控制台创建的标签"
            request.setReplyToAddress(true); // 使用管理控制台中配置的回信地址（状态必须是验证通过）
            request.setReplyAddress(replyAddress);
            request.setToAddress(to); // "目标地址"
            request.setSubject(subject); // "邮件主题"
            request.setHtmlBody(text); // "邮件正文"
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            System.out.print(httpResponse.getEnvId());
            return true;
        } catch (ServerException e) {
            e.printStackTrace();
            return false;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void send(String subject, String text, List<String> tos) throws Exception {
        for (String email : tos) {
            if (!emailFormat(email)) {
                return;
            }
            send(subject, text, email);
        }
    }


    /**
     * 校验EMAIL格式，真为正确
     *
     * @param email
     * @return true 为格式正确 false 为格式错误
     * @author
     * @date 2017-7-19
     */
    public static boolean emailFormat(String email) {
        boolean tag = true;
        if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
            tag = false;
        }
        return tag;
    }
}
