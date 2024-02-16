package com.crmapp.crm.service;


import com.crmapp.crm.entity.StatusEntity;
import com.crmapp.crm.repository.StatusRepositiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    @Autowired
    StatusRepositiory statusRepositiory;

    public List<StatusEntity> getAllStatus(){
        return statusRepositiory.findAll();
    }

    public StatusEntity getStatusById(int id){

        StatusEntity dataStatus = null;
        Optional<StatusEntity> statusEntity = statusRepositiory.findById(id);

        if (statusEntity.isPresent()) {
            dataStatus = statusEntity.get();
        }

        return dataStatus;
    }
}