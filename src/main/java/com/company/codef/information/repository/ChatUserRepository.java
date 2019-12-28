package com.company.codef.information.repository;

import com.company.codef.information.entity.Chat;
import com.company.codef.information.entity.ChatUser;
import com.company.codef.information.entity.ConnUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {

    @Query("select cu from ChatUser as cu where cu.chat.chatId = :chatId")
    List<ChatUser> findAllUserByChat(@Param("chatId") Long chatId);

    @Query("select count(cu) from ChatUser as cu where (cu.connUser.userName = :userName" +
            " or cu.connUser.handle = :handle) and cu.chat.chatId = :chatId")
    Integer validateCount(@Param("userName") String userName,
                          @Param("handle") String handle,
                          @Param("chatId") Long chatId);
}
