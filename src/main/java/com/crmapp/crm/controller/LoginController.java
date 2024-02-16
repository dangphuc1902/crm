package com.crmapp.crm.controller;

import com.crmapp.crm.entity.RolesEntity;
import com.crmapp.crm.entity.UsersEntity;
//import com.crmapp.crm.repository.RoleIdUserReponsitory;
import com.crmapp.crm.repository.RolesRepository;
import com.crmapp.crm.repository.UserRespository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserRespository userRespository;

    @Autowired
    private RolesRepository rolesRepository;

//    public LoginController(RoleIdUserReponsitory roleIdUserReponsitory) {
//        this.roleIdUserReponsitory = roleIdUserReponsitory;
//    }

    public boolean authenticate(String email, String password) {
        // Tìm người dùng có email và mật khẩu tương ứng
        List<UsersEntity> listUser = userRespository.findByEmailAndPassword(email, password);

        // Nếu tìm thấy ít nhất một người dùng, trả về true
        return !listUser.isEmpty();
    }

    @GetMapping("")
    public String login(HttpServletRequest request, Model model) {
//        List<UsersEntity> list = userRespository.findByEmailAndPassword("nguyenvana@gmail.com", "123456");
//        for (UsersEntity item : list) {
//            System.out.println("Kiemtra " + item.getEmail());
//        }
        //  Lay cookies xuoongs gan
        Cookie[]cookies = request.getCookies();
        if (cookies == null) {

            return "login";
        }else {
            String email = "";
            String password = "";
            for (Cookie cookie : cookies){  
                if (cookie.getName().equals("email")){
                    email = cookie.getValue();
                }

                if (cookie.getName().equals("password")){
                    password = cookie.getValue();
                }
            }
            model.addAttribute("email",email);
            model.addAttribute("password",password);
            return "login";
        }
    }
    @PostMapping("")
    public String progressLogin(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "password", required = false) String password,
            Model model,
            boolean remember,
            HttpSession httpSession,
            HttpServletResponse response) {

        // Controller: Nơi định nghĩa link
        // TODO note: Model model: Cho phép trị từ java ra file HTML
        // View; Chính là file html
        /*
         * Hoàn thiện chức năng login:
         * Bước 1: Thế tham số người dùng tuyền vào hàm findByEmailANdPassword
         * - Làm cách nào lấy ược tham số ?
         * - Bên HTML làm cách nào có thể được gọi link /login với phương thức post?
         * - Làm cách nào truyền email và password
         * Bước 2: Kiếm tra xem List có dữ liệu hay không?
         * Bước 3: Nếu có thì chuyển qua trang DashBoard(Tạo Link trang Dashboard/ Sử
         * dng page index.html)
         * Bước 4: Nếu thất bài thì xuất thông báo "Đăng nhập thất bại" ra màn hình
         * login
         * - Làm cách nào để trả giá trị file ra HTML
         * Lưu ý: Phương thc Post => Chỉnh form data bên giao diện login
         * Cách làm:
         * - Giải quyết vấn de lay du lieu khi nhan nut submit dang nhap trc.
         */
        // If the user list is empty (there are matching users), redirect to the
        // Dashboard page
        System.out.println("Kiem tra: " + email + "    _       " + password);
        List<UsersEntity> listUser = userRespository.findByEmailAndPassword(email, password);
        RolesEntity role;
        String roleName = "";
        for (UsersEntity roleId : listUser){
            role = roleId.getRolesEntity();
            if (role != null) {
                roleName = role.getName();
                System.out.println("Kiem tra role_id " + roleName);
            }
        }
        boolean isSuccess = false;
        // Kiểm tra xem danh sách users có giá trị hay không.
        if (listUser.size() > 0) {
            // có giá trị => Đăng nhập thành công
            if (remember){      // TODO note: remmeber lưu mật khẩu.
                Cookie emailCookie = new Cookie("email", email);
                Cookie passwordCookie = new Cookie("password",password);
                response.addCookie(emailCookie);
                response.addCookie(passwordCookie);
                System.out.println("Them cookie thanh cong! ");

//                Gán session để khi user đăng nhập thành công thì trả về dashboard
                httpSession.setAttribute("email",email);
                httpSession.setMaxInactiveInterval(8*60*60);
            }

            // Create Session to email user to know login success
            httpSession.setAttribute("email",email);
            httpSession.setMaxInactiveInterval(8*60*60);

            httpSession.setAttribute("role",roleName);
            httpSession.setMaxInactiveInterval(8*60*60);
            isSuccess = true;
            return "redirect:/dashboard";
        } else {
            // không có giá trị => đăng nhập thất bại.
            // Đẩy giátriji cua bien isSuccess ra file html và dat ten key(bien) la
            // isSuccess.
            model.addAttribute("error", "Đăng nhập thất bại, vui lòng thử lại!");
            isSuccess = false;
            return "login";
        }
    }
    // Kiểm tra xem danh sách users có giá tri hay không.
    // if (!listUser.isEmpty()) {
    // for(UsersEntity list : listUser){
    // System.out.println("Kiemtra " + list.getEmail());
    // System.out.println("Kiemtra " + list.getPassword());
    // }
    // return "index";
    // } else {
    // // If there are no matching users or an error, display a login failure
    // message
    // model.addAttribute("error", "Đăng nhập thất bại, vui lòng thử lại!");
    // return "login";
    // }
    // }

/*
*   Khi đăng nập thành cong thì luu email và mat khau vào Cookie
*   Khi nguoi dung vo lai link/ login thi se dien san email va mat khau vao input.
*
* Cac buoc lam:
*   - Kiem tra dang nhap thanh cong chua.
*   - lay email va mat khau setCookie
*   - lay cookie xuong
* */

}
