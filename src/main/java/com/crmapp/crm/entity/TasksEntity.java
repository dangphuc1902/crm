package com.crmapp.crm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tasks")
public class TasksEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private java.util.Date endDate;
    @ManyToOne      // TODO note: Dựa vào "ManyToOne" hay "OneToMany" Xem chữ sau cùng để chọn một đối tượng private hay một List đối tượng.
    @JoinColumn(name = "user_id")       // TODO note: Tên cột trong dâatabase dùng để liên kết dữ liệu
    private UsersEntity usersEntity;    // TODO note: Dựa vào "ManyToOne" hay "OneToMany" Xem chữ sau cùng để chọn một đối tượng private hay một List đối tượng.
    @ManyToOne
    @JoinColumn(name = "job_id")
    private JobsEntity jobsEntity;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusEntity statusEntity;

}
