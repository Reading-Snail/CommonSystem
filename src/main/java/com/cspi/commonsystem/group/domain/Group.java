package com.cspi.commonsystem.group.domain;

import com.cspi.commonsystem.user.domain.UserGroup;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "GROUP_MASTER")
public class Group {
    @Id
    @SequenceGenerator(name = "group_seq_generator", sequenceName = "group_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq_generator")
    @Column(name = "GROUP_ID", nullable = false)
    private Long id;

    @Column(name = "GROUP_CODE", nullable = false, unique = true)
    private String code;

    @Column(name = "NAME")
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "group")
    private List<UserGroup> userGroupList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "group")
    private List<GroupMenuAuth> groupMenuAuthList = new ArrayList<>();

}