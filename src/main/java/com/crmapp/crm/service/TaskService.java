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
        System.out.println("Test isNameTaskExist 1");
        for (TasksEntity task : listTask) {
            if (task.getName().equalsIgnoreCase(nameTask)) {
                isExits = true;
                break;
            }
        }
        return isExits;
    }

    // Kiem tra công việc đã có ai làm hay chưa.
    public boolean checkConditions(String name, UsersEntity usersEntity, JobsEntity jobsEntity) {
        List<TasksEntity> tasksEntities = getAllTask();
        boolean isSuccess = true;
        for (TasksEntity tasks : tasksEntities) {
            if (tasks.getName().equalsIgnoreCase(name) && tasks.getUsersEntity().getId() == usersEntity.getId() && tasks.getJobsEntity().getId() == jobsEntity.getId()) {
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

    public List<TasksEntity> findByUsersEntity(UsersEntity usersEntity) {
        List<TasksEntity> tasksEntities = taskReponsitory.findByUsersEntity(usersEntity);
        return tasksEntities;
    }

    public boolean processAddTask(String nameTask, JobsEntity jobsEntity, UsersEntity usersEntity, Date startDate, Date endDate, StatusEntity statusEntity) {
        boolean isSuccess = false;  // biến flag kiểm tra xem có thêm user thành công hay không, mặc định là không (false)
        TasksEntity tasksEntity = new TasksEntity();
        tasksEntity.setName(nameTask);
        tasksEntity.setJobsEntity(jobsEntity);
        tasksEntity.setUsersEntity(usersEntity);
        tasksEntity.setStartDate(startDate);
        tasksEntity.setEndDate(endDate);
        tasksEntity.setStatusEntity(statusEntity);
        try {
            if (!isNameTaskExist(nameTask) && checkConditions(nameTask, usersEntity, jobsEntity)
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
    public boolean processUpdateTask(int id, String nameTask, JobsEntity jobsEntity, UsersEntity usersEntity, Date startDate, Date endDate, StatusEntity statusEntity) {
        boolean isSuccess = false;  // biến flag kiểm tra xem có thêm user thành công hay không, mặc định là không (false)
        TasksEntity tasksEntity = new TasksEntity();
        tasksEntity.setId(id);
        tasksEntity.setName(nameTask);
        tasksEntity.setJobsEntity(jobsEntity);
        tasksEntity.setUsersEntity(usersEntity);
        tasksEntity.setStartDate(startDate);
        tasksEntity.setEndDate(endDate);
        tasksEntity.setStatusEntity(statusEntity);
        try {
            if (!isNameTaskExist(nameTask) && checkConditions(nameTask, usersEntity, jobsEntity)
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
}
