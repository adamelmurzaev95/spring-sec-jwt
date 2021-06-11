package com.adamelmurzaev.springsecurirysuleymanov.controller;

import com.adamelmurzaev.springsecurirysuleymanov.security.SecurityUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/success")
    public String success(Model model){
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", securityUser);
        return "success";
    }
}
