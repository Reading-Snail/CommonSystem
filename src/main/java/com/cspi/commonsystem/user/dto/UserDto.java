package com.cspi.commonsystem.user.dto;

import com.cspi.commonsystem.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link User}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String id;
    private String name;
    private String email;
    private String password;
    private Integer failAttempt;
    private LocalDate latestLogin;
    private String departmentId;
    private String companyId;

    public User toEntity() {
        return User.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .departmentId(this.departmentId)
                .companyId(this.companyId)
                .build();
    }
}