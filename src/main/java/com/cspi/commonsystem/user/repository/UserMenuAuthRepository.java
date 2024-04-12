package com.cspi.commonsystem.user.repository;

import com.cspi.commonsystem.user.domain.User;
import com.cspi.commonsystem.user.domain.UserMenuAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMenuAuthRepository extends JpaRepository<UserMenuAuth, Long> {

}
