package com.crmapp.crm.controller;

import com.crmapp.crm.entity.JobsEntity;
import com.crmapp.crm.entity.StatusEntity;
import com.crmapp.crm.entity.TasksEntity;
import com.crmapp.crm.entity.UsersEntity;
import com.crmapp.crm.service.JobsService;
import com.crmapp.crm.service.StatusService;
import com.crmapp.crm.service.TaskService;
import com.crmapp.crm.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private JobsService jobsService;
    @Autowired
    private UserService userService;
    @Autowired
    private StatusService statusService;
    @GetMapping("/table")
    public String showTaskTable(Model model){
        List<TasksEntity> taskList = taskService.getAllTask();
        model.addAttribute("taskList",taskList);
        return "task";
    }

    @GetMapping("/add")
    public String addTask(Model model){
        List<JobsEntity> listJobs = jobsService.getAlljob();
        model.addAttribute("listJobs",listJobs);
        List<UsersEntity> listUser = userService.getAllUser();
        model.addAttribute("listUser",listUser);
        List<StatusEntity> statusEntities = statusService.getAllStatus();
        model.addAttribute("statusEntitie", statusEntities);
        return "task-add";
    }

    @PostMapping("/add")
    public String processAddTask(@RequestParam String nameTask,
                                 @RequestParam(name = "description") String description,
                                 @RequestParam("job_id") int job_id,
                                 @RequestParam("user_id") int user_id,
                                 @RequestParam("status_id") int status_id,
                                 @RequestParam(name = "startDate")@DateTimeFormat(pattern = "dd-MM-yyyy")  Date startDate,
                                 @RequestParam(name = "endDate")@DateTimeFormat(pattern = "dd-MM-yyyy")  Date endDate,
                                 Model model){
        List<JobsEntity> listJobs = jobsService.getAlljob();
        model.addAttribute("listJobs",listJobs);
        List<UsersEntity> listUser = userService.getAllUser();
        model.addAttribute("listUser",listUser);
        List<StatusEntity> statusList = statusService.getAllStatus();
        model.addAttribute("nameTask", nameTask);
        model.addAttribute("description",description);
        JobsEntity jobs = jobsService.getJobById(job_id);
        UsersEntity users = userService.getUserId(user_id);
        StatusEntity status = statusService.getStatusById(status_id);
        boolean checkConditions = taskService.checkConditions(nameTask,description,users,jobs);
        model.addAttribute("checkConditions",checkConditions);
        boolean checkConditionsDate = taskService.checkConditionsDate(jobs,startDate,endDate);
        model.addAttribute("checkConditionsDate",checkConditionsDate);
        boolean isProcessAddSucces = taskService.processAddTask(nameTask,description,jobs,users,startDate,endDate,status);
        model.addAttribute("isProcessAddSucces",isProcessAddSucces);
        return "task-add";
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
        return "task-update";
    }
    @PostMapping("/update/{task_id}")
    public String processUpdateTask(@RequestParam String nameTask,
                                    @RequestParam(name = "description") String description,
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
        return "redirect:/task/table";
    }

    @GetMapping("/delete/{task_id}")
    public String deleteTask(@PathVariable(name = "task_id") int id, Model model){
        taskService.deleteTask(id);
        return "redirect:/task/table";
    }

//    @GetMapping("/details/{task_id}")
//    public String taskDel
}