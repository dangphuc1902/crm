package com.crmapp.crm.repository;

import com.crmapp.crm.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepositiory extends JpaRepository<StatusEntity,Integer> {
}
