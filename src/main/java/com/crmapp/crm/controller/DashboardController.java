package com.crmapp.crm.controller;

import com.crmapp.crm.entity.UsersEntity;
import com.crmapp.crm.service.DashboardService;
import com.crmapp.crm.service.ProfileService;
import com.crmapp.crm.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    UserService userService;
    @Autowired
    ProfileService profileService;
    @GetMapping("")
    public String dashboard(Model model, HttpSession session){
        UsersEntity users = userService.getUserBySession(session);
        String avatarPath = userService.getPathAvata(users);
        model.addAttribute("avatarPath",avatarPath);
//        UsersEntity user = userService.getUserBySession(session);
//        model.addAttribute("img", user.getAvatarPath());

        int unfulfilled = dashboardService.getTaskUnfulfilled();
        model.addAttribute("unfulfilled", unfulfilled);

        int unfulfilledPercent = dashboardService.getTaskUnfulfilledPercent();
        model.addAttribute("unfulfilledPercent", unfulfilledPercent);

        int processing = dashboardService.getTaskProcessing();
        model.addAttribute("processing", processing);

        int processingPercent = dashboardService.getTaskProcessingPercent();
        model.addAttribute("processingPercent", processingPercent);

        int completed = dashboardService.getTaskCompleted();
        model.addAttribute("completed", completed);

        int completedPercent = dashboardService.getTaskCompletedPercent();
        model.addAttribute("completedPercent", completedPercent);
        return("index");
    }
}
