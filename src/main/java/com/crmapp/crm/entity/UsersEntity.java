package com.crmapp.crm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "email",nullable = false,unique = true)
    private String email;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "firstname",nullable = false)
    private String firstname;
    @Column(name="lastname")
    private String lastname;
    @Column(name="fullname")
    private String fullname;
    @Column(name="phonenumber")
    private String phonenumber;
    @Column(name="avatar_path ")
    private String avatarPath;
    @ManyToOne      // TODO note: Dựa vào "ManyToOne" hay "OneToMany" Xem chữ sau cùng để chọn một đối tượng private hay một List đối tượng.
    @JoinColumn(name = "role_id")       // TODO note: Tên cột trong dâatabase dùng để liên kết dữ liệu
    private RolesEntity rolesEntity;    // TODO note: Dựa vào "ManyToOne" hay "OneToMany" Xem chữ sau cùng để chọn một đối tượng private hay một List đối tượng.
}
