package com.ark.demo.controllers;

import com.ark.demo.models.Request;
import com.ark.demo.models.Response;
import com.ark.demo.models.User;
import com.ark.demo.models.Thread;
import com.ark.demo.models.data.RequestRepository;
import com.ark.demo.models.data.ResponseRepository;
import com.ark.demo.models.data.ThreadRepository;
import com.ark.demo.models.data.UserRepository;
import com.ark.demo.models.dto.CreateResponseFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("response")
public class ResponseController {
    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    RequestRepository requestRepository;


    @Autowired
    ThreadRepository threadRepository;

    @Autowired
    AuthenticationController authenticationController;





    @GetMapping("index")
    public String index(HttpServletRequest request, Model model){
        User user = authenticationController.getUserFromSession(request.getSession());
        if(isNull(user)){
            return "redirect:../login";
        }
        model.addAttribute("title", "Messages");
        model.addAttribute("responses", responseRepository.findByUserId(user.getId()));


        return "response/index";
    }

    @PostMapping("create")
    public String displayCreateResponseForm(HttpServletRequest request, Model model,@RequestParam("id") Integer id) {
        User user = authenticationController.getUserFromSession(request.getSession());
        if (isNull(user)) {
            return "redirect:../login";
        }

        model.addAttribute("title", "Respond to Request");
        Request requestDetails = requestRepository.findById(id).get();
        model.addAttribute("request", requestDetails);
        CreateResponseFormDTO createResponseFormDTO = new CreateResponseFormDTO();
        createResponseFormDTO.setUser(user);



        model.addAttribute(createResponseFormDTO);

        return "response/create";
    }

    @PostMapping("submit")
    public String processResponse(@ModelAttribute @Valid CreateResponseFormDTO createResponseFormDTO, Errors errors, HttpServletRequest request, Model model){
        if (errors.hasErrors()){
            model.addAttribute("title", "Respond to Request");
            model.addAttribute(createResponseFormDTO);
            return "response/create";
        }


        User user = authenticationController.getUserFromSession(request.getSession());
        if (isNull(user)) {
            return "redirect:../login";
        }

//        removed id
//        Thread thread = new Thread();
//        check imports to ensure the model has been added
        Request threadRequest = requestRepository.findById(createResponseFormDTO.getThread().getId()).get();
        Thread thread = new Thread(threadRequest, createResponseFormDTO.getUser());

        threadRequest.addThread(thread);
        createResponseFormDTO.getUser().addUserThread(thread);


        Response response = new Response(createResponseFormDTO.getUser(), createResponseFormDTO.getMessage(), createResponseFormDTO.getContactSharing());
        thread.addThreadResponse(response);
        threadRepository.save(thread);
        responseRepository.save(response);

        userRepository.save(user);
    return "redirect:/response/responseConfirmation";
    }

    @GetMapping("responseConfirmation")
    public String displayResponseConformation(HttpServletRequest request, Model model) {
        User user = authenticationController.getUserFromSession(request.getSession());

        if (isNull(user)) {
            return "redirect:../login";
        }

        return "response/responseConfirmation";
    }



}
