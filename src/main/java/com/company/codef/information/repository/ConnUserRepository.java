package com.company.codef.information.repository;

import com.company.codef.information.entity.Chat;
import com.company.codef.information.entity.ConnUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnUserRepository extends JpaRepository<ConnUser, Long> {
    boolean existsByUserNameOrHandle(String userName, String handle);
    ConnUser findByUserName(String userName);
    boolean existsByHandle(String handle);
    ConnUser findByUserNameAndHandle(String userName, String handle);
}
