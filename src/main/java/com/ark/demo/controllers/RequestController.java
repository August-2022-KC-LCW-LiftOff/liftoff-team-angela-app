package com.ark.demo.controllers;

import com.ark.demo.models.data.RequestRepository;
import com.ark.demo.models.Request;
import com.ark.demo.models.User;
import com.ark.demo.models.data.UserRepository;
import com.ark.demo.models.dto.CreateRequestFormDTO;
import com.ark.demo.models.enums.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping
    public String requestForm(Model model, HttpServletRequest request) {
        User user = authenticationController.getUserFromSession(request.getSession());
        if(isNull(user)){
            return "redirect:../login";
        }
        model.addAttribute("title", "Create Request");
        model.addAttribute(new CreateRequestFormDTO());
        model.addAttribute("states",authenticationController.createStatesMap());
        model.addAttribute("types", RequestType.values());

        return "requestTemplates/createRequest";
    }

    @PostMapping
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

        Request newRequest = new Request(createRequestFormDTO.getTitle(),createRequestFormDTO.getDescription(), createRequestFormDTO.getAddressLine1(),createRequestFormDTO.getAddressLine2(),createRequestFormDTO.getCity(),createRequestFormDTO.getState(),createRequestFormDTO.getZipcode(),createRequestFormDTO.getDueDate(),createRequestFormDTO.getPublicEvent(),createRequestFormDTO.getLocation(), createRequestFormDTO.getType(),createRequestFormDTO.getLevel());
        newRequest.setPublicEvent(createRequestFormDTO.getPublicEvent());
        newRequest.setUser(user);
        requestRepository.save(newRequest);
        user.addRequest(newRequest);
        userRepository.save(user);
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
    }

    @GetMapping("viewRequest/{requestId}")
    public String viewRequest(HttpServletRequest request, Model model, @PathVariable int requestId){
        model.addAttribute("request", requestRepository.findById(requestId).get());
        return "requestTemplates/viewRequest";
    }







}


