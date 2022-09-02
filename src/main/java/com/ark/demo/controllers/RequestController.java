package com.ark.demo.controllers;

import com.ark.demo.models.data.RequestRepository;
import com.ark.demo.models.Request;
import com.ark.demo.models.User;
import com.ark.demo.models.data.ThreadRepository;
import com.ark.demo.models.data.UserRepository;
import com.ark.demo.models.dto.CreateRequestFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("request")
public class RequestController {
    @Autowired
    AuthenticationController authenticationController;

   @Autowired
   RequestRepository requestRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ThreadRepository threadRepository;

    @GetMapping
    public String requestForm(Model model, HttpServletRequest request) {
        User user = authenticationController.getUserFromSession(request.getSession());
        if(isNull(user)){
            return "redirect:../login";
        }
        model.addAttribute("title", "Create Request");
        model.addAttribute(new CreateRequestFormDTO());
        return "requestTemplates/createRequest";
    }

    @PostMapping()
    public String requestSubmit(@ModelAttribute @Valid CreateRequestFormDTO createRequestFormDTO, Model model, Errors errors, HttpServletRequest request) {
        if(errors.hasErrors()){
            model.addAttribute("title", "Create Request");
            model.addAttribute(createRequestFormDTO);
            return "requestTemplates/createRequest";
        }
        User user = authenticationController.getUserFromSession(request.getSession());
        if(isNull(user)){
            return "redirect:../login";
        }

        Request newRequest = new Request(createRequestFormDTO.getTitle(), createRequestFormDTO.getDescription(), user, createRequestFormDTO.getDueDate());

        Thread  newThread =  new Thread();



        if(createRequestFormDTO.getPublicEvent()){
            newRequest.setPublicEvent(createRequestFormDTO.getPublicEvent());
        }

//        its not correct :(
        newThread.addThreadUser(user);
        threadRepository.save(user);



        requestRepository.save(newRequest);
        user.addRequest(newRequest);
        userRepository.save(user);



//** removed the forward slash
        return "redirect:request/requestConfirmation";
    }


    @GetMapping("userRequests")
    public String allUsrRequests(HttpServletRequest request, Model model){
        User user = authenticationController.getUserFromSession(request.getSession());
        if(isNull(user)){
            return "redirect:../login";
        }

        model.addAttribute("title", "Request Confirmation");
        model.addAttribute("requests", requestRepository.findByUserId(user.getId()));
        return "requestTemplates/requestConfirmation";
//        needs folder orientation
    }

    @GetMapping("requestConfirmation")
    public String displayRequestConfirmation(HttpServletRequest request, Model model){
        User user = authenticationController.getUserFromSession(request.getSession());
        if(isNull(user)){
            return "redirect:../login";
        }

        model.addAttribute("title", "Request Confirmation");
        model.addAttribute("request", requestRepository.findLastRequestByUserId(user.getId()));
        return "requestTemplates/requestConfirmation";
//        needs folder orientation
    }









}


