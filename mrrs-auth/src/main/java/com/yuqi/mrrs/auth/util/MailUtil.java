package com.yuqi.mrrs.auth.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;

@Slf4j
@Component
@EnableConfigurationProperties({MailProperties.class})
public class MailUtil {

    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Autowired
    MailProperties mailProperties;

    @Autowired
    private TemplateEngine templateEngine;

    public void SendTemplateMail(String to, String subject, HashMap<String,Object> params){

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        try {
            messageHelper.setFrom(mailProperties.getUsername());
            messageHelper.setSubject(subject);
            messageHelper.setTo(to);
            Context context = new Context();
            context.setVariables(params);
            String s = templateEngine.process("/RegisterValidCode", context);
            messageHelper.setText(s,true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("邮件发送失败");
            e.printStackTrace();
        }

    }

}
