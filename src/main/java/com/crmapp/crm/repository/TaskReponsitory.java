package com.crmapp.crm.repository;

import com.crmapp.crm.entity.StatusEntity;
import com.crmapp.crm.entity.TasksEntity;
import com.crmapp.crm.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskReponsitory extends JpaRepository<TasksEntity,Integer> {
    List<TasksEntity> findByUsersEntity(UsersEntity usersEntity);
    List<TasksEntity> findByStatusEntity(StatusEntity status);
}
