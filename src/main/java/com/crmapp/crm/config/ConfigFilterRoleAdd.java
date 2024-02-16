package com.crmapp.crm.config;

import com.crmapp.crm.filter.CustomFilterRole;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigFilterRoleAdd {
    // Khai báo thông tin cấu hình cho filter.
    @Bean
    public FilterRegistrationBean<CustomFilterRole> filterRoleAdd(){
        FilterRegistrationBean<CustomFilterRole> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomFilterRole());
//      link Liên quan đến set quyền.
        registrationBean.addUrlPatterns("/role/add");
        return registrationBean;
    }
}
