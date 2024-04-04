package com.cspi.commonsystem.user.domain;

import com.cspi.commonsystem.menu.domain.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "USER_MENU_AUTH")
public class UserMenuAuth {
    @Id
    @Column(name = "USER_MENU_AUTH_ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    @Column(name = "USE_YN", length = 1)
    private Character useYn;

    // CRUD
    @Column(name = "CREATE_YN", length = 1)
    private Character createYn;
    @Column(name = "READ_YN", length = 1)
    private Character readYn;
    @Column(name = "UPDATE_YN", length = 1)
    private Character updateYn;
    @Column(name = "DELETE_YN", length = 1)
    private Character deleteYn;
    @Column(name = "EXCEL_YN", length = 1)
    private Character excelYn;
    @Column(name = "REPORT_YN", length = 1)
    private Character reportYn;

    // 옵션 기능
    @Column(name = "OPTION_1", length = 1)
    private Character option1;
    @Column(name = "OPTION_2", length = 1)
    private Character option2;
    @Column(name = "OPTION_3", length = 1)
    private Character option3;

}
