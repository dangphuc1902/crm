package com.crmapp.crm.repository;

import com.crmapp.crm.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface UserRespository extends JpaRepository<UsersEntity,Integer> {
    List<UsersEntity>findByEmailAndPassword(String email, String password);
}
