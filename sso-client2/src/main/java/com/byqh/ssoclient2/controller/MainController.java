package com.byqh.ssoclient2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("main")
    public String goToMain() {
        return "main.html";
    }
}
