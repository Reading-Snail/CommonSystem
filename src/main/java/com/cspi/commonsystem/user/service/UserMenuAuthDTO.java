package com.cspi.commonsystem.user.service;

import com.cspi.commonsystem.user.domain.UserMenuAuth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.cspi.commonsystem.user.domain.UserMenuAuth}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMenuAuthDTO{

    private Long id;
    private String userId;
    private Long menuId;
    private Character useYn;
    private Character createYn;
    private Character readYn;
    private Character updateYn;
    private Character deleteYn;
    private Character excelYn;
    private Character reportYn;
    private Character option1;
    private Character option2;
    private Character option3;

    public UserMenuAuth toEntity() {
        return UserMenuAuth.builder()
                .id(this.id)
                .useYn(this.useYn)
                .createYn(this.createYn)
                .readYn(this.readYn)
                .updateYn(this.updateYn)
                .deleteYn(this.deleteYn)
                .excelYn(this.excelYn)
                .reportYn(this.reportYn)
                .option1(this.option1)
                .option2(this.option2)
                .option3(this.option3)
                .build();
    }
}