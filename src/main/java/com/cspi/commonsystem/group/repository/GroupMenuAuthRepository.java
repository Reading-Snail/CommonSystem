package com.cspi.commonsystem.group.repository;

import com.cspi.commonsystem.group.domain.Group;
import com.cspi.commonsystem.group.domain.GroupMenuAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMenuAuthRepository extends JpaRepository<GroupMenuAuth,Long> {
}
