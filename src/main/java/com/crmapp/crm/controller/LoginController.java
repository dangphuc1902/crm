//package com.crmapp.crm.controller;
//
//import com.crmapp.crm.service.LoginService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
//
///**
// * Các bước làm một chức năng trong ứng dụng
// * Bước 1: Phân tích yêu cầu chức năng, tức là phân tich chức năng đó mình cần làm gì và kết quả mong muốn là gì
// *
// * Bước 2: Xác định được câu truy vấn (query) giành cho chức năng đó
// *
// * Bước 3: từ câu truy vấn xác định được đường dẫn có nhận tham ố hay không và số lượng tham số là bao nhiêu
// *
// */
//
////Controler: nơi định nghĩa link
//// Model : cho phép trả giá trị từ java ra file HTML (view)
////View : Chính là file html
//
//@Controller
//@RequestMapping("/login")
//public class LoginController {
//
//    @Autowired
//    private LoginService loginService;
//
//    @GetMapping ("")
//    public String login( HttpServletRequest request, Model model){
//        String email = loginService.saveEmail(request);
//        model.addAttribute("email", email);
//
//        String password = loginService.savePassword(request);
//        model.addAttribute("password", password);
//
//        return "login";
//    }
//
//    @PostMapping("")
//    public String progressLogin(HttpSession httpSession, @RequestParam String email, @RequestParam String password, Model model, HttpServletResponse response, @RequestParam(value = "remember", defaultValue = "false") boolean remembers){
//        return loginService.performLogin(httpSession, email, password, model, response, remembers);
//    }
//
//}
package com.crmapp.crm.controller;

import com.crmapp.crm.entity.RolesEntity;
import com.crmapp.crm.entity.UsersEntity;
//import com.crmapp.crm.repository.RoleIdUserReponsitory;
import com.crmapp.crm.repository.RolesRepository;
import com.crmapp.crm.repository.UserRespository;
import com.crmapp.crm.service.LoginService;
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

//    public boolean authenticate(String email, String password) {
//        // Tìm người dùng có email và mật khẩu tương ứng
//        List<UsersEntity> listUser = userRespository.findByEmailAndPassword(email, password);
//
//        // Nếu tìm thấy ít nhất một người dùng, trả về true
//        return !listUser.isEmpty();
//    }
    @Autowired
    private LoginService loginService;
    @GetMapping("")
    public String login(HttpServletRequest request, Model model) {
        String email = "";
        //  Lay cookies xuoongs gan
        Cookie[]cookies = request.getCookies();
        return loginService.saveEmailAndPasswor(cookies,model);
    }
    @PostMapping("")
    public String progressLogin(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "email", required = false) String email,
                                @RequestParam(value = "password", required = false) String password,
                                Model model,
                                boolean remember,
                                HttpSession httpSession,
                                HttpServletResponse response) {

        return loginService.performLogin(httpSession, email, password, model, response, remember);
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