package com.cspi.commonsystem.user.domain;

import com.cspi.commonsystem.group.domain.Group;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "USER_GROUP")
public class UserGroup {
    @Id
    @SequenceGenerator(name = "user_group_seq_generator", sequenceName = "user_group_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_group_seq_generator")
    @Column(name = "USER_GROUP_ID", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    public void setUserAndGroup(User user, Group group) {
        this.user = user;
        this.group = group;
        user.getUserGroupList().add(this);
        group.getUserGroupList().add(this);
    }
}