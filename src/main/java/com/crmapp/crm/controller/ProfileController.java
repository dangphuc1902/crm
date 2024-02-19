package com.crmapp.crm.controller;

import com.crmapp.crm.entity.StatusEntity;
import com.crmapp.crm.entity.TasksEntity;
import com.crmapp.crm.entity.UsersEntity;
import com.crmapp.crm.service.ProfileService;
import com.crmapp.crm.service.TaskService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private TaskService taskService;
    @GetMapping("/table")
    public String profile(Model model, HttpSession httpSession,
                          HttpServletResponse response){
        UsersEntity usersEntity = profileService.getUserBySession(httpSession);
        model.addAttribute("usersEntity",usersEntity);
        String avatarPath = usersEntity.getAvatarPath();
        model.addAttribute("avatarPath",avatarPath);
        List<TasksEntity> task = taskService.findByUsersEntity(usersEntity);
        model.addAttribute("taskList",task);
        double doNotTask = profileService.calculatePercentage(1);
        model.addAttribute("doNotTask",doNotTask);
        double doingTask = profileService.calculatePercentage(2);
        model.addAttribute("doingTask",doingTask);
        double doneTask = profileService.calculatePercentage(3);
        model.addAttribute("doneTask",doneTask);
        return "profile";
    }

    @GetMapping("/edit")
    public String editProfile(@RequestParam String nameTask,
                              @PathVariable(name = "task_id") int id,
                              @RequestParam("job_id") int job_id,
                              @RequestParam("user_id") int user_id,
                              @RequestParam("status_id") int status_id,
                              @RequestParam(name = "startDate")@DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
                              @RequestParam(name = "endDate")@DateTimeFormat(pattern = "dd-MM-yyyy")  Date endDate,
                              Model model, HttpSession httpSession,
                              HttpServletResponse response){
        UsersEntity usersEntity = profileService.getUserBySession(httpSession);
        model.addAttribute("usersEntity",usersEntity);
        String avatarPath = usersEntity.getAvatarPath();
        model.addAttribute("avatarPath",avatarPath);
        List<TasksEntity> task = taskService.findByUsersEntity(usersEntity);
        model.addAttribute("taskList",task);
        return "profile-edit";
    }

}
