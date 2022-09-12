package com.ark.demo.services;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    private JavaMailSender javaMailSender;
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    public void sendMail(String toEmail, String htmlText, String subject) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
            helper = new MimeMessageHelper(mimeMessage,true);
            helper.setFrom("ark-no-reply@domesne.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlText,true);
        javaMailSender.send(mimeMessage);
    }
}
