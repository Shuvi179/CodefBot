package com.company.codef.information.service;

import com.company.codef.information.entity.Chat;

public interface ChatService {
    Chat addChat(String chatName, Long chatId);
    boolean validateChat(Long chatId);
}
