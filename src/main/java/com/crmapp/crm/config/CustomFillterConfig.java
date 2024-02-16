package com.crmapp.crm.config;

//import com.crmapp.crm.filter.AuthenticationFilter;
import com.crmapp.crm.filter.CustomFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomFillterConfig {
    // Khai báo thông tin cấu hình cho filter.
    @Bean
    public FilterRegistrationBean<CustomFilter> filterConfig(){
        FilterRegistrationBean<CustomFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomFilter());
//      link Liên quan đến set quyền.
        registrationBean.addUrlPatterns("/login");   // Khi người dùng gọi link là /role mới kích hoạt filter.
        registrationBean.setOrder(1);
        return registrationBean;
    }

//    @Bean
//    public FilterRegistrationBean<AuthenticationFilter> authenFilterConfig(){
//        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new AuthenticationFilter());
////      link Liên quan đến set quyền.
//        registrationBean.addUrlPatterns("/role/add");   // Khi người dùng gọi link là /role mới kích hoạt filter.
//        registrationBean.setOrder(2);
//        return registrationBean;
//    }
}
