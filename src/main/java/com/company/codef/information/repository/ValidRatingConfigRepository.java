package com.company.codef.information.repository;

import com.company.codef.information.entity.Chat;
import com.company.codef.information.entity.ValidRatingConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidRatingConfigRepository extends JpaRepository<ValidRatingConfig, Long> {
    ValidRatingConfig getByChat(Chat chat);
}
