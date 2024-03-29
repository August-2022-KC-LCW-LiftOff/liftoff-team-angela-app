package com.ark.demo.controllers;

import com.ark.demo.models.User;
import com.ark.demo.models.UserDetails;
import com.ark.demo.models.data.UserDetailsRepository;
import com.ark.demo.models.data.UserRepository;
import com.ark.demo.models.dto.DeleteFormDTO;
import com.ark.demo.models.dto.EditProfileFormDTO;
import com.ark.demo.models.dto.UpdatePasswordFormDTO;
import com.ark.demo.models.dto.ViewProfileDTO;
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
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public String displayUserProfile(HttpServletRequest request, Model model){
        User user = authenticationController.getUserFromSession(request.getSession());
        ViewProfileDTO viewProfileDTO = new ViewProfileDTO();
        viewProfileDTO.setUserDetails(user.getUserDetails());
        viewProfileDTO.setDateCreated(formatDateAsString(user.getDateCreated()));
        model.addAttribute(user);
        model.addAttribute("title","View Profile");
        model.addAttribute(viewProfileDTO);
        return "userTemplates/viewProfile";
    }
    @GetMapping("/editProfile")
    public String displayEditProfileForm(HttpServletRequest request, Model model){
        User user = authenticationController.getUserFromSession(request.getSession());
        model.addAttribute("title","Edit Profile");
        EditProfileFormDTO editProfileFormDTO = new EditProfileFormDTO();
        UserDetails userDetails = user.getUserDetails();
        editProfileFormDTO.setUserDetails(userDetails);
        editProfileFormDTO.setIcon(userDetails.getIcon());
        String[] names = new String[]{"alien","angel","basic_guy","billy","borg","bricky","camouflage","candy","chef","Citizen-Stone-Age","cowboy","dandy","devil","geek","geisha","girl","ninja","pirate","princess","punker","squared","stripey","sunglasses","warrior-man-at-arm"};
        model.addAttribute("names",names);
        model.addAttribute(editProfileFormDTO);
        model.addAttribute("states",authenticationController.createStatesMap());
        return "userTemplates/editProfile";
    }

    @PostMapping("/editProfile")
    public String processUpdatePasswordForm(@ModelAttribute @Valid EditProfileFormDTO editProfileFormDTO, Errors errors, HttpServletRequest request, Model model) throws MessagingException, UnsupportedEncodingException {
        if(errors.hasErrors()){
            model.addAttribute("title","Edit Profile");
            model.addAttribute(editProfileFormDTO);
            return "userTemplates/editProfile";
        }
        User user = authenticationController.getUserFromSession(request.getSession());

        UserDetails userDetails = user.getUserDetails();
        userDetails.setFirstName(editProfileFormDTO.getUserDetails().getFirstName());
        userDetails.setLastName(editProfileFormDTO.getUserDetails().getLastName());
        userDetails.setAddressLine1(editProfileFormDTO.getUserDetails().getAddressLine1());
        userDetails.setAddressLine2(editProfileFormDTO.getUserDetails().getAddressLine2());
        userDetails.setCity(editProfileFormDTO.getUserDetails().getCity());
        userDetails.setState(editProfileFormDTO.getUserDetails().getState());
        userDetails.setZipcode(editProfileFormDTO.getUserDetails().getZipcode());
        userDetails.setPhoneNumber(editProfileFormDTO.getUserDetails().getPhoneNumber());
        if(!userDetails.getEmailAddress().equals(editProfileFormDTO.getUserDetails().getEmailAddress())){
            userDetails.setUid(editProfileFormDTO.getUserDetails().getEmailAddress());
            userDetails.setEmailVerified(false);
            emailService.sendMail(editProfileFormDTO.getUserDetails().getEmailAddress(), String.format(ReadFile.readFile("src/main/resources/templates/mailTemplates/updatedEmailAddressEmail.html"),userDetails.getUid()),"E-mail Address Updated");
        }
        userDetails.setEmailAddress(editProfileFormDTO.getUserDetails().getEmailAddress());
        userDetails.setIcon(editProfileFormDTO.getIcon());
        user.setLocation(editProfileFormDTO.getLocation());
        userRepository.save(user);
        userDetailsRepository.save(userDetails);
        return "redirect:../user";
    }

    @GetMapping("/updatePassword")
    public String displayUpdatePasswordForm(HttpServletRequest request,Model model){
        User user = authenticationController.getUserFromSession(request.getSession());
        model.addAttribute("title","Update Password");
        UpdatePasswordFormDTO updatePasswordFormDTO = new UpdatePasswordFormDTO();
        updatePasswordFormDTO.setUser(user);
        model.addAttribute(updatePasswordFormDTO);
        return "userTemplates/updatePassword";
    }

    @PostMapping("/updatePassword")
    public String processUpdatePasswordForm(@ModelAttribute @Valid UpdatePasswordFormDTO updatePasswordFormDTO, Errors errors, HttpServletRequest request, Model model){
        if(errors.hasErrors()){
            model.addAttribute("title","Update Password");
            model.addAttribute(updatePasswordFormDTO);
            return "userTemplates/updatePassword";
        }
        User user = authenticationController.getUserFromSession(request.getSession());
        if(!updatePasswordFormDTO.getPassword().equals(updatePasswordFormDTO.getVerifyPassword())){
            errors.rejectValue("password","password.mismatch","Passwords do not match");
            model.addAttribute("title","Update Password");
            model.addAttribute(updatePasswordFormDTO);
            return "userTemplates/updatePassword";
        }
        user.setPwHash(updatePasswordFormDTO.getPassword());
        userRepository.save(user);
        model.addAttribute("formResponse","Password Updated.");
        return "userTemplates/updatePassword";
    }

    @GetMapping("/deleteProfile")
    public String displayDeleteProfileForm(HttpServletRequest request, Model model){
        User user = authenticationController.getUserFromSession(request.getSession());
        model.addAttribute("title","Delete Account");
        DeleteFormDTO deleteFormDTO = new DeleteFormDTO();
        deleteFormDTO.setUser(user);
        deleteFormDTO.setUserDetails(user.getUserDetails());
        model.addAttribute(deleteFormDTO);
        return "userTemplates/deleteProfile";
    }

    @PostMapping("/deleteProfile")
    public String processDeleteProfileForm(@ModelAttribute @Valid DeleteFormDTO deleteFormDTO, HttpServletRequest request){
        if(deleteFormDTO.getConfirm().toLowerCase().equals("yes")){
            userDetailsRepository.delete(deleteFormDTO.getUserDetails());
            userRepository.delete(deleteFormDTO.getUser());
            return "userTemplates/accountDeleted";
        }
        return "redirect:../user";
    }
    private String formatDateAsString(Date date){
        String pattern = "MMMM dd, yyyy hh:mm a";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

}
