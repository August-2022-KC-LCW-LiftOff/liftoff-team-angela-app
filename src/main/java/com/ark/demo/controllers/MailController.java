package com.ark.demo.controllers;

import com.ark.demo.models.UserDetails;
import com.ark.demo.models.data.UserDetailsRepository;
import com.ark.demo.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping("mail")
public class MailController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @GetMapping()
    public String verifyEmail(@PathParam("uid") String uid){
        UserDetails userDetails = userDetailsRepository.findByUid(uid);
        userDetails.setEmailVerified(true);
        userDetails.setUid(null);
        userDetailsRepository.save(userDetails);
        return "/mailTemplates/emailVerified";
    }
}
