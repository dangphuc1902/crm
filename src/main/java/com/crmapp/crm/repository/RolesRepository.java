package com.crmapp.crm.repository;

import com.crmapp.crm.entity.RolesEntity;
import com.crmapp.crm.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository: Đưa lên IOC(Container: để dùng chung)
@Repository
public interface RolesRepository extends JpaRepository<RolesEntity,Integer> {

}