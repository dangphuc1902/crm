package com.crmapp.crm.controller;

import com.crmapp.crm.entity.JobsEntity;
import com.crmapp.crm.service.JobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/jobs")
public class JobsController {
    @Autowired
    private JobsService jobsService;

//    Display show table Jobs
    @GetMapping("/table")
    public String JobsTable(Model model){
        List<JobsEntity> jobsEntity = jobsService.getAlljob();
        model.addAttribute("jobs",jobsEntity);
        return "groupwork";
    }
    //    Display add work
    @GetMapping("/add")
    public String addWork(Model model){
        return "groupwork-add";
    }

    @PostMapping("/add")
    public String processAdd(@RequestParam(name = "nameProject") String nameProject,
                             @RequestParam(name = "startDate")@DateTimeFormat(pattern = "dd-MM-yyyy")  Date startDate,
                             @RequestParam(name = "endDate")@DateTimeFormat(pattern = "dd-MM-yyyy")  Date endDate,
                             Model model){
        JobsEntity jobs = new JobsEntity();
        jobs.setName(nameProject);
        jobs.setStartDate(startDate);
        jobs.setEndDate(endDate);
        boolean idSuccess = jobsService.saveJob(jobs);
        model.addAttribute("idSuccess",idSuccess);
        return "groupwork-add";
    }
    @GetMapping("/update/{job_id}")
    public String getUpdateWork(@PathVariable(name = "job_id") int id, Model model){
        JobsEntity jobsEntity = jobsService.getJobById(id);
        model.addAttribute("jobsEntity", jobsEntity);
        return "groupwork-update";
    }

    @PostMapping("/update/{job_id}")
    public String processUpdate(@PathVariable(name = "job_id") int id,
                                @RequestParam(name = "name") String name,
                                @RequestParam(name = "startDate")@DateTimeFormat(pattern = "dd-MM-yyyy")  Date startDate,
                                @RequestParam(name = "endDate")@DateTimeFormat(pattern = "dd-MM-yyyy")  Date endDate,
                                Model model){
        List<JobsEntity> jobsEntity = jobsService.getAlljob();
        model.addAttribute("name",name);
        model.addAttribute("startDate",startDate);
        model.addAttribute("endDate",endDate);
        model.addAttribute("jobsEntity",jobsEntity);
        boolean isUpdateSuccess = jobsService.updatejob(id,name,startDate,endDate);
        model.addAttribute("isUpdateSuccess", isUpdateSuccess);
        return "redirect:/jobs/table";
    }
    // function delete
    @GetMapping("/delete/{job_id}")
    public String  deleteItem(@PathVariable(name = "job_id") Integer id) {
        jobsService.deletJobById(id);
        return "redirect:/jobs/table"; // Chuyển hướng người dùng về trang role-table.html sau khi xóa thành công
    }

//    Display details

    @GetMapping("/details/{job_id}")
    public String processDeltails(@PathVariable(name = "job_id") int id, Model model){
        List<JobsEntity> listJobs = jobsService.getAlljob();
        model.addAttribute("listJobs",listJobs);
        return "groupwork-details";
    }
}
