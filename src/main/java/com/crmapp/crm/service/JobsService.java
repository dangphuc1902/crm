package com.crmapp.crm.service;

import com.crmapp.crm.entity.JobsEntity;
import com.crmapp.crm.entity.UsersEntity;
import com.crmapp.crm.repository.JobsReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class JobsService {

    @Autowired
    private JobsReponsitory jobsReponsitory;


    public List<JobsEntity> getAlljob() {
        return jobsReponsitory.findAll();
    }

    public JobsEntity getJobById(int job_id) {
        Optional<JobsEntity> jobsEntity = jobsReponsitory.findById(job_id);
        JobsEntity datajobs = null;
        if (jobsEntity.isPresent()) {
            datajobs = jobsEntity.get();
        }
        return datajobs;
    }

//    public List<UsersEntity> getUserByTask(List<JobsEntity> listTask){
//
//        return listTask.stream().map(TasksEntity::getUsersEntity).distinct().collect(Collectors.toList());
//    }

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