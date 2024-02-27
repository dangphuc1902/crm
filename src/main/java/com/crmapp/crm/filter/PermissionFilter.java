package com.crmapp.crm.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class PermissionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();

        // Kiểm tra xem session lưu trữ ở login lúc đăng nhập thành công có tồn tại không
        if(session != null && session.getAttribute("email")!= null && !session.getAttribute("email").equals("")){
            // cho đi tiếp vào đường dẫn mà client đang gọi hoặc thoát ra khỏi filter và đi tiếp
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            // chuyển hớng về trang đăng nhập
            response.sendRedirect("http://localhost:8080/login");
        }
    }
}
