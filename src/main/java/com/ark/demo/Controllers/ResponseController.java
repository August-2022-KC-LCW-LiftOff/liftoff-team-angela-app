package com.ark.demo.Controllers;

import com.ark.demo.models.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("response")
public class ResponseController {
//    @Autowired
//    private ResponseRepository responseRepository;
    public ArrayList<Response> response = new ArrayList<>();

    @GetMapping("create")
    public String displayResponseForm(Model model) {
        model.addAttribute("title", "Response Form");
        model.addAttribute(new Response());
        return "response/create";
    }
    @PostMapping
    public String processResponseForm (@ModelAttribute Response newResponse, Model model){
        model.addAttribute("title", "Response Sent");
//        response.add(newResponse);
        return "response/viewResponse";
    }


}
