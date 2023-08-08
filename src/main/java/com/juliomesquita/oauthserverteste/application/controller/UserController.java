package com.juliomesquita.oauthserverteste.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/login")
public class UserController {
    @GetMapping
    public String getHomePage(){
        return "index";
    }

    @GetMapping(path = "/logout")
    public String logout(){

        return "index";
    }
}
