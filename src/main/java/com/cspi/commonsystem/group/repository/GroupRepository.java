package com.cspi.commonsystem.group.repository;

import com.cspi.commonsystem.group.domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {
}
