package com.cspi.commonsystem.menu.dto;

import com.cspi.commonsystem.menu.domain.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.cspi.commonsystem.menu.domain.Menu}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
    private Long id;
    private String code;
    private Long parentId;
    private Long sortOrder;
    private String name;
    private Character type;
    private String programId;
    private String caller;
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

    public Menu toEntity() {
        return Menu.builder()
                    .id(this.id)
//                    .code(this.code)
                    .sortOrder(this.sortOrder)
                    .name(this.name)
                    .type(this.type)
                    .programId(this.programId)
                    .caller(this.caller)
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