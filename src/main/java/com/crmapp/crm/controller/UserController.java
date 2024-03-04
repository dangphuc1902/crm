package com.crmapp.crm.controller;

import com.crmapp.crm.entity.JobsEntity;
import com.crmapp.crm.entity.RolesEntity;
import com.crmapp.crm.entity.TasksEntity;
import com.crmapp.crm.entity.UsersEntity;
import com.crmapp.crm.repository.UserRespository;
import com.crmapp.crm.service.NameParserService;
import com.crmapp.crm.service.RoleService;
import com.crmapp.crm.service.TaskService;
import com.crmapp.crm.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    @Autowired
    private TaskService taskService;


    @GetMapping("/add")
    public String userAdd(Model model, HttpSession session){
        UsersEntity users = userService.getUserBySession(session);
        String avatarPath = userService.getPathAvata(users);
        model.addAttribute("avatarPath",avatarPath);
        List<RolesEntity> listRole = roleService.getAllRole();
        model.addAttribute("listRole",listRole);
        return"user-add";
    }
    @PostMapping("/add")
    public String processAdd(@RequestParam String fullname,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String phoneNumber,
                             @RequestParam("selectedRoleId") int roleId,
                             Model model,
                             @ModelAttribute UsersEntity user,
                             @RequestParam("fileImage") MultipartFile file) {

        RolesEntity rolesEntity = roleService.getRoleId(roleId);
        boolean insertUser = userService.insertUser(fullname, email, password, phoneNumber,file ,rolesEntity);
        model.addAttribute("insertUser",insertUser);
        UsersEntity usersEntity = new UsersEntity();
        model.addAttribute("fullname",fullname);
        model.addAttribute("email",email);
        model.addAttribute("password",password);
        model.addAttribute("phoneNumber",phoneNumber);
        List<RolesEntity> listRole = roleService.getAllRole();
        model.addAttribute("listRole",listRole);
        return"user-add";
    }
//    TODO: delete user
    @GetMapping("/delete/{user_id}")
    public String  deleteItem(@PathVariable(name = "user_id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/user/table"; // Chuyển hướng người dùng về trang role-table.html sau khi xóa thành công
    }
//  Show table user
    @GetMapping("/table")
    public String userTable(Model model ,HttpSession session){
        UsersEntity users = userService.getUserBySession(session);
        String avatarPath = userService.getPathAvata(users);
        model.addAttribute("avatarPath",avatarPath);
        List<UsersEntity> listUser = userService.getAllUser();
        model.addAttribute("listUser",listUser);
        return "user-table";
    }
// Update user after edit
    @GetMapping("/update/{user_id}")
    public String update(@PathVariable(name = "user_id") int id, Model model, HttpSession session){
        UsersEntity users = userService.getUserBySession(session);
        String avatarPath = userService.getPathAvata(users);
        model.addAttribute("avatarPath",avatarPath);
        UsersEntity usersEntity = userService.getUserId(id);
        model.addAttribute("usersEntity",usersEntity);
        List<RolesEntity> listRole = roleService.getAllRole();
        model.addAttribute("listRole",listRole);
        return "user-update";
    }
    @PostMapping("/update/{user_id}")
    public String processUpdate(@PathVariable(name = "user_id") int user_id,
                                @RequestParam String fullname,
                                @RequestParam String email,
                                @RequestParam String password,
                                @RequestParam String phoneNumber,
                                @RequestParam("selectedRoleId") int roleId,
                                Model model,
                                @RequestParam("fileImage") MultipartFile file){
        RolesEntity rolesEntity = roleService.getRoleId(roleId);
        boolean insertUser = userService.processUpdate(fullname, email, password,user_id, phoneNumber,file,rolesEntity);
        model.addAttribute("insertUser",insertUser);
        UsersEntity usersEntity = new UsersEntity();
        model.addAttribute("fullname",fullname);
        model.addAttribute("email",email);
        model.addAttribute("password",password);
        model.addAttribute("phoneNumber",phoneNumber);
        List<RolesEntity> listRole = roleService.getAllRole();
        model.addAttribute("listRole",listRole);
        return "redirect:/user/table";
    }
    @GetMapping("/details/{user_id}")
    public String userDetails( @PathVariable("user_id") int id,  Model model, HttpSession httpSession){
        UsersEntity users = userService.getUserBySession(httpSession);
        String avatarPath = userService.getPathAvata(users);
        model.addAttribute("avatarPath",avatarPath);
        UsersEntity user = userService.getUserId(id);
        model.addAttribute("usersEntity", user);
        String avatarPathid = userService.getPathAvata(user);
        model.addAttribute("avatarPathid",avatarPathid);
        List<TasksEntity> tasksEntities = taskService.findByUsersEntity(user);
        List<TasksEntity> taskUnfulfilled = userService.checkTasksUnfulfilled(tasksEntities);
        model.addAttribute("unfulfilled", taskUnfulfilled);

        List<TasksEntity> taskProcessing = userService.checkTasksProcessing(tasksEntities);
        model.addAttribute("processing", taskProcessing);

        List<TasksEntity> taskMade = userService.checkTasksMade(tasksEntities);
        model.addAttribute("made", taskMade);

        int quantityUnfulfilled = userService.getTaskUnfulfilled(tasksEntities);
        model.addAttribute("quantityUnfulfilled", quantityUnfulfilled);

        int quantityProcessing = userService.getTaskProcessing(tasksEntities);
        model.addAttribute("quantityProcessing", quantityProcessing);

        int quantityCompleted = userService.getTaskCompleted(tasksEntities);
        model.addAttribute("quantityCompleted", quantityCompleted);

        return "user-details";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        userService.deleteSession(request);
        return "redirect:/login";
    }
//  Show user details.
    @GetMapping("/userdetails")
    public String userDetails( Model model, HttpServletRequest request, HttpSession httpSession){
        UsersEntity users = userService.getUserBySession(httpSession);
        String avatarPath = userService.getPathAvata(users);
        model.addAttribute("avatarPath",avatarPath);
        HttpSession session = request.getSession();
        UsersEntity user = userService.getUserBySession(session);
        model.addAttribute("usersEntity", user);
        String avatarPathid = userService.getPathAvata(user);
        model.addAttribute("avatarPathid",avatarPathid);

        List<TasksEntity> tasksEntities = taskService.findByUsersEntity(user);
        List<TasksEntity> taskUnfulfilled = userService.checkTasksUnfulfilled(tasksEntities);
        model.addAttribute("unfulfilled", taskUnfulfilled);

        List<TasksEntity> taskProcessing = userService.checkTasksProcessing(tasksEntities);
        model.addAttribute("processing", taskProcessing);

        List<TasksEntity> taskMade = userService.checkTasksMade(tasksEntities);
        model.addAttribute("made", taskMade);

        int quantityUnfulfilled = userService.getTaskUnfulfilled(tasksEntities);
        model.addAttribute("quantityUnfulfilled", quantityUnfulfilled);

        int quantityProcessing = userService.getTaskProcessing(tasksEntities);
        model.addAttribute("quantityProcessing", quantityProcessing);

        int quantityCompleted = userService.getTaskCompleted(tasksEntities);
        model.addAttribute("quantityCompleted", quantityCompleted);

        return "user-details";
    }
}

