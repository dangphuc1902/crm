package com.crmapp.crm.controller;

import com.crmapp.crm.entity.UsersEntity;
import com.crmapp.crm.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blank")
public class blankController {
    @Autowired
    private UserService userService;
    @GetMapping("")
    public String blank(Model model, HttpSession session){
        UsersEntity users = userService.getUserBySession(session);
        String avatarPath = userService.getPathAvata(users);
        model.addAttribute("avatarPath",avatarPath);
        return "blank";
    }
}
