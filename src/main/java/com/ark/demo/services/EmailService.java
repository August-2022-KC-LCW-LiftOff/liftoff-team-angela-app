package com.ark.demo.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {
    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    public void sendRegistrationEmail(String toEmail, String uid) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom("ark-no-reply@domesne.com");
        helper.setTo(toEmail);
        helper.setSubject("Verify E-mail Address");
        String text = String.format("""
                <!DOCTYPE html>
                <html lang="en">
                    <head>
                    <style>
                    td {text-align:center;}
                    .button{
                      width: 150px;
                      height:75px;
                      background:rgb(0,1,213);
                      }
                     a{
                       color: rgb(255,255,255);
                       font-size:36px;
                       text-decoration: none;
                     }
                     em{
                       font-size: 10px;
                     }
                    </style>
                    </head>
                    <body style="text-align:center;">
                    <div>
                      <table>
                        <tr>
                          <td style="background:black;color:white;" colspan=3>
                            ARK
                          </td>
                        <tr>
                        <tr>
                          <td colspan=3>
                            <h1>Thank you for registering with <img src='http://localhost:8080/images/arkheartsmall.png'>ARK!</h1>
                          </td>
                        </tr>
                        <tr>
                          <td colspan=3>
                            <h3>Click the button below to verify your email address.</h3>
                          </td>
                        </tr>
                        <tr>
                          <td></td>
                          <td class="button">
                            <a href="http://localhost:8080/mail?uid=%s">Verify</a>
                          </td>
                          <td></td>
                        </tr>
                        <tr>
                          <td colspan=3>
                            <em>You are receiving this email because this email address was used to register a new account at ARK - Acts of Random Kindness.  If you did not request this email, you can delete it and no further action is required.  If you are not the intended recipient of this email, please delete immediately.</em>
                          </td>
                        </tr>
                      </table>
                    </div>
                    </body>
                </html>
                """, uid);
        helper.setText(text,true);
        javaMailSender.send(mimeMessage);
    }
}
