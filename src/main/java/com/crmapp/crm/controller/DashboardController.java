package com.crmapp.crm.controller;

import com.crmapp.crm.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    ProfileService profileService;
    @GetMapping("")
    public String dashboard(Model model){
        profileService.jobNumber(model);
        return("index");
    }
}
