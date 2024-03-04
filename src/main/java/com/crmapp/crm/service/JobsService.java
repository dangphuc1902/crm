package com.crmapp.crm.service;

import com.crmapp.crm.entity.JobsEntity;
import com.crmapp.crm.entity.TasksEntity;
import com.crmapp.crm.entity.UsersEntity;
import com.crmapp.crm.repository.JobsReponsitory;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobsService {

    @Autowired
    private JobsReponsitory jobsReponsitory;
@Autowired
private UserService userService;

    public List<JobsEntity> getAlljob() {
        return jobsReponsitory.findAll();
    }
    public List<JobsEntity> getJobByRole(HttpSession session) {
        List<JobsEntity> jobs = new ArrayList<>(List.of());
        UsersEntity users =userService.getUserBySession(session);
        if(users.getRolesEntity().getName().equals("ROLE_ADMIN")){
            return jobsReponsitory.findAll();
        } else {
            List<TasksEntity> tasksEntities = users.getTasks();
                    ;
            tasksEntities.forEach(item -> jobs.add(item.getJobsEntity()));
        }
        return jobs.stream().distinct().collect(Collectors.toList());
    }

    public JobsEntity getJobById(int job_id) {
        Optional<JobsEntity> jobsEntity = jobsReponsitory.findById(job_id);
        JobsEntity datajobs = null;
        if (jobsEntity.isPresent()) {
            datajobs = jobsEntity.get();
        }
        return datajobs;
    }
    public int getTaskUnfulfilled(JobsEntity job) {
        List<TasksEntity> tasksEntities = job.getTasks();
        float quantity = 0;
        for (TasksEntity tasks : tasksEntities){
            if(tasks.getStatusEntity().getName().equals("Chưa thực hiện")){
                quantity += 1;
            }
        }
        if(quantity == 0){
            return 0;
        }else{
            return (int)(quantity/(float)tasksEntities.size()*100);
        }
    }
    public int getTaskProcessing(JobsEntity job) {
        List<TasksEntity> tasksEntities = job.getTasks();
        float quantity = 0;
        for (TasksEntity tasks : tasksEntities){
            if(tasks.getStatusEntity().getName().equals("Đang thực hiện")){
                quantity += 1;
            }
        }
        if(quantity == 0){
            return 0;
        }else{
            return (int)(quantity/(float)tasksEntities.size()*100);
        }
    }
    public int getTaskCompleted(JobsEntity job) {
        List<TasksEntity> tasksEntities = job.getTasks();
        float quantity = 0;
        for (TasksEntity tasks : tasksEntities){
            if(tasks.getStatusEntity().getName().equals("Đã hoàn thành")){
                quantity += 1;
            }
        }
        if(quantity == 0){
            return 0;
        }else{
            return (int)(quantity/(float)tasksEntities.size()*100);
        }
    }
    public String getCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        return formattedTime ;
    }
    public List<UsersEntity> getUserByTask(List<TasksEntity> listTask){

        return listTask.stream().map(TasksEntity::getUsersEntity).distinct().collect(Collectors.toList());
    }

    public List<JobsEntity> getJobForUpdate(HttpSession session,TasksEntity TasksEntity){
        UsersEntity users = userService.getUserBySession(session);
        if(users.getRolesEntity().getName().equals("ROLE_USER")){
            return Collections.singletonList(TasksEntity.getJobsEntity());
        }else return getJobByRole(session);
    }

    public boolean checkNameJob(String nameJob){
        List<JobsEntity> jobsEntities = jobsReponsitory.findAll();
        return jobsEntities.stream().anyMatch(jobs -> jobs.getName().equalsIgnoreCase(nameJob));
    }

    public boolean saveJob(JobsEntity jobs) {
        boolean isSuccess = false;

        if (!checkNameJob(jobs.getName())){
            try {
                jobsReponsitory.save(jobs);
                isSuccess = true;
            }catch (Exception e) {
                System.out.println("Lỗi Thêm dữ lệu" + e.getMessage());
            }
        }else {
            System.out.println("Email đã tồn tại");
        }
        return isSuccess;
    }

    public void deletJobById(int id) {
        jobsReponsitory.deleteById(id);
    }

    public boolean updatejob(int id, String nameProject, Date startDay, Date endDay) {
        boolean isSuccess = false;
        JobsEntity jobs = new JobsEntity();
        jobs.setId(id);
        jobs.setName(nameProject);
        jobs.setStartDate(startDay);
        jobs.setEndDate(endDay);


        if (nameProject.equalsIgnoreCase(jobs.getName()) || !checkNameJob(nameProject)){
            try {
                jobsReponsitory.save(jobs);
                isSuccess = true;
            }catch (Exception e) {
                System.out.println("Lỗi Thêm dữ lệu" + e.getMessage());
            }
        }else {
            System.out.println("Email đã tồn tại");
        }
        return isSuccess;

    }
}