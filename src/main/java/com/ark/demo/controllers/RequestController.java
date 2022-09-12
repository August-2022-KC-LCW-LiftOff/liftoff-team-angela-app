package com.ark.demo.controllers;

import com.ark.demo.models.data.RequestRepository;
import com.ark.demo.models.Request;
import com.ark.demo.models.User;
import com.ark.demo.models.data.RequestRepository;
import com.ark.demo.models.data.ThreadRepository;
import com.ark.demo.models.data.UserRepository;
import com.ark.demo.models.dto.CloseRequestFormDTO;
import com.ark.demo.models.dto.CreateRequestFormDTO;
import com.ark.demo.models.enums.RequestType;
import com.ark.demo.models.dto.EditRequestFormDTO;
import com.ark.demo.models.enums.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.TreeMap;

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


    @GetMapping()
    public String requestForm(Model model, HttpServletRequest request) {
        User user = authenticationController.getUserFromSession(request.getSession());
        if(isNull(user)){
            return "redirect:/login";
        }
        model.addAttribute("title", "Create Request");
        model.addAttribute(new CreateRequestFormDTO());
        model.addAttribute("states",authenticationController.createStatesMap());
        model.addAttribute("types", RequestType.values());

        return "requestTemplates/createRequest";
    }

    @PostMapping()
    public String requestSubmit(@ModelAttribute @Valid CreateRequestFormDTO createRequestFormDTO, Model model, Errors errors, HttpServletRequest request) {
        User user = authenticationController.getUserFromSession(request.getSession());
        if(isNull(user)){
            return "redirect:/login";
        }
        if(errors.hasErrors()){
            model.addAttribute("title", "Create Request");
            model.addAttribute(createRequestFormDTO);
            return "requestTemplates/createRequest";
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
    public String allUserRequests(HttpServletRequest request, Model model){
        User user = authenticationController.getUserFromSession(request.getSession());
        if(isNull(user)){
            return "redirect:../login";
        }
        model.addAttribute(user);
        model.addAttribute("title", "User Requests");
        model.addAttribute("requests", requestRepository.findByUserId(user.getId()));
        model.addAttribute(new CloseRequestFormDTO());
        return "requestTemplates/userRequests";
    }
    @PostMapping("userRequests")
    public String showRequestsByUser(HttpServletRequest request, Model model){
        User user = authenticationController.getUserFromSession(request.getSession());
        if(isNull(user)){
            return "redirect:../login";
        }
        model.addAttribute(user);
        model.addAttribute("title", "User Requests");
        model.addAttribute("requests", requestRepository.findByUserId(user.getId()));
        return "requestTemplates/userRequests";
    }
    @PostMapping("edit")
    public String editRequest(@RequestParam("requestId") Integer requestId, HttpServletRequest request, Model model) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        User user = authenticationController.getUserFromSession(request.getSession());
        if(isNull(user)){
            return "redirect:../login";
        }
        Request updateRequest = requestRepository.findById(requestId).get();
        model.addAttribute(sendObjectToDTO(updateRequest,  new EditRequestFormDTO()));
        model.addAttribute("states",authenticationController.createStatesMap());
        model.addAttribute("statuses",createStatuses());
        CloseRequestFormDTO closeRequestFormDTO = new CloseRequestFormDTO();
        closeRequestFormDTO.setRequestId(requestId);
        model.addAttribute(closeRequestFormDTO);
        return "requestTemplates/editRequest";
    }
    @PostMapping("edit/process")
    public String processEditRequestForm(@ModelAttribute @Valid EditRequestFormDTO editRequestFormDTO, @ModelAttribute CloseRequestFormDTO closeRequestFormDTO, Errors errors, HttpServletRequest request, Model model){
        User user = authenticationController.getUserFromSession(request.getSession());
        if(isNull(user)){
            return "redirect:../../login";
        }
        if(errors.hasErrors()){
            model.addAttribute("title","Edit Request");
            model.addAttribute(editRequestFormDTO);
            model.addAttribute("states",authenticationController.createStatesMap());
            model.addAttribute("statuses",createStatuses());
            return "requestTemplates/editRequest";
        }
        Request editRequest = requestRepository.findById(editRequestFormDTO.getId()).get();
        requestRepository.save((Request) updateObjectFromDTO(editRequest,editRequestFormDTO));
        if(closeRequestFormDTO.getCloseType().equals("resolved")){
            model.addAttribute("request",editRequest);
            return "requestTemplates/showGratitude";
        }
        return "redirect:/request/userRequests";
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
    public String viewRequest(@PathVariable("requestId") Integer requestId,HttpServletRequest request, Model model){
        User user = authenticationController.getUserFromSession(request.getSession());
        if(isNull(user)){
            return "redirect:../../login";
        }
        Request viewRequest = requestRepository.findById(requestId).get();
        model.addAttribute("request",viewRequest);
        return "requestTemplates/viewRequest";
    }
    @PostMapping("close")
    public String closeRequest(@RequestParam Integer requestId,HttpServletRequest request,Model model){
        User user = authenticationController.getUserFromSession(request.getSession());
        if(isNull(user)){
            return "redirect:../login";
        }
        model.addAttribute("title","Close Request");
        CloseRequestFormDTO closeRequestFormDTO = new CloseRequestFormDTO();
        closeRequestFormDTO.setRequestId(requestId);
        model.addAttribute(closeRequestFormDTO);
        return "requestTemplates/closeRequest";
    }
    @PostMapping("closeRequest")
    public String closeRequestFinal(@RequestParam Integer requestId, @RequestParam String closeType,HttpServletRequest request){
        User user = authenticationController.getUserFromSession(request.getSession());
        if(isNull(user)){
            return "redirect:../login";
        }
        if(closeType.equals("cancel")){
            return "redirect:/request/userRequests";
        }
        Request closeRequest = requestRepository.findById(requestId).get();
        closeRequest.setStatus(RequestStatus.INACTIVE);
        requestRepository.save(closeRequest);
        return "redirect:/request/userRequests";
    }
    public TreeMap<String, String> createStatuses(){
        TreeMap<String, String> statuses = new TreeMap<>();
        for (RequestStatus status : RequestStatus.values()){
            statuses.put(status.toString(), status.getDisplayName());
        }
        return statuses;
    }

    private Object sendObjectToDTO(Object o,Object dtoObject) throws InvocationTargetException, IllegalAccessException {
        Class<?> objectClass = o.getClass();
        Class<?> dtoClass = dtoObject.getClass();
        Field[] dtoDeclaredFields = dtoClass.getDeclaredFields();
        Method[] dtoDeclaredMethods = dtoClass.getDeclaredMethods();
        Method setterMethod = null;
        Method getterMethod = null;
        for(Field declaredField:dtoDeclaredFields){
            try{
                setterMethod = dtoClass.getDeclaredMethod("set"+declaredField.getName().substring(0,1).toUpperCase()+declaredField.getName().substring(1),declaredField.getType());
            } catch (Exception e){
                System.out.println("Setter Not Found: "+e.getMessage());
            }
            try{
                if(declaredField.getName() == "id"){
                    getterMethod = objectClass.getSuperclass().getDeclaredMethod("getId");
                } else {
                    getterMethod = objectClass.getDeclaredMethod("get"+declaredField.getName().substring(0,1).toUpperCase()+declaredField.getName().substring(1));
                }
            } catch (Exception e){
                System.out.println("Getter Not Found: "+e.getMessage());
            }
            setterMethod.invoke(dtoObject,getterMethod.invoke(o));
        }
        return dtoObject;
    }
    private Object updateObjectFromDTO(Object o, Object dtoObject){
        Class<?> objectClass = o.getClass();
        Field[] objectClassFields = objectClass.getDeclaredFields();
        Method setter = null;
        Method getter = null;
        for(Field field:objectClassFields){
            try{
                setter = objectClass.getDeclaredMethod("set"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1),field.getType());
            } catch(Exception e){
                System.out.println("Setter Not Found: "+e.getMessage());
            }
            try{
                getter = dtoObject.getClass().getDeclaredMethod("get"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1));
            } catch (Exception e){
                System.out.println("Getter Not Found: "+e.getMessage());
            }
            try {
                setter.invoke(o, getter.invoke(dtoObject));
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return o;
    }
}


