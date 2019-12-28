package com.company.codef.telegram.command;

import com.company.codef.SpringContext;
import com.company.codef.codeforces.entity.UserCF;
import com.company.codef.codeforces.httpClient.CodefHttpClient;
import com.company.codef.information.entity.ConnUser;
import com.company.codef.information.service.ChatUserService;
import com.company.codef.utils.SendMessageUtil;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AllRatingCommand extends BotCommand {
    public AllRatingCommand(){
        super("allRating", "Desc");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        ChatUserService chatUserService = SpringContext.getBean(ChatUserService.class);
        List<ConnUser> users = chatUserService.findAllUserByChatId(chat.getId());
        List<String> usersHandle = getUsersHandle(users);
        List<UserCF> userResult = CodefHttpClient.getUserInfoByHandle(usersHandle);
        userResult.sort(Comparator.comparing(UserCF::getRating).reversed());
        String message = SendMessageUtil.getAllRatingMessage(userResult);
        SendMessageUtil.sendAnswer(chat.getId(), message, absSender);
    }

    private List<String> getUsersHandle(List<ConnUser> connUsers){
        return connUsers.stream().map(ConnUser::getHandle).collect(Collectors.toList());
    }
}
