package com.cspi.commonsystem.group.dto;

import com.cspi.commonsystem.group.domain.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.cspi.commonsystem.group.domain.Group}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {
    private Long id;
    private String code;
    private String name;

    public Group toEntity() {
        return Group.builder()
                .id(this.id)
                .code(this.code)
                .name(this.name)
                .build();
    }
}