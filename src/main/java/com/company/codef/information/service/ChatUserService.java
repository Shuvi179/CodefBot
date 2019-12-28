package com.company.codef.information.service;

import com.company.codef.information.entity.ConnUser;

import java.util.List;

public interface ChatUserService {
    boolean addChatUser(String userName, String handle, Long chatId, String chatName);
    List<ConnUser> findAllUserByChatId(Long chatId);
}
