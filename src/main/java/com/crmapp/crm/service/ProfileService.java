package com.crmapp.crm.service;

import com.crmapp.crm.entity.StatusEntity;
import com.crmapp.crm.entity.TasksEntity;
import com.crmapp.crm.entity.UsersEntity;
import com.crmapp.crm.repository.StatusRepositiory;
import com.crmapp.crm.repository.TaskReponsitory;
import com.crmapp.crm.repository.UserRespository;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;

@Service
public class ProfileService {
    @Autowired
    private UserRespository userRespository;
    @Autowired
    private TaskReponsitory taskReponsitory;
    @Autowired
    private StatusService statusService;
    public void jobNumber(Model model){
        double doNotTask = calculatePercentage(1);
        model.addAttribute("doNotTask",doNotTask);
        double doingTask = calculatePercentage(2);
        model.addAttribute("doingTask",doingTask);
        double doneTask = calculatePercentage(3);
        model.addAttribute("doneTask",doneTask);
    }
    public double calculatePercentage(int statusId) {
        // Tính toán phần trăm công việc dựa trên trạng thái
        int totalTasks = taskReponsitory.findAll().size();
        StatusEntity status = statusService.getStatusById(statusId);
        int tasksWithStatus = taskReponsitory.findByStatusEntity(status).size();
        return ((double) tasksWithStatus / totalTasks) * 100;
    }
    public List<TasksEntity> findByUsersEntity(UsersEntity usersEntity){
        return taskReponsitory.findByUsersEntity(usersEntity);
    }

    public UsersEntity getUserBySession(HttpSession session) {
        UsersEntity usersEntity = null;
        String email = "";
        if(session != null && session.getAttribute("email")!= null && !session.getAttribute("email").equals("")){
            email = (String) session.getAttribute("email");
            usersEntity = userRespository.getByEmail(email);
            System.out.println("Kiêm tra " + email);
        } else {
            System.out.println("Không thấy email");
        }
        return usersEntity;
    }

}
