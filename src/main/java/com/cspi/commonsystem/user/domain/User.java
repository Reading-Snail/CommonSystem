package com.cspi.commonsystem.user.domain;

import com.cspi.commonsystem.Employee;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_MASTER")
public class User {

    @Id
    @Column(name = "USER_ID", nullable = false)
    private String id;              // id

    @Column(name = "NAME")
    private String name;            // 이름

    @Column(name = "PASSWORD")
    private String password;        // 비밀번호

    @Column(name = "EMAIL")
    private String email;           // 이메일

    @OneToMany(mappedBy = "user")
    private List<UserGroup> userGroupList;  // 사용자화면권한그룹

    @OneToMany(mappedBy = "user")
    private List<UserMenuAuth> userMenuAuthList;    // 사용자화면권한

    @Embedded
    private PrePasswords prePasswords;    // 이전 비밀번호 5개 저장

    @Column(name = "FAIL_ATTEMPT")
    private Integer failAttempt;    //로그인 오류 횟수

    @ColumnDefault("'N'")
    @Column(name = "LOCK_YN", length = 1)
    private Character lockYn;       // 계정 잠금 여부

    @Column(name = "LATEST_LOGIN")
    private LocalDate latestLogin;  // 최종 로그인 일시

    // 확장
    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;      // 사번

    private String departmentId;      // 부서

    @Column(name = "COMPANY_ID")
    private String companyId;       // 소속 구분


    public void lockUser(){
        this.lockYn = 'Y';
    }

    /**
     * 사용자 계정 잠금해제 + 로그인 실패 횟수 초기화
     */
    public void unlockUser(){
        this.lockYn = 'N';
        this.failAttempt = 0;
    }

    /**
     * 새로운 비밀번호를 prePasswords에 추가하고 prePasswords를 갱신
     *
     * @param newPassword
     */
    public void changePassword(String newPassword) {
       this.prePasswords = PrePasswords.builder()
                .prePassword1(this.password)
                .prePassword2(this.prePasswords.getPrePassword1())
                .prePassword3(this.prePasswords.getPrePassword2())
                .prePassword4(this.prePasswords.getPrePassword3())
                .prePassword5(this.prePasswords.getPrePassword4())
                .build();
        this.password = newPassword;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class PrePasswords {

        @Builder.Default
        @Column(name = "POST_PASSWORD_1")
        private String prePassword1 = "";

        @Builder.Default
        @Column(name = "POST_PASSWORD_2")
        private String prePassword2 = "";

        @Builder.Default
        @Column(name = "POST_PASSWORD_3")
        private String prePassword3 = "";

        @Builder.Default
        @Column(name = "POST_PASSWORD_4")
        private String prePassword4 = "";

        @Builder.Default
        @Column(name = "POST_PASSWORD_5")
        private String prePassword5 = "";

    }

}
