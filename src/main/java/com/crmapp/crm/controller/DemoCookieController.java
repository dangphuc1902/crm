package com.crmapp.crm.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/cookie")
public class DemoCookieController {
    @GetMapping("")
    public String CreateCookie(HttpServletResponse response, HttpServletRequest request){
//        Get cookie by request, Create cookie by response
////        Create Cookie
//        Cookie cookie = new Cookie("hello", "Emlacookiene");
//        Cookie cookie1 = new Cookie("username", URLEncoder.encode("Nguyễn Văn A", StandardCharsets.UTF_8));         // Mã hoa code de cookie tieng viet
//
////        Server bat cookie tao cookie
//        response.addCookie(cookie);
//        response.addCookie(cookie1);

//        get all cookie client truyen len.
        Cookie[] cookies =  request.getCookies();

//        Duyệt qua từng cookie
        for (Cookie cookie : cookies){
//            Lấy tên cookie đang duyet den
            String name = cookie.getName();
//            Lay gia tri cookie dang duyet den
            String value = cookie.getValue();
            if (name.equals("hello")){
                System.out.println("Kiem tra name: " + name);
                System.out.println("Kiem tra value: " + value);
            }
        }

        return "login";
    }
}
