package com.cspi.commonsystem.group;

import com.cspi.commonsystem.menu.domain.Menu;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "GROUP_MENU_AUTH")
public class GroupMenuAuth {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

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
