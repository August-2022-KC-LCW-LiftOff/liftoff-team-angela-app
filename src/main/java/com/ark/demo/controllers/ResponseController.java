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
import javax.websocket.server.PathParam;

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

    @GetMapping("create")
    public String displayCreateResponseForm(HttpServletRequest request, Model model, @PathParam("requestId") Integer requestId ) {
        User user = authenticationController.getUserFromSession(request.getSession());
        if (isNull(user)) {
            return "redirect:../login";
        }
        model.addAttribute("title", "Respond to Request");
        model.addAttribute("request", requestRepository.findById(requestId));
        CreateResponseFormDTO createResponseFormDTO = new CreateResponseFormDTO();
        createResponseFormDTO.setUser(user);
        model.addAttribute(createResponseFormDTO);
        return "response/create";
    }

    @PostMapping("create")
    public String processResponse(@ModelAttribute @Valid CreateResponseFormDTO createResponseFormDTO, Errors errors, HttpServletRequest request, Model model, @RequestParam Integer requestId){
        if (errors.hasErrors()){
            model.addAttribute("title", "Respond to Request");
            model.addAttribute(createResponseFormDTO);
            return "response/create";
        }
        User user = authenticationController.getUserFromSession(request.getSession());
        if (isNull(user)) {
            return "redirect:../login";
        }
        Response response = new Response(createResponseFormDTO.getUser(), createResponseFormDTO.getMessage(), createResponseFormDTO.getContactSharing());

        Request incomingRequest = requestRepository.findById(requestId).get();
        Thread newThread = new Thread(user, incomingRequest);

        newThread.addResponse(response);

        threadRepository.save(newThread);
        incomingRequest.addThread(newThread);
        requestRepository.save(incomingRequest);
        User threadUser = userRepository.findById(createResponseFormDTO.getUser().getId()).get();

        threadUser.addThread(newThread);
        userRepository.save(threadUser);
        response.setThread(newThread);
        responseRepository.save(response);
    return "redirect:/response/viewResponse";
    }

    @GetMapping("viewResponse")
    public String displayResponseConformation(HttpServletRequest request, Model model) {
        User user = authenticationController.getUserFromSession(request.getSession());

        if (isNull(user)) {
            return "redirect:../login";
        }

        return "response/responseConfirmation";

    }

}
