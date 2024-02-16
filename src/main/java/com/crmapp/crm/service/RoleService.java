package com.crmapp.crm.service;

import com.crmapp.crm.entity.RolesEntity;
import com.crmapp.crm.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

//Lưu ý: Tên service sẽ giống tên controller. Bởi vì Service là nơi xử lý logic code cho controller,
@Service
public class RoleService {
//    public RolesEntity findByid;
    @Autowired
    private RolesRepository rolesRepository;
    public List<RolesEntity> getAllRole(){
        return rolesRepository.findAll();
    }

public void insertRole(String roleName, String desc,Model model){

    boolean isSuccess = false;
    RolesEntity rolesEntity = new RolesEntity();
    rolesEntity.setName(roleName);
    rolesEntity.setDescription(desc);


    if (roleName.equals("") || desc == "") {
        isSuccess = false;
        model.addAttribute("isSuccess",isSuccess);

    }else {
        try {
            rolesRepository.save(rolesEntity);
            isSuccess = true;
            model.addAttribute("isSuccess",isSuccess);
        } catch (Exception e) {
            // Handle the exception if the save operation fails
            e.printStackTrace();
            isSuccess = false;
            model.addAttribute("isSuccess",isSuccess);
        }

    }
}

    public RolesEntity updateRole(RolesEntity rolesEntity) {
        return rolesRepository.save(rolesEntity);
    }

    public RolesEntity getRoleId(int role_id){
        // TODO Optional: Có hoặc không có cũng được. trong tất cả ngôn ngữ đều vậy
        // Lấy giá trị getRoleId
//      Kiểu dữ liệu Optional: Chứa các hàm hỗ trợ sẵn, Giúp kiểm tra giá trị có null hay không để tránh bị lỗi null dữ liệu trong quá trình xử lý.
    Optional<RolesEntity> rolesEntity = rolesRepository.findById(role_id);
        RolesEntity dataRole = null;
//    do RolesEntity đang bị Optional bao bọc.Nên cần gọi hàm có sẵn.
//        isPresent();    nếu trả về true thì roleEntity có giá trị.
        if (rolesEntity.isPresent()){
          dataRole = rolesEntity.get();  // Giúp hủy Optional đi và trả về giá trị thực của biến.
        }
        return dataRole;
    }
}
