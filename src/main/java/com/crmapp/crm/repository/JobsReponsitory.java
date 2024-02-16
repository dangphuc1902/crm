package com.crmapp.crm.repository;

import com.crmapp.crm.entity.JobsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface JobsReponsitory extends JpaRepository<JobsEntity,Integer> {
}
