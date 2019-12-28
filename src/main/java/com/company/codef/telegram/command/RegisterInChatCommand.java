package com.company.codef.telegram.command;

import com.company.codef.SpringContext;
import com.company.codef.codeforces.entity.UserCF;
import com.company.codef.codeforces.httpClient.CodefHttpClient;
import com.company.codef.information.entity.ConnUser;
import com.company.codef.information.service.ChatService;
import com.company.codef.information.service.ChatUserService;
import com.company.codef.information.service.ConnUserService;
import com.company.codef.utils.SendMessageUtil;
import org.apache.catalina.core.ApplicationContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Comparator;
import java.util.List;

@Component
public class RegisterInChatCommand extends BotCommand {

    public RegisterInChatCommand(){
        super("register", "Desc");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if(strings.length == 0){
            return;
        }
        String userHandle = strings[0];
        boolean validHandle = CodefHttpClient.validateHandle(strings);
        if(!validHandle){
            String message = SendMessageUtil.getInvalidHandleErrorMessage(userHandle);
            SendMessageUtil.sendAnswer(chat.getId(), message, absSender);
            return;
        }
        String chatName = getChatName(user, chat);
        ChatUserService chatUserService = SpringContext.getBean(ChatUserService.class);
        boolean result = chatUserService.addChatUser(user.getUserName(), userHandle, chat.getId(), chatName);
        String message = SendMessageUtil.getRegisterMessageResult(result, user, userHandle);
        SendMessageUtil.sendAnswer(chat.getId(), message, absSender);
    }

    private String getChatName(User user, Chat chat){
        if(StringUtils.isEmpty(chat.getTitle())){
            return user.getUserName();
        }
        return chat.getTitle();
    }

}