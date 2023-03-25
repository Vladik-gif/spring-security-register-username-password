package com.vladik.demosecurity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class HomeController {

    private static final String home = "/home";

    @GetMapping(home)
    public String home(){
        return "Hello world!";
    }

}
