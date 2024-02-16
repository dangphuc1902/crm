//package com.crmapp.crm.filter;
//
//
//import com.crmapp.crm.repository.UserRespository;
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//
//import java.io.IOException;
//
//
//public class AuthenticationFilter implements Filter {
//
//
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
//
//        HttpServletRequest request =  (HttpServletRequest) servletRequest;  // Ep kieu HttpServletRequest moi co getSession
//        // request: Client truyền vào server.
//        HttpServletResponse response = (HttpServletResponse) servletResponse; //// Ep kieu HttpServletResponse moi co sendRedirect
//        // response: server truyền vào Client.
//        HttpSession session = request.getSession();     //Session tạo cookie là idsession.=> Lấy IDsesstion ở client. Yêu client dùng gửi lên server
//
//
////        String path = request.getServletPath();
//        if (session != null && session.getAttribute("email") != null && !session.getAttribute("email").equals("")) {
//            if (session.getAttribute("role").equals("ROLE_ADMIN")){
//                chain.doFilter(servletRequest,servletResponse);
//            }else {
//                response.sendRedirect("http://localhost:8080");
//            }
//        }else {
//            response.sendRedirect("http://localhost:8080/login");
//        }
//    }
//}
