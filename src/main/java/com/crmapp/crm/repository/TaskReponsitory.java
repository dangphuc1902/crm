package com.crmapp.crm.repository;

import com.crmapp.crm.entity.TasksEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskReponsitory extends JpaRepository<TasksEntity,Integer> {
}
