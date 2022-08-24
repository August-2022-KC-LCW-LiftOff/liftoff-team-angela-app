package com.ark.demo.controllers;

import com.ark.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.isNull;

@Controller
public class HomeController {
    @Autowired
    AuthenticationController authenticationController;
    @GetMapping
    public String index(HttpServletRequest request, Model model){
        model.addAttribute("user",authenticationController.getUserFromSession(request.getSession()));
        return "index";
    }


}
