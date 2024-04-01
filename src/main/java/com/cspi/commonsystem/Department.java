package com.cspi.commonsystem;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "DEPARTMENT_MASTER")
public class Department {
    @Id
    @Column(name = "DEPARTMENT_ID", nullable = false)
    private String id;

    @OneToMany(mappedBy = "department")
    private List<Employee> employee;
}