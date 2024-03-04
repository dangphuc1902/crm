package com.crmapp.crm.repository;

import com.crmapp.crm.entity.StatusEntity;
import com.crmapp.crm.entity.TasksEntity;
import com.crmapp.crm.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskReponsitory extends JpaRepository<TasksEntity,Integer> {
    List<TasksEntity> findByUsersEntity(UsersEntity usersEntity);
    List<TasksEntity> findByStatusEntity(StatusEntity status);
}
