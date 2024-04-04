package com.cspi.commonsystem.user.repository;

import com.cspi.commonsystem.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * 사용자의 이전 비밀번호들을 조회하여 새로운 비밀번호와 중복되는지 확인
     *
     * @param id 사용자ID
     * @param newPassword 새로운 비밀번호
     */
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.id = :id AND (u.prePasswords.prePassword1 = :newPassword OR u.prePasswords.prePassword2 = :newPassword OR u.prePasswords.prePassword3 = :newPassword OR u.prePasswords.prePassword4 = :newPassword OR u.prePasswords.prePassword5 = :newPassword)")
    boolean existsByIdAndNewPassword(@Param("id") String id, @Param("newPassword") String newPassword);

}
