package com.ark.demo.controllers;

import com.ark.demo.models.User;
import com.ark.demo.models.UserDetails;
import com.ark.demo.models.data.UserDetailsRepository;
import com.ark.demo.models.data.UserRepository;
import com.ark.demo.models.dto.LoginFormDTO;
import com.ark.demo.models.dto.RegistrationFormDTO;
import com.ark.demo.models.enums.RequestType;
import com.ark.demo.models.enums.USStates;
import com.ark.demo.models.mail.EmailTemplateStrings;
import com.ark.demo.services.EmailService;
import com.ark.demo.services.ReadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.TreeMap;

import static java.util.Objects.isNull;

@Controller
@RequestMapping()
public class AuthenticationController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    EmailService emailService;

    private static final String userSessionKey = "user";

    private final TreeMap<String,String> states = createStatesMap();

    public User getUserFromSession(HttpSession session){
        Integer userID = (Integer) session.getAttribute(userSessionKey);
        if(userID == null){
            return null;
        }
        Optional<User> user = userRepository.findById(userID);
        if(user.isEmpty()){
            return null;
        }
        return user.get();
    }

    private static void setUserInSession(HttpSession session, User user){
        session.setAttribute(userSessionKey, user.getId());
    }

    @GetMapping("/register")
    public String displayRegistrationForm(HttpServletRequest request, Model model){
        User signedIn = getUserFromSession(request.getSession());
        if(!isNull(signedIn)){
            return "redirect:";
        }
        model.addAttribute(new RegistrationFormDTO());
        model.addAttribute("title","Register");

        model.addAttribute("states",states);
        return "userTemplates/register";
    }
    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute @Valid RegistrationFormDTO registrationFormDTO, Errors errors, HttpServletRequest request, Model model) throws MessagingException, UnsupportedEncodingException {
        if(errors.hasErrors()){
            model.addAttribute("title","Register");
            model.addAttribute(registrationFormDTO);
            model.addAttribute("states",states);
            return "userTemplates/register";
        }

        User existingUser = userRepository.findByUsername(registrationFormDTO.getUsername());
        if(existingUser != null){
            errors.rejectValue("username","username.alreadyexists","A user with that username already exists");
            model.addAttribute("title","Register");
            model.addAttribute(registrationFormDTO);
            model.addAttribute("states",states);
            return "userTemplates/register";
        }

        String password = registrationFormDTO.getPassword();
        String verifyPassword = registrationFormDTO.getVerifyPassword();
        if(!password.equals(verifyPassword)){
            errors.rejectValue("password","password.mismatch","Passwords do not match");
            model.addAttribute("title","Register");
            model.addAttribute(registrationFormDTO);
            model.addAttribute("states",states);
            return "userTemplates/register";
        }

        User newUser = new User(registrationFormDTO.getUsername(),registrationFormDTO.getPassword(), registrationFormDTO.getLocation());
        UserDetails newUserDetails = new UserDetails(registrationFormDTO.getFirstName(),registrationFormDTO.getLastName(),registrationFormDTO.getAddressLine1(),registrationFormDTO.getAddressLine2(), registrationFormDTO.getCity(),registrationFormDTO.getState(),registrationFormDTO.getZipcode(), registrationFormDTO.getEmailAddress(),registrationFormDTO.getPhoneNumber());
        newUser.setUserDetails(newUserDetails);
        newUserDetails.setUid(registrationFormDTO.getEmailAddress());
        newUserDetails.setEmailVerified(false);
        userDetailsRepository.save(newUserDetails);
        userRepository.save(newUser);
        setUserInSession(request.getSession(),newUser);
        emailService.sendMail(newUserDetails.getEmailAddress(), String.format(new EmailTemplateStrings().getRegistrationEmail(),newUserDetails.getUid()),"Verify E-mail Address");
        return "redirect:/";
    }
    @GetMapping("/login")
    public String displayLoginForm(Model model){
        model.addAttribute("title","Login");
        model.addAttribute(new LoginFormDTO());
        return "userTemplates/login";
    }

    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO, Errors errors, HttpServletRequest request, Model model){
        if(errors.hasErrors()){
            model.addAttribute("title","Login");
            model.addAttribute(loginFormDTO);
            return "userTemplates/login";
        }

        User validUser = userRepository.findByUsername(loginFormDTO.getUsername());
        if(isNull(validUser)){
            errors.rejectValue("username","username.doesnotexist","Incorrect Username or Password");
            model.addAttribute("title","Login");
            model.addAttribute(loginFormDTO);
            return "userTemplates/login";
        }

        String password = loginFormDTO.getPassword();
        if(!validUser.isMatchingPassword(password)){
            errors.rejectValue("username","password.mismatch","Incorrect Username or Password");
            model.addAttribute("title","Login");
            model.addAttribute(loginFormDTO);
            return "userTemplates/login";
        }
        setUserInSession(request.getSession(),validUser);
        return "redirect:/";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/";
    }
    public TreeMap<String, String> createStatesMap(){
        TreeMap<String, String> states = new TreeMap<>();
        for (USStates state : USStates.values()){
            states.put(state.toString(), state.getDisplayName());
        }
        return states;
    }

    public TreeMap<String, String> createTypesMap(){
        TreeMap<String, String> types = new TreeMap<>();
        for (RequestType type : RequestType.values()){
            types.put(type.toString(), type.getDisplayName());
        }
        return types;
    }
}
