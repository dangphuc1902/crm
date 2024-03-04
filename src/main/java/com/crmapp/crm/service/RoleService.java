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

public void insertRole(String roleName, String description,Model model){

    boolean isSuccess = false;
    RolesEntity rolesEntity = new RolesEntity();
    rolesEntity.setName(roleName);
    rolesEntity.setDescription(description);


    if (roleName.equals("") || description == "") {
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
    private boolean checkRoleName(String roleName) {
        List<RolesEntity> rolesEntityList = rolesRepository.findAll();
        return rolesEntityList.stream().anyMatch(role -> role.getName().equalsIgnoreCase(roleName));
    }

    private boolean checkForNull(String roleName, String desc){
        return roleName != null && !roleName.isEmpty() && desc != null && !desc.isEmpty();
    }
    public String notificationUpdate(String roleName, String desc, RolesEntity role){
        if(roleName == null || roleName.isEmpty()){
            return "Vui lòng nhập tên quyền!";
        } else if (desc == null || desc.isEmpty()) {
            return "Vui lòng nhập mô tả!";
        } else if (roleName.equalsIgnoreCase(role.getName())) {
            return "";
        } else if (checkRoleName(roleName)) {
            return "Tên quyền đã tồn tại!";
        } else {
            return "";
        }
    }
    public RolesEntity getRoleById(int id) {
        // Optional: có hoặc không có cũng được.
        // Optional chứa các hàm hỗ trợ sẵn giúp kiểm tra giá trị có null hay không để tránh bị lỗi null dữ liệu trong quá trình sử lí
        RolesEntity dataRole = null;
        Optional<RolesEntity> rolesEntity = rolesRepository.findById(id);
        // isPresent : kiểm tra xem biến có giá trị hay không nếu là true tức biến có giá trị, nếu là false th sẻ không có giá trị

        if (rolesEntity.isPresent()) {
            dataRole = rolesEntity.get();
        }

        return dataRole;
    }

    public boolean updateRole(String roleName, String desc, RolesEntity rolesEntity, RolesEntity role){
        boolean isSuccess = false;

        if(checkForNull(roleName, desc) && (roleName.equalsIgnoreCase(role.getName()) || !checkRoleName(roleName))){
            try {
                rolesRepository.save(rolesEntity);
                isSuccess = true;
            }catch (Exception e){
                System.out.println("Lỗi Thêm dữ lệu" + e.getMessage());
            }
        }
        return isSuccess;
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
