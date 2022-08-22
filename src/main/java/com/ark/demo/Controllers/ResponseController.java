package com.ark.demo.Controllers;

import com.ark.demo.models.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("response")
public class ResponseController {
//    @Autowired
//    private ResponseRepository responseRepository;
    private static List<String> response = new ArrayList<>();

    @GetMapping("index")
    public String index(){
        return "response/index";
    }
    @GetMapping("create")
    public String displayResponseForm() {
        return "response/create";
    }

    @PostMapping("create")
    public String createResponse(@RequestParam String responseMessage){
        response.add(responseMessage);
    return "redirect:/response/viewResponse";
    }
    @GetMapping("viewResponse")
    public String displayResponseConformation(Model model) {
        model.addAttribute("response", response);
        return "response/viewResponse";

    }
}
