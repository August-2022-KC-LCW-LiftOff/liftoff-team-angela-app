package com.ark.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    @Autowired
    AuthenticationController authenticationController;
    @GetMapping
    public String index(HttpServletRequest request, Model model){
        model.addAttribute("user",authenticationController.getUserFromSession(request.getSession()));
        return "index";
    }

    @GetMapping("/tos")
    public String displayTermsOfService(){
        return "/policies/tos";
    }
}
