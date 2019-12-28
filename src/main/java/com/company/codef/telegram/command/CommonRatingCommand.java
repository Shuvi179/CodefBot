package com.company.codef.telegram.command;

import com.company.codef.SpringContext;
import com.company.codef.codeforces.entity.UserCF;
import com.company.codef.codeforces.httpClient.CodefHttpClient;
import com.company.codef.information.entity.ConnUser;
import com.company.codef.information.service.ChatUserService;
import com.company.codef.information.service.UserCommonRatingService;
import com.company.codef.utils.SendMessageUtil;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommonRatingCommand extends BotCommand {
    public CommonRatingCommand(){
        super("commonRating", "Desc");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        UserCommonRatingService userCommonRatingService = SpringContext.getBean(UserCommonRatingService.class);
        Map<String, Integer> usersCommonRating = userCommonRatingService.getUsersCommonRating(chat.getId());
        if(usersCommonRating.isEmpty()){
            String message = "User register list is empty";
            SendMessageUtil.sendAnswer(chat.getId(), message, absSender);
            return;
        }
        String message = SendMessageUtil.getAllCommonRatingMessage(usersCommonRating);
        SendMessageUtil.sendAnswer(chat.getId(), message, absSender);
    }
}
