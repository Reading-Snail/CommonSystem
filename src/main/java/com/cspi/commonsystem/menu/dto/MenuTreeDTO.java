package com.cspi.commonsystem.menu.dto;

import com.cspi.commonsystem.menu.domain.Menu;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MenuTreeDTO {
    private Long id;
    private String code;
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
    private String parentId;
    private List<MenuTreeDTO> children;

    public static MenuTreeDTO of(Menu menu) {
        return MenuTreeDTO.builder()
                .id(menu.getId())
//                .code(menu.getCode())
                .name(menu.getName())
                .children(menu.getChildren().stream().map(MenuTreeDTO::of).collect(Collectors.toList()))
                .build();
    }
}
