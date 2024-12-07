package org.example.dipl.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainController {
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "Головна сторінка");
        return "home";
    }

}
