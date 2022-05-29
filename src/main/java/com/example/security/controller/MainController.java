package com.example.security.controller;


import com.example.security.entity.User;
import com.example.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class MainController {

    private final UserService userService;

    @GetMapping("/")
    public String getMessage(){
        return "Home";
    }

    @GetMapping("/auth")
    public String auth(Principal principal){

        User user = userService.findByUsername(principal.getName());

        return "Auth - - " + user.getUsername() + " " + user.getEmail() ;
    }

}
