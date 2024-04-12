package com.cspi.commonsystem.user.dto;

import com.cspi.commonsystem.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for {@link User}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRespDTO {

    private String id;
    private String name;
    private String email;
    @Builder.Default
    private List<Long> groupIds = new ArrayList<>();
    private Integer failAttempt;
    private LocalDate latestLogin;
    private String departmentId;
    private String companyId;

    public static UserRespDTO toRespDTO(User user){
        List<Long> groupIds = user.getUserGroupList().stream()
                .map(userGroup -> userGroup.getGroup().getId())
                .collect(Collectors.toList());
        return UserRespDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .groupIds(groupIds)
                .departmentId(user.getDepartmentId())
                .companyId(user.getCompanyId())
                .build();
    }
}