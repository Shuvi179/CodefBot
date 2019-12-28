package com.company.codef.telegram.command;

import com.company.codef.SpringContext;
import com.company.codef.codeforces.httpClient.CodefHttpClient;
import com.company.codef.information.service.ConnUserService;
import com.company.codef.utils.SendMessageUtil;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class UpdateHandleCommand extends BotCommand {

    public UpdateHandleCommand(){
        super("updateHandle", "Desc");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = user.getUserName();

        if(strings.length == 0) {
            return;
        }
        String userHandle = strings[0];
        boolean validHandle = CodefHttpClient.validateHandle(strings);
        if(!validHandle){
            String message = SendMessageUtil.getInvalidHandleErrorMessage(userHandle);
            SendMessageUtil.sendAnswer(chat.getId(), message, absSender);
            return;
        }
        ConnUserService connUserService = SpringContext.getBean(ConnUserService.class);
        boolean result = connUserService.updateUser(userName, userHandle);
        String message = SendMessageUtil.getRegisterMessageResult(result, user, userHandle);
        SendMessageUtil.sendAnswer(chat.getId(), message, absSender);
    }
}
