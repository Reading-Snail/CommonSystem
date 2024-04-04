package com.cspi.commonsystem.user.repository;

import com.cspi.commonsystem.group.domain.Group;
import com.cspi.commonsystem.user.domain.User;
import com.cspi.commonsystem.user.domain.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    boolean existsByUserAndGroup(User user, Group group);
}
