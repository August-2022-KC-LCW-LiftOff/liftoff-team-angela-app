package com.ark.demo.controllers;

import com.ark.demo.models.Request;
import com.ark.demo.models.Response;
import com.ark.demo.models.Thread;
import com.ark.demo.models.User;
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
        model.addAttribute("title", "Messages");
        model.addAttribute("threadResponses", responseRepository.findByUserId(user.getId()));
        return "response/index";
    }
    @PostMapping("create")
    public String displayCreateResponseForm(HttpServletRequest request, Model model,@RequestParam("id") Integer id) {
        User user = authenticationController.getUserFromSession(request.getSession());
        model.addAttribute("title", "Respond to Request");
        Request requestDetails = requestRepository.findById(id).get();
        model.addAttribute("request", requestDetails);
        CreateResponseFormDTO createResponseFormDTO = new CreateResponseFormDTO();
        createResponseFormDTO.setUser(user);
        model.addAttribute(createResponseFormDTO);
        return "response/create";
    }
    @PostMapping("submit")
    public String processResponse(@ModelAttribute @Valid CreateResponseFormDTO createResponseFormDTO, Errors errors, HttpServletRequest request, Model model, @RequestParam("id") Integer id){
        User user = authenticationController.getUserFromSession(request.getSession());
        if (errors.hasErrors()){
            model.addAttribute("title", "Respond to Request");
            model.addAttribute(createResponseFormDTO);
            return "response/create";
        }
        Response response = new Response(createResponseFormDTO.getUser(), createResponseFormDTO.getMessage(), createResponseFormDTO.getContactSharing());
        Thread thread = new Thread();
        Request threadRequest = requestRepository.findById(id).get();
        thread.setRequest(threadRequest);
        thread.addResponse(response);
        thread.setUser(user);
        responseRepository.save(response);
        threadRepository.save(thread);
        response.setThread(thread);
        userRepository.save(user);
    return "redirect:/response/responseConfirmation";
    }
    @GetMapping("responseConfirmation")
    public String displayResponseConformation(HttpServletRequest request, Model model) {
        return "response/responseConfirmation";
    }
}
