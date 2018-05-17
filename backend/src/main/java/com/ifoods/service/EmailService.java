package com.ifoods.service;

import java.util.List;

/**
 * 邮件服务
 * Created by 吴晓冬 on 2017/12/28.
 */
public interface EmailService
{
    public boolean send(String subject, String text, String to) throws Exception;

    public void send(String subject, String text, List<String> tos) throws Exception;
}
