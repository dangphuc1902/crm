package com.crmapp.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @GetMapping("/table")
    public String profile(){
        return "profile";
    }
    @GetMapping("/edit")
    public String editProfile(){
        return "profile-edit";
    }
}
