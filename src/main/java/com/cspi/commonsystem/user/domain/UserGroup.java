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
    @Column(name = "USER_GROUP_ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    public void setUserAndGroup(User user, Group group) {
        this.user = user;
        this.group = group;
    }
}