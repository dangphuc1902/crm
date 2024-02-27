package com.crmapp.crm.controller;

import com.crmapp.crm.entity.JobsEntity;
import com.crmapp.crm.entity.StatusEntity;
import com.crmapp.crm.entity.TasksEntity;
import com.crmapp.crm.entity.UsersEntity;
import com.crmapp.crm.service.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private JobsService jobsService;
    @Autowired
    private UserService userService;
    @Autowired
    private StatusService statusService;
    @GetMapping("/table")
    public String profile(Model model, HttpSession httpSession,
                          HttpServletResponse response){
        UsersEntity usersEntity = profileService.getUserBySession(httpSession);
        model.addAttribute("usersEntity",usersEntity);
        String avatarPath = usersEntity.getAvatarPath();
        httpSession.setAttribute("avatarPath",avatarPath);
        httpSession.setMaxInactiveInterval(8*60*60);
        System.out.println("Kiểm tra avatar Path: " + avatarPath);
        model.addAttribute("avatarPath",avatarPath);
        List<TasksEntity> task = taskService.findByUsersEntity(usersEntity);
        model.addAttribute("taskList",task);
        profileService.jobNumber(model);
        return "profile";
    }

    @GetMapping("/update/{task_id}")
    public String getUpdateTask(@PathVariable(name = "task_id") int id, Model model){
    TasksEntity tasksEntity = taskService.getTaskById(id);
    model.addAttribute("tasksEntity",tasksEntity);
    List<JobsEntity> listJobs = jobsService.getAlljob();
    model.addAttribute("listJobs",listJobs);
    List<UsersEntity> listUser = userService.getAllUser();
    model.addAttribute("listUser",listUser);
    List<StatusEntity> statusEntities = statusService.getAllStatus();
    model.addAttribute("statusEntitie", statusEntities);
    return "profile-edit";
    }
    @PostMapping("/update/{task_id}")
    public String processUpdateTask(@RequestParam String nameTask,
                                    @RequestParam("description") String description,
                                    @PathVariable(name = "task_id") int id,
                                    @RequestParam("job_id") int job_id,
                                    @RequestParam("user_id") int user_id,
                                    @RequestParam("status_id") int status_id,
                                    @RequestParam(name = "startDate")@DateTimeFormat(pattern = "dd-MM-yyyy")  Date startDate,
                                    @RequestParam(name = "endDate")@DateTimeFormat(pattern = "dd-MM-yyyy")  Date endDate,
                                    Model model){
        JobsEntity jobs = jobsService.getJobById(job_id);
        UsersEntity users = userService.getUserId(user_id);
        StatusEntity status = statusService.getStatusById(status_id);
        boolean checkConditions = taskService.checkConditions(nameTask,description,users,jobs);
        model.addAttribute("checkConditions",checkConditions);
        boolean checkConditionsDate = taskService.checkConditionsDate(jobs,startDate,endDate);
        model.addAttribute("checkConditionsDate",checkConditionsDate);
        boolean isProcessAddSucces = taskService.processUpdateTask(id,nameTask,description,jobs,users,startDate,endDate,status);
        model.addAttribute("isProcessAddSucces",isProcessAddSucces);
        return "redirect:/profile/table";
    }
}
