package com.crmapp.crm.controller;

import com.crmapp.crm.entity.RolesEntity;
import com.crmapp.crm.repository.RolesRepository;
import com.crmapp.crm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
/*
* b1: xác định nghiệp vụ cho chức năng
* b2: Xác định câu truy vấn
* b3: Xác định số lượng tham số sẽ sử dụng cho controller (đường dẫn đã khai báo)
* -- Xử l nghiệp vụ thông qua code
* b4:  để thực hiện được các câu truy vấn liên quan tới bảng đã được xác địnhở bước 2 => phải tạo ra
* file repository để quản lý các câu truy vấn nếu chưa có.
* b5: Xác định hàm tương ứng với lại câu truy vấn bước 2 của JPA
*
* */

    @Autowired
    private RolesRepository rolesRepository;


    @Autowired
    private RoleService roleService;    // Gọi Service

    /*
* save(): có 2 chức năng vừa là thêm mới dữ liệu, vừa là cập nhật dữ liệu.
*   - Thêm mới: khóa chính class entity truyền vào hàm save() không có  giá trị
*   - Cập nhật: khóa chính của class entity truyền vào hàm save() có giá trị.
* */

    @GetMapping("/add")
    public String add(Model model){
        //TODO note:  Thêm dữ liệu vào bảng role và không gán giá trị cho kháo chính Id

        return "role-add.html";
    }
    @GetMapping("/table")
    public String tableRole(Model model){
        List<RolesEntity> listRole = roleService.getAllRole();     // Get Service
        model.addAttribute("tableRole",listRole);       // trả dữ liệu về giao diện.
        return "role-table";
    }
    @GetMapping("/delete/{roleId}")
    public String  deleteItem(@PathVariable(name = "roleId") Integer id) {
        rolesRepository.deleteById(id);
        return "redirect:/role/table"; // Chuyển hướng người dùng về trang role-table.html sau khi xóa thành công
    }
    @PostMapping("/add")
    public String processAdd(@RequestParam String roleName,
                             @RequestParam String desc,Model model
                             ){
        roleService.insertRole(roleName,desc,model);

        return "role-add.html";
    }
//    TODo: chỉnh sửa role.
//     * - Chỉnh link/ role thành /role/add : fix lỗi liên quan css và js bên file HTML.   xong
//     * - Nếu thêm thành role thành công thì phải xuất ra màn hiình thông báo "thêm thành công" ngược lại "Thêm thất bại".
//     * - B1: Tạo đường dẫn load giao diện thêm mới quyền.
//    *  B2: Khi ngưi dùng click vào role muốn chỉnh sửa thì phải gán động od vào đường dẫn biết được người dùng đang click vào role nào muốn chỉnh sửa.
//     * */
    @GetMapping("/update/{roleId}")
    public String update(@PathVariable(name = "roleId") int id, Model model){
        RolesEntity rolesEntity =  roleService.getRoleId(id);
        model.addAttribute("rolesEntity", rolesEntity);     // trả đối tượng ra giao diện luôn.

        // truyền tham id vaoo để truyen tham số qua và biết được đang sửa id nào từ đó lấy được role name.
        return "revisionRole";
    }
    @PostMapping("/update/{roleId}")
    public String processUpdate(@PathVariable(name = "roleId") int id, @RequestParam String roleName,
                                @RequestParam String desc,Model model){
        RolesEntity rolesEntity = new RolesEntity();
        rolesEntity.setId(id);          // set id chức năng update.
        rolesEntity.setName(roleName);
        rolesEntity.setDescription(desc);
        roleService.updateRole(rolesEntity);
        model.addAttribute("rolesEntity", rolesEntity);     // trả đối tượng ra giao diện luôn.
        return "redirect:/role/table";
    }
}
