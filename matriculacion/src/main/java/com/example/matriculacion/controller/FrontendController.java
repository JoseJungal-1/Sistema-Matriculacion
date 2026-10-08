package com.example.matriculacion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    @GetMapping({"/", "/login"})
    public String index() {
        return "forward:/index.html";
    }
}
