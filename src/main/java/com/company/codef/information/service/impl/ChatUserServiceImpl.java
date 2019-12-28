package com.company.codef.information.service.impl;

import com.company.codef.information.entity.Chat;
import com.company.codef.information.entity.ChatUser;
import com.company.codef.information.entity.ConnUser;
import com.company.codef.information.repository.ChatUserRepository;
import com.company.codef.information.service.ChatService;
import com.company.codef.information.service.ChatUserService;
import com.company.codef.information.service.ConnUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatUserServiceImpl implements ChatUserService {

    @Autowired
    private ChatUserRepository chatUserRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ConnUserService connUserService;

    public boolean addChatUser(String userName, String handle, Long chatId, String chatName){
        if(chatUserRepository.validateCount(userName, handle, chatId) > 0){
            return false;
        }
        Chat chat = chatService.addChat(chatName, chatId);
        ConnUser connUser = connUserService.addUser(userName, handle);
        ChatUser chatUser = createChatUserByValue(chat, connUser);
        chatUserRepository.save(chatUser);
        return true;
    }

    public List<ConnUser> findAllUserByChatId(Long chatId){
        return chatUserRepository.findAllUserByChat(chatId).stream()
                .map(ChatUser::getConnUser).collect(Collectors.toList());
    }

    private boolean validateChatUser(String userName, String handle, Long chatId){
        return !chatService.validateChat(chatId) &&
                !connUserService.validateConnUser(userName, handle);
    }

    private ChatUser createChatUserByValue(Chat chat, ConnUser connUser){
        return new ChatUser(chat, connUser);
    }

}
