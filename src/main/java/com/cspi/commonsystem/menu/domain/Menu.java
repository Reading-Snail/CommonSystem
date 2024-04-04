package com.cspi.commonsystem.menu.domain;

import com.cspi.commonsystem.group.domain.GroupMenuAuth;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MENU_MASTER")
public class Menu {
    @Id
    @Column(name = "MENU_ID", nullable = false)
    @SequenceGenerator(name = "menu_seq_generator", sequenceName = "menu_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_seq_generator")
    private Long id;

    @Column(name = "MENU_CODE", nullable = false, unique = true)
    private String code;    // 화면에서 채번된 값

    @Column(name = "TYPE", length = 1)
    private Character type;     // 구분 M:Menu; P:Program

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

    @Column(name = "PROGRAM_ID")
    private String programId;   //

    @Column(name = "CALLER")
    private String caller;      // 호출자 해당 메뉴를 불러오는 화면

    @ColumnDefault("'Y'")
    @Column(name = "USE_YN", nullable = false, length = 1)
    private Character useYn;

    // CRUD
    @ColumnDefault("'N'")
    @Column(name = "CREATE_YN", nullable = false, length = 1)
    private Character createYn;
    @ColumnDefault("'N'")
    @Column(name = "READ_YN", nullable = false, length = 1)
    private Character readYn;
    @ColumnDefault("'N'")
    @Column(name = "UPDATE_YN", nullable = false, length = 1)
    private Character updateYn;
    @ColumnDefault("'N'")
    @Column(name = "DELETE_YN", nullable = false, length = 1)
    private Character deleteYn;
    @ColumnDefault("'N'")
    @Column(name = "EXCEL_YN", nullable = false, length = 1)
    private Character excelYn;
    @ColumnDefault("'N'")
    @Column(name = "REPORT_YN", nullable = false, length = 1)
    private Character reportYn;

    // 옵션 기능
    @ColumnDefault("'N'")
    @Column(name = "OPTION_1", nullable = false, length = 1)
    private Character option1;
    @ColumnDefault("'N'")
    @Column(name = "OPTION_2", nullable = false, length = 1)
    private Character option2;
    @ColumnDefault("'N'")
    @Column(name = "OPTION_3", nullable = false, length = 1)
    private Character option3;

    public void addToParentAsChild(Menu parent) {
        this.parent = parent;
        parent.getChildren().add(this);
    }

}
