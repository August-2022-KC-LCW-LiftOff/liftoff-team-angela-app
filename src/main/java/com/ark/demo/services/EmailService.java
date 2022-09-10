package com.ark.demo.services;

import com.ark.demo.models.Request;
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
    public void sendRegistrationEmail(String toEmail, String uid) {
        String subject = "Verify E-mail Address";
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
        sendMail(toEmail,text, subject);
    }
    public void sendRequestConfirmation(String toEmail, Request request){
        String subject = "New Request Created";
        String text = String.format("""
                <!DOCTYPE html>
                <html lang="en">
                    <head>
                    <style>
                    .label {text-align:right;}
                    .value {text-align:left;}
                    tr:nth-child(odd){background:rgb(127,127,127);}
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
                            <h1>A new request has been created!</h1>
                          </td>
                        </tr>
                        <tr>
                          <td colspan=3>
                            <h3>Request Details</h3>
                          </td>
                        </tr>
                        <tr>
                          <td class="label">title:</td>
                          <td colspan=2 class="value">
                            %s
                          </td>
                        </tr>
                        <tr>
                          <td class="label">public:</td>
                          <td colspan=2 class="value">
                            %s
                          </td>
                        </tr>
                        <tr>
                          <td class="label">status:</td>
                          <td colspan=2 class="value">
                            %s
                          </td>
                        </tr>
                        <tr>
                          <td class="label">due date:</td>
                          <td colspan=2 class="value">
                            %s
                          </td>
                        </tr>
                        <tr>
                          <td class="label">address line 1:</td>
                          <td colspan=2 class="value">
                            %s
                          </td>
                        </tr>
                        <tr>
                          <td class="label">address line 2:</td>
                          <td colspan=2 class="value">
                            %s
                          </td>
                        </tr>
                        <tr>
                          <td class="label">city:</td>
                          <td colspan=2 class="value">
                            %s
                          </td>
                        </tr>
                        <tr>
                          <td class="label">state:</td>
                          <td colspan=2 class="value">
                            %s
                          </td>
                        </tr>
                        <tr>
                          <td class="label">zipcode:</td>
                          <td colspan=2 class="value">
                            %s
                          </td>
                        </tr>
                        <tr>
                          <td class="label">description:</td>
                          <td colspan=2 class="value">
                            %s
                          </td>
                        </tr>
                      </table>
                    </div>
                    </body>
                </html>
                """,
                request.getTitle(),
                request.getPublicEvent(),
                request.getStatus(),
                request.getDueDate(),
                request.getAddressLine1(),
                request.getAddressLine2(),
                request.getCity(),
                request.getState(),
                request.getZipcode(),
                request.getDescription());
        sendMail(toEmail,text,subject);
    }
    private void sendMail(String toEmail, String text, String subject){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try{
            helper = new MimeMessageHelper(mimeMessage,true);
            helper.setFrom("ark-no-reply@domesne.com");
            helper.setTo(toEmail);
            helper.setSubject("subject");
            helper.setText(text,true);
        } catch (Exception e){
            e.printStackTrace();
        }
        javaMailSender.send(mimeMessage);
    }
}
