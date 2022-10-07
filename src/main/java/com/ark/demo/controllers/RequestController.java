package com.ark.demo.controllers;

import com.ark.demo.models.Request;
import com.ark.demo.models.Thread;
import com.ark.demo.models.User;
import com.ark.demo.models.data.RequestRepository;
import com.ark.demo.models.data.ThreadRepository;
import com.ark.demo.models.data.UserDetailsRepository;
import com.ark.demo.models.data.UserRepository;
import com.ark.demo.models.dto.CloseRequestFormDTO;
import com.ark.demo.models.dto.CreateRequestFormDTO;
import com.ark.demo.models.dto.EditRequestFormDTO;
import com.ark.demo.models.enums.PriorityLevel;
import com.ark.demo.models.enums.RequestStatus;
import com.ark.demo.models.enums.RequestType;
import com.ark.demo.services.EmailService;
import com.ark.demo.services.ReadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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
    EmailService emailService;

    @Autowired
    ThreadRepository threadRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @GetMapping()
    public String requestForm(Model model, HttpServletRequest request) {
        User user = authenticationController.getUserFromSession(request.getSession());
        model.addAttribute(user);
        model.addAttribute("title", "Create Request");
        CreateRequestFormDTO createRequestFormDTO = new CreateRequestFormDTO();
        createRequestFormDTO.setUser(user);
        model.addAttribute(createRequestFormDTO);
        model.addAttribute("states",authenticationController.createStatesMap());
        model.addAttribute("types", authenticationController.createTypesMap());
        return "requestTemplates/createRequest";
    }

    @PostMapping()
    public String requestSubmit(@ModelAttribute @Valid CreateRequestFormDTO createRequestFormDTO, Model model, Errors errors, HttpServletRequest request) throws MessagingException {
        User user = authenticationController.getUserFromSession(request.getSession());
        if(errors.hasErrors()){
            model.addAttribute("title", "Create Request");
            model.addAttribute(createRequestFormDTO);
            return "requestTemplates/createRequest";
        }

        Request newRequest = new Request(createRequestFormDTO.getTitle(),createRequestFormDTO.getDescription(), createRequestFormDTO.getAddressLine1(),createRequestFormDTO.getAddressLine2(),createRequestFormDTO.getCity(),createRequestFormDTO.getState(),createRequestFormDTO.getZipcode(),createRequestFormDTO.getDueDate(),createRequestFormDTO.getPublicEvent(),createRequestFormDTO.getLocation(), createRequestFormDTO.getType(),createRequestFormDTO.getLevel(),createRequestFormDTO.getUser());
        newRequest.setPublicEvent(createRequestFormDTO.getPublicEvent());
        newRequest.setUser(user);
        requestRepository.save(newRequest);
        user.addRequest(newRequest);
        userRepository.save(user);
        String text = String.format(ReadFile.readFile("src/main/resources/templates/mailTemplates/requestConfirmationEmail.html"),
                newRequest.getTitle(),
                newRequest.getPublicEvent(),
                newRequest.getStatus(),
                newRequest.getDueDate(),
                newRequest.getAddressLine1(),
                newRequest.getAddressLine2(),
                newRequest.getCity(),
                newRequest.getState(),
                newRequest.getZipcode(),
                newRequest.getDescription());
        emailService.sendMail(user.getUserDetails().getEmailAddress(),text,"New Request Created");
        return "redirect:request/requestConfirmation";
    }


    @GetMapping("userRequests")
    public String allUserRequests(HttpServletRequest request, Model model){
        User user = authenticationController.getUserFromSession(request.getSession());
        model.addAttribute(user);
        model.addAttribute("title", "User Requests");
        model.addAttribute("requests", requestRepository.findByUserId(user.getId()));
        model.addAttribute(new CloseRequestFormDTO());
        return "requestTemplates/userRequests";
    }
    @PostMapping("userRequests")
    public String showRequestsByUser(HttpServletRequest request, Model model){
        User user = authenticationController.getUserFromSession(request.getSession());
        model.addAttribute(user);
        model.addAttribute("title", "User Requests");
        model.addAttribute("requests", requestRepository.findByUserId(user.getId()));
        return "requestTemplates/userRequests";
    }
    @PostMapping("edit")
    public String editRequest(@RequestParam("requestId") Integer requestId, HttpServletRequest request, Model model) throws InvocationTargetException, IllegalAccessException {
        User user = authenticationController.getUserFromSession(request.getSession());
        model.addAttribute(user);
        Request updateRequest = requestRepository.findById(requestId).get();
        EditRequestFormDTO editRequestFormDTO = new EditRequestFormDTO();
        model.addAttribute("editRequestFormDTO",sendObjectToDTO(updateRequest,  editRequestFormDTO));
        model.addAttribute("levels", PriorityLevel.values());
        model.addAttribute("types",RequestType.values());
        model.addAttribute("states",authenticationController.createStatesMap());
        model.addAttribute("statuses",createStatuses());
        CloseRequestFormDTO closeRequestFormDTO = new CloseRequestFormDTO();
        closeRequestFormDTO.setRequestId(requestId);
        model.addAttribute(closeRequestFormDTO);
        return "requestTemplates/editRequest";
    }
    @PostMapping("edit/process")
    public String processEditRequestForm(@ModelAttribute @Valid EditRequestFormDTO editRequestFormDTO, @ModelAttribute CloseRequestFormDTO closeRequestFormDTO, Errors errors, HttpServletRequest request, Model model) throws MessagingException {
        User user = authenticationController.getUserFromSession(request.getSession());
        model.addAttribute(user);
        if(errors.hasErrors()){
            model.addAttribute("title","Edit Request");
            model.addAttribute(editRequestFormDTO);
            model.addAttribute("states",authenticationController.createStatesMap());
            model.addAttribute("statuses",createStatuses());
            return "requestTemplates/editRequest";
        }
        Integer editRequestId = editRequestFormDTO.getId();
        Request editRequest = requestRepository.findById(editRequestId).get();


        requestRepository.save((Request) updateObjectFromDTO(editRequest,editRequestFormDTO));

        if(closeRequestFormDTO.getCloseType().equals("resolved")){
            model.addAttribute("request",editRequest);
            List<Thread> threads = threadRepository.findAllByRequestId(editRequestId);
            List<User> usersFromThreads = new ArrayList<>();
            if(threads.isEmpty()){
                return "redirect:/";
            }
            for(Thread thread : threads){
                usersFromThreads.add(thread.getUser());
            }
            //send threadUsers to template
            model.addAttribute("threadUsers", usersFromThreads);


            return "requestTemplates/showGratitude";
        }
        String text = String.format(ReadFile.readFile("src/main/resources/templates/mailTemplates/requestConfirmationEmail.html"),
                editRequest.getTitle(),
                editRequest.getPublicEvent(),
                editRequest.getStatus(),
                editRequest.getDueDate(),
                editRequest.getAddressLine1(),
                editRequest.getAddressLine2(),
                editRequest.getCity(),
                editRequest.getState(),
                editRequest.getZipcode(),
                editRequest.getDescription());
        emailService.sendMail(user.getUserDetails().getEmailAddress(),text,"Request Updated");
        return "redirect:/request/userRequests";
    }

    @PostMapping("edit/save")
    public String sendGratitude(HttpServletRequest request, @RequestParam("recipients") String[] recipientIds, @RequestParam("thankyou") String cardSelection, Model model){

        User user = authenticationController.getUserFromSession(request.getSession());
        model.addAttribute(user);
        for(String id : recipientIds){
            Integer idInt = Integer.parseInt(id);
            User recipientUser = userRepository.findById(idInt).get();

            recipientUser.getUserDetails().addGratitudeCard("/images/thankYouCards/" + cardSelection +  "Preview.jpg");
            userRepository.save(recipientUser);

        }
        return "redirect:/";

    }


    @GetMapping("requestConfirmation")
    public String displayRequestConfirmation(HttpServletRequest request, Model model){
        User user = authenticationController.getUserFromSession(request.getSession());
        model.addAttribute(user);
        model.addAttribute("title", "Request Confirmation");
        model.addAttribute("request", requestRepository.findLastRequestByUserId(user.getId()));
        return "requestTemplates/requestConfirmation";
    }

    @GetMapping("viewRequest/{requestId}")
    public String viewRequest(@PathVariable("requestId") Integer id,HttpServletRequest request, Model model){
        User user = authenticationController.getUserFromSession(request.getSession());
        model.addAttribute(user);
        Request viewRequest = requestRepository.findById(id).get();
        List<Thread> threadList = new ArrayList<>();
        model.addAttribute("request",viewRequest);

        if(user.getId() == viewRequest.getUser().getId()){
            threadList = threadRepository.findAllByRequestId(id);

//            this is where were not getting to request
        }else{
            for( Thread thread : viewRequest.getThreads()){
                if(user.getId() == thread.getUser().getId()){
                    threadList.add(thread);
                }
            }
        }
        model.addAttribute("threadList", threadList);


        return "requestTemplates/viewRequest";
    }
    @PostMapping("close")
    public String closeRequest(@RequestParam Integer requestId,HttpServletRequest request,Model model){
        User user = authenticationController.getUserFromSession(request.getSession());
        model.addAttribute(user);
        model.addAttribute("title","Close Request");
        CloseRequestFormDTO closeRequestFormDTO = new CloseRequestFormDTO();
        closeRequestFormDTO.setRequestId(requestId);
        model.addAttribute(closeRequestFormDTO);

        return "requestTemplates/closeRequest";
    }
    @PostMapping("closeRequest")
    public String closeRequestFinal(@RequestParam Integer requestId, @RequestParam String closeType,HttpServletRequest request, Model model){
        User user = authenticationController.getUserFromSession(request.getSession());
        model.addAttribute(user);
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
                System.out.println("Object to DTO: Declared Setter Field: " + declaredField.getName());
                System.out.println("Object to DTO: Setter Not Found: "+e.getMessage());
            }
            try{
                if(declaredField.getName() == "id"){
                    getterMethod = objectClass.getSuperclass().getDeclaredMethod("getId");
                } else {
                    getterMethod = objectClass.getDeclaredMethod("get"+declaredField.getName().substring(0,1).toUpperCase()+declaredField.getName().substring(1));
                }
            } catch (Exception e){
                System.out.println("Object to DTO: Declared Getter Field: " + declaredField.getName());
                System.out.println("Object to DTO: Getter Not Found: "+e.getMessage());
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
                System.out.println("Object from DTO: Declared Setter Field: " + field.getName());
                System.out.println("Object from DTO: Setter Not Found: "+e.getMessage());
            }
            try{
                getter = dtoObject.getClass().getDeclaredMethod("get"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1));
            } catch (Exception e){
                System.out.println("Object from DTO: Declared Getter Field: " + field.getName());
                System.out.println("Object from DTO: Getter Not Found: "+e.getMessage());
            }
            try {
                setter.invoke(o, getter.invoke(dtoObject));
            } catch (Exception e){
                System.out.println("Object from DTO: Declared Invocation Field: " + field.getName());
                System.out.println("Object from DTO: Invocation Error: "+e.getMessage());
            }
        }
        return o;
    }
}


