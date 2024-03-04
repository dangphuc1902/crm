package com.crmapp.crm.repository;

import com.crmapp.crm.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepositiory extends JpaRepository<StatusEntity,Integer> {
}
