package com.crmapp.crm.service;

import com.crmapp.crm.entity.*;
import com.crmapp.crm.repository.TaskReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskReponsitory taskReponsitory;

    public List<TasksEntity> getAllTask() {
        return taskReponsitory.findAll();
    }

    public boolean isNameTaskExist(String nameTask) {
        List<TasksEntity> listTask = taskReponsitory.findAll();
        boolean isExits = false;
        for (TasksEntity task : listTask) {
            if (task.getName().equalsIgnoreCase(nameTask)) {
                isExits = true;
                break;
            }
        }
        return isExits;
    }

    // Kiem tra công việc đã có ai làm hay chưa.
    public boolean checkConditions(String name,String description, UsersEntity usersEntity, JobsEntity jobsEntity) {
        List<TasksEntity> tasksEntities = getAllTask();
        boolean isSuccess = true;
        for (TasksEntity tasks : tasksEntities) {
            if (tasks.getName().equalsIgnoreCase(name) && tasks.getDescription().equalsIgnoreCase(description) && tasks.getUsersEntity().getId() == usersEntity.getId() && tasks.getJobsEntity().getId() == jobsEntity.getId()) {
                isSuccess = false;
            }
        }
        return isSuccess;
    }

    // Kiem tra ngay làm task có trong ngày của dự án
    public boolean checkConditionsDate(JobsEntity jobsEntity, Date startDate, Date endDate) {
        boolean isSuccess = true;
        //Ngày bắt đầu không thể sau ngày kết thúc
        if (startDate.after(endDate)) {
            // Ngày bắt đầu không thể bằng ngày kết thúc
        } else if (startDate == (endDate)) {
            isSuccess = false;
        } else {
            // Kiểm tra điều kiện ngày bắt đầu công việc không thể nằm ngoài ngày bắt đầu dự án và ngày kết thúc dự án,
            // ngày kết thúc thì ngược lại
            if (startDate.before(jobsEntity.getStartDate()) && (startDate).before(jobsEntity.getEndDate())
                    && endDate.after(jobsEntity.getEndDate())) {
                isSuccess = false;
            }
        }
        return isSuccess;
    }

    public TasksEntity getTaskById(int task_id) {
        Optional<TasksEntity> tasksEntity = taskReponsitory.findById(task_id);
        TasksEntity datatask = null;
        if (tasksEntity.isPresent()) {
            datatask = tasksEntity.get();
        }
        return datatask;
    }

    public List<TasksEntity>findByUsersEntity(UsersEntity usersEntity) {
        List<TasksEntity> tasksEntities = taskReponsitory.findByUsersEntity(usersEntity);
        return tasksEntities;
    }


    public boolean processAddTask(String nameTask,String description, JobsEntity jobsEntity, UsersEntity usersEntity, Date startDate, Date endDate, StatusEntity statusEntity) {
        boolean isSuccess = false;  // biến flag kiểm tra xem có thêm user thành công hay không, mặc định là không (false)
        TasksEntity tasksEntity = new TasksEntity();
        tasksEntity.setName(nameTask);
        tasksEntity.setDescription(description);
        tasksEntity.setJobsEntity(jobsEntity);
        tasksEntity.setUsersEntity(usersEntity);
        tasksEntity.setStartDate(startDate);
        tasksEntity.setEndDate(endDate);
        tasksEntity.setStatusEntity(statusEntity);
        try {
            if (!isNameTaskExist(nameTask) && checkConditions(nameTask,description,usersEntity, jobsEntity)
                    && checkConditionsDate(jobsEntity, startDate, endDate)) {
                // Yêu cầu khi thêm thì email phải khác nhau, nếu email không tồn tại thì mới add user.
                taskReponsitory.save(tasksEntity);
                isSuccess = true;
            } else {
                System.out.println(nameTask + "đã tồn tại!");
            }
        } catch (Exception e) {
            // đoạn code bên trong catch chỉ được chạy khi đoạn code bên trong try bị lỗi (Runtime Error)
            e.printStackTrace();
        }
        return isSuccess;
    }
    public void deleteTask(int task_id){
        taskReponsitory.deleteById(task_id);
    }
    public boolean checkConditionsUpdate(TasksEntity tasksEntity, String nameTask, UsersEntity usersEntity, JobsEntity jobsEntity){
        List<TasksEntity> tasksEntities = getAllTask();
        boolean isSuccess = true;
        // Khi update tên công việc, tên dự án, tên user có thẻ trùng với dữ liệu đưa lên
        if(tasksEntity.getName().equalsIgnoreCase(nameTask) && tasksEntity.getUsersEntity().getId() == usersEntity.getId()
                && tasksEntity.getJobsEntity().getId() == jobsEntity.getId()){
            return isSuccess;
        }else {
            for (TasksEntity task : tasksEntities){
                // Kiểm tra điều kiện không thể vừa trùng tên công việc, tên user và tên dự án
                if (task.getName().equalsIgnoreCase(nameTask) && task.getUsersEntity().getId() == usersEntity.getId()
                        && task.getJobsEntity().getId() == jobsEntity.getId()) {
                    isSuccess = false;
                    break;
                }
            }
        }

        for (TasksEntity task : tasksEntities){
            // Kiểm tra điều kiện không thể vừa trùng tên công việc, tên user và tên dự án
            if(task.getName().equalsIgnoreCase(nameTask) && task.getUsersEntity().getId() == usersEntity.getId()
                    && task.getJobsEntity().getId() == jobsEntity.getId()){
                isSuccess = false;
            }
        }
        return isSuccess;
    }
    public String notificationUpdate(int id,JobsEntity jobsEntity, UsersEntity usersEntity, TasksEntity tasksEntity, String nameTask, Date startDate, Date endDate){
        if(nameTask == null || nameTask.isEmpty()){
            return "Vui lòng nhập tên công việc!";
        } else if (startDate == null || startDate == null) {
            return "Vui lòng nhập ngày bắt đầu!";
        }else if (endDate == null || endDate == null) {
            return "Vui lòng nhập ngày kết thúc!";
        }else if (!checkConditions(nameTask, tasksEntity.getDescription(), usersEntity, jobsEntity)) {
            return "Công việc đã được người này đảm nhận!";
        }else if (!checkConditionsDate(jobsEntity, startDate, endDate)) {
            return "Ngày của công việc phải nằm trong khoảng ngày của dự án!";
        } else {
            return "";
        }
    }
    private boolean checkForNull(String nameTask,String description, Date startDate, Date endDate){
        return nameTask != null && !nameTask.isEmpty()
                && startDate != null && startDate != null && description != null
                && endDate != null && endDate != null;
    }
    public boolean processUpdateTask(int id, String nameTask,String description, JobsEntity jobsEntity, UsersEntity usersEntity, Date startDate, Date endDate, StatusEntity statusEntity) {
        boolean isSuccess = false;  // biến flag kiểm tra xem có thêm user thành công hay không, mặc định là không (false)
        TasksEntity tasksEntity = new TasksEntity();
        tasksEntity.setId(id);
        tasksEntity.setDescription(description);
        tasksEntity.setName(nameTask);
        tasksEntity.setJobsEntity(jobsEntity);
        tasksEntity.setUsersEntity(usersEntity);
        tasksEntity.setStartDate(startDate);
        tasksEntity.setEndDate(endDate);
        tasksEntity.setStatusEntity(statusEntity);

        if(checkForNull(nameTask, description,startDate, endDate) && checkConditionsUpdate(tasksEntity, nameTask, usersEntity, jobsEntity)
                && checkConditionsDate(jobsEntity, startDate, endDate)){
            try {
                taskReponsitory.save(tasksEntity);
                isSuccess = true;
            }catch (Exception e) {
                System.out.println("Lỗi Thêm dữ lệu" + e.getMessage());
            }
        }
        return isSuccess;
    }
}
