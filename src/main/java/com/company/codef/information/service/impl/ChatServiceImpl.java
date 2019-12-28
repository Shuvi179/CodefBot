package com.company.codef.information.service.impl;

import com.company.codef.information.entity.Chat;
import com.company.codef.information.repository.ChatRepository;
import com.company.codef.information.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    private ChatRepository chatRepository;

    public Chat addChat(String chatName, Long chatId){
        Chat chat = createChatByValue(chatName, chatId);
        if(!validateChat(chat.getChatId())){
           return chatRepository.save(chat);
        }
        return findChatByChatId(chatId);
    }

    public Chat findChatByChatId(Long chatId){
        return chatRepository.findByChatId(chatId);
    }

    public boolean validateChat(Long chatId){
        return chatRepository.existsByChatId(chatId);
    }

    private Chat createChatByValue(String chatName, Long chatId){
        return new Chat(chatName, chatId);
    }
}
