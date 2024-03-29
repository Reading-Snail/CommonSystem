package com.cspi.commonsystem;

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
    @Column(name = "GROUP_ID", nullable = false)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "group")
    private List<UserGroup> userGroup;

    @OneToMany(mappedBy = "group")
    private List<GroupMenuAuth> groupMenuAuth = new ArrayList<>();

}