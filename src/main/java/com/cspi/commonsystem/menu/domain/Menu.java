package com.cspi.commonsystem.menu.domain;

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
    private String id;

    @OneToMany(mappedBy = "menu")
    private List<GroupMenuAuth> groupMenuAuth = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Menu parent;      // 부모 ID

    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Menu> children = new ArrayList<>();

    @Column(name = "SORT_ORDER")
    private Long sortOrder;          // 형제간 순서

    @Column(name = "NAME")
    private String name;        // 명칭

    @Column(name = "TYPE", length = 1)
    private Character type;     // 구분 M:Menu; P:Program

    @Column(name = "PROGRAM_ID")
    private String programId;   //

    @Column(name = "CALLER")
    private String caller;      // 호출자 해당 메뉴를 불러오는 화면

    @Builder.Default
    @Column(name = "USE_YN", length = 1)
    private Character useYn = 'N';

    // CRUD
    @Builder.Default
    @Column(name = "CREATE_YN", nullable = false, length = 1)
    private Character createYn = 'N';
    @Builder.Default
    @Column(name = "READ_YN", nullable = false, length = 1)
    private Character readYn = 'N';
    @Builder.Default
    @Column(name = "UPDATE_YN", nullable = false, length = 1)
    private Character updateYn = 'N';
    @Builder.Default
    @Column(name = "DELETE_YN", nullable = false, length = 1)
    private Character deleteYn = 'N';
    @Builder.Default
    @Column(name = "EXCEL_YN", nullable = false, length = 1)
    private Character excelYn = 'N';
    @Builder.Default
    @Column(name = "REPORT_YN", nullable = false, length = 1)
    private Character reportYn = 'N';

    // 옵션 기능
    @Builder.Default
    @Column(name = "OPTION_1", nullable = false, length = 1)
    private Character option1 = 'N';
    @Builder.Default
    @Column(name = "OPTION_2", nullable = false, length = 1)
    private Character option2 = 'N';
    @Builder.Default
    @Column(name = "OPTION_3", nullable = false, length = 1)
    private Character option3 = 'N';

    public void setParent(Menu parent) {
        this.parent = parent;
        parent.getChildren().add(newNode);
    }

}
