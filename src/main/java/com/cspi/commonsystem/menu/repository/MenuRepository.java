package com.cspi.commonsystem.menu.repository;

import com.cspi.commonsystem.menu.domain.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByParentIsNull();
}
