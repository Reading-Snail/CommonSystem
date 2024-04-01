package com.cspi.commonsystem.menu;

import com.cspi.commonsystem.group.GroupMenuAuth;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "MENU_MASTER")
public class Menu {
    @Id
    @Column(name = "MENU_ID", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "menu")
    private List<GroupMenuAuth> groupMenuAuth = new ArrayList<>();

    @Column(name = "PARENT_ID")
    private Long parentId;      // 부모 ID

    @Column(name = "SORT_ORDER")
    private Long sortOrder;          // 형제간 순서

    @Column(name = "NAME")
    private String name;        // 명칭

    @Column(name = "TYPE", length = 1)
    private String type;     // 구분 M:Menu; P:Program

    @Column(name = "PROGRAM_ID")
    private String programId;   //

    @Column(name = "CALLER")
    private String caller;      // 호출자 해당 메뉴를 불러오는 화면

    @Column(name = "USE_YN", length = 1)
    private String useYn;

    // CRUD
    @Column(name = "CREATE_YN", length = 1)
    private String createYn;
    @Column(name = "READ_YN", length = 1)
    private String readYn;
    @Column(name = "UPDATE_YN", length = 1)
    private String updateYn;
    @Column(name = "DELETE_YN", length = 1)
    private String deleteYn;
    @Column(name = "EXCEL_YN", length = 1)
    private String excelYn;
    @Column(name = "REPORT_YN", length = 1)
    private String reportYn;

    // 옵션 기능
    @Column(name = "OPTION_1", length = 1)
    private String option1;
    @Column(name = "OPTION_2", length = 1)
    private String option2;
    @Column(name = "OPTION_3", length = 1)
    private String option3;

}
