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

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

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
        model.addAttribute("title", "Messages");
        List<Request> userRequests = requestRepository.findByUserId(user.getId());
        List<Response> threadResponses = new ArrayList<>();
        for(Request userRequest : userRequests){
            List<Thread> requestThreads = userRequest.getThreads();
            for(Thread requestThread:requestThreads){
                List<Response> requestResponses = requestThread.getResponses();
                for(Response requestResponse:requestResponses){
                    threadResponses.add(requestResponse);
                }
            }
        }
        model.addAttribute("threadResponses",threadResponses);
        return "response/index";
    }
    @PostMapping("create")
    public String displayCreateResponseForm(HttpServletRequest request, Model model,@RequestParam("id") Integer id, @RequestParam(value = "threadId", required = false) Integer threadId){
        User user = authenticationController.getUserFromSession(request.getSession());
        model.addAttribute("title", "Respond to Request");
        Request requestDetails = requestRepository.findById(id).get();
        model.addAttribute("request", requestDetails);
        CreateResponseFormDTO createResponseFormDTO = new CreateResponseFormDTO();
        createResponseFormDTO.setUser(user);
        model.addAttribute(createResponseFormDTO);
        model.addAttribute("threadId", (!isNull(threadId))?threadId:-1);

        return "response/create";
    }
    @PostMapping("submit")
    public String processResponse(@ModelAttribute @Valid CreateResponseFormDTO createResponseFormDTO, Errors errors, HttpServletRequest request, Model model, @RequestParam("id") Integer id, @Nullable @RequestParam(value = "threadId", required = false) Integer threadId){
        User user = authenticationController.getUserFromSession(request.getSession());
        if (errors.hasErrors()){
            model.addAttribute("title", "Respond to Request");
            model.addAttribute(createResponseFormDTO);
            return "response/create";
        }
        Response response = new Response(createResponseFormDTO.getUser(), createResponseFormDTO.getMessage(), createResponseFormDTO.getContactSharing());
        Thread thread;

        if(threadId != -1){
             thread = threadRepository.findById(threadId).get();
        }
        else{
            thread = new Thread();
        }

        Request threadRequest = requestRepository.findById(id).get();
        thread.setRequest(threadRequest);
        thread.addResponse(response);
        thread.setUser(user);
        responseRepository.save(response);
        threadRepository.save(thread);
        response.setThread(thread);
        userRepository.save(user);
        model.addAttribute(thread);
    return "/response/responseConfirmation";
    }

}
