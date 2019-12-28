package com.company.codef.information.repository;

import com.company.codef.information.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long>{
    Chat findByChatId(Long Id);
    boolean existsByChatId(Long Id);
}
