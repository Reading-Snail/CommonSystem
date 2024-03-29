package com.cspi.commonsystem;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "EMPLOYEE_MASTER")
public class Employee {

    @Id
    @Column(name = "EMPLOYEE_ID", nullable = false)
    private String id;

    @OneToMany(mappedBy = "employee")
    private List<User> userList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;

}