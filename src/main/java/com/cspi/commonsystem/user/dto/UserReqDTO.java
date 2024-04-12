package com.cspi.commonsystem.user.dto;

import com.cspi.commonsystem.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link User}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReqDTO {

    private String id;
    private String name;
    private String password;
    private String email;
    @Builder.Default
    private List<Long> groupIds = new ArrayList<>();
    private Integer failAttempt;
    private LocalDate latestLogin;
    private String departmentId;
    private String companyId;

    public User toEntity() {
        return User.builder()
                .id(this.id)
                .name(this.name)
                .password(this.password)
                .email(this.email)
                .departmentId(this.departmentId)
                .companyId(this.companyId)
                .build();
    }
}