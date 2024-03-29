package com.cspi.commonsystem;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "USER_MASTER")
public class User {

    @Id
    @Column(name = "USER_ID", nullable = false)
    private String id;              // id

    @OneToMany(mappedBy = "user")
    private List<UserGroup> UserGroup;

    @OneToMany(mappedBy = "user")
    private List<UserMenuAuth> userMenuAuthList;

    @Column(name = "NAME")
    private String name;            // 이름

    @Column(name = "EMAIL")
    private String email;           // 이메일

    @Column(name = "PASSWORD")
    private String password;        // 비밀번호

    @Column(name = "POST_PASSWORD_1")
    private String postPassword1;    // 이번 비밀번호

    @Column(name = "POST_PASSWORD_2")
    private String postPassword2;

    @Column(name = "POST_PASSWORD_3")
    private String postPassword3;

    @Column(name = "POST_PASSWORD_4")
    private String postPassword4;

    @Column(name = "POST_PASSWORD_5")
    private String postPassword5;

    @Column(name = "FAIL_ATTEMPT")
    private Integer failAttempt;    //로그인 오류 횟수

    @Column(name = "LOCK_YN")
    private String lockYn;       // 계정 잠금 여부

    @Column(name = "LATEST_LOGIN")
    private LocalDate latestLogin;  // 최종 로그인 일시

    // 확장
    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;      // 사번

    private Long departmentId;      // 부서

    @Column(name = "COMPANY_ID")
    private String companyId;       // 소속 구분

    /*사용자을 잠금*/
    public void lockUser(){
        this.lockYn = "Y";
    }

    public void unlockUser(){
        this.lockYn = "N";
    }

}
