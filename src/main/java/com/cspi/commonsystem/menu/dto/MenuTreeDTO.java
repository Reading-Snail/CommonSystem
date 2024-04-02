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
    private String id;
    private String parentId;
    private List<MenuTreeDTO> children;
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

    public static MenuTreeDTO of(Menu menu) {
        return MenuTreeDTO.builder()
                .id(menu.getId())
                .name(menu.getName())
                .children(menu.getChildren().stream().map(MenuTreeDTO::of).collect(Collectors.toList()))
                .build();
    }
}
