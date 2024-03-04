package com.crmapp.crm.config;

//import com.crmapp.crm.filter.AuthenticationFilter;
import com.crmapp.crm.filter.AdminAndManagerFilter;
import com.crmapp.crm.filter.AuthenticationFilter;
import com.crmapp.crm.filter.CustomFilter;
import com.crmapp.crm.filter.PermissionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomFillterConfig {
    // Khai báo thông tin cấu hình cho filter.
    @Bean
    public FilterRegistrationBean<CustomFilter> CustomFilterConfig(){

        FilterRegistrationBean<CustomFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomFilter());
        registrationBean.addUrlPatterns("/login"); // khi người dùng gọi link là /login mới kích hoạt filter
        registrationBean.setOrder(1);

        return registrationBean;

    }

    @Bean
    public FilterRegistrationBean<PermissionFilter> filterConfig(){
        FilterRegistrationBean<PermissionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new PermissionFilter());
        registrationBean.addUrlPatterns("/dashboard/*","/profile/*", "/jobs/*", "/role/*", "/task/*", "/user/*","");
        registrationBean.setOrder(2);

        return registrationBean;

    }

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilterConfig (){

        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthenticationFilter());
        registrationBean.addUrlPatterns("/role/*","/jobs/add", "/user/add", "/user/update/*", "/user/delete/*");
        registrationBean.setOrder(3);

        return registrationBean;

    }

    @Bean
    public FilterRegistrationBean<AdminAndManagerFilter> adminAndManageFilterConfig (){

        FilterRegistrationBean<AdminAndManagerFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AdminAndManagerFilter());
        registrationBean.addUrlPatterns("/user/*", "/jobs/add", "/jobs/update/*", "/jobs/delete/*",
                "/task/add",  "/task/delete/*");
        registrationBean.setOrder(4);

        return registrationBean;

    }


}
