package com.crmapp.crm.service;

import com.crmapp.crm.entity.RolesEntity;
import com.crmapp.crm.entity.UsersEntity;
import com.crmapp.crm.repository.UserRespository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    private UserRespository userRespository;

    public String saveEmailAndPasswor(Cookie[] cookies, Model model){
        String email = "";
        if (cookies == null) {

            return "login";
        }else {
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


    public String performLogin(HttpSession httpSession, String email, String password, Model model, HttpServletResponse response, boolean remember) {

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
        List<UsersEntity> listUser = userRespository.findByEmailAndPassword(email, password);
        RolesEntity role;
        String roleName = "";
        for (UsersEntity roleId : listUser) {
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
            if (remember) {      // TODO note: remmeber lưu mật khẩu.
                Cookie emailCookie = new Cookie("email", email);
                Cookie passwordCookie = new Cookie("password", password);
                response.addCookie(emailCookie);
                response.addCookie(passwordCookie);
                System.out.println("Them cookie thanh cong! ");

//                Gán session để khi user đăng nhập thành công thì trả về dashboard
                httpSession.setAttribute("email", email);
                httpSession.setMaxInactiveInterval(8 * 60 * 60);
            }

            // Create Session to email user to know login success
            httpSession.setAttribute("email", email);
            httpSession.setMaxInactiveInterval(8 * 60 * 60);

            httpSession.setAttribute("roleName", roleName);
            httpSession.setMaxInactiveInterval(8 * 60 * 60);

            httpSession.setAttribute("user_id", roleName);
            httpSession.setMaxInactiveInterval(8 * 60 * 60);
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
}

/**
 * Hoàn thiện chức năng login
 * Bước 1: Thế tham số người dùng truyền vào hàm findByEmailAndPassword
 * Bước 2: Kiểm tra xem list có dữ liệu hay không ?
 *  * làm cách nào nhận được tham số?
 *  * làm cách nào có thể gọi được link/login với phương thức post
 *  * làm cách nào để truyền email và password?
 * Bước 3: Nếu có thì trả ra chuyên qua dashboard(lưu tạo link/ dashboard sử dụng page index.html)
 * Bước 4: Nếu thât bại thì xuất thông báo " Đăng nhập thất bại" ra màng hình login
 *  * làm cách nào để trả giá trị biến ra htlm?
 *  * làm cácnh nào để
 * *Lưu ý: phương thức post=> Chỉnh form data bên giao diện login
 *
 */



