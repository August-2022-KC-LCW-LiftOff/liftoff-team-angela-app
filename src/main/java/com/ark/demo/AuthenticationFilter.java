package com.ark.demo;

import com.ark.demo.controllers.AuthenticationController;
import com.ark.demo.models.User;
import com.ark.demo.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter implements HandlerInterceptor {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;

    private static final List<String> whitelist = Arrays.asList("/login","/register","/logout","/css/ark.css","/", "/aboutUs","/policies/safety");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if(isWhiteListed(request.getRequestURI())){
            return true;
        }
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        if(user != null){
            return true;
        }
        response.sendRedirect("/login");
        return false;
    }

    public static boolean isWhiteListed(String path){
        if(path.contains(";")){
            path = path.substring(0,path.indexOf(";"));
        }
        for(String safepath:whitelist){
            if(path.contains("/images/")){
                return true;
            }
            if(path.equals(safepath)){
                return true;
            }
        }
        return false;
    }
}
