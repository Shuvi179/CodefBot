package com.company.codef.telegram.command;
import com.company.codef.codeforces.httpClient.CodefHttpClient;
import com.company.codef.utils.SendMessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.company.codef.codeforces.entity.UserCF;

import java.util.Comparator;
import java.util.List;

public class RatingCommand extends BotCommand {

    public RatingCommand(){
        super("rating", "Desc");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = user.getUserName();
        if(StringUtils.isEmpty(userName)){
            return;
        }
        if(strings.length == 0){
            return;
        }
        List<String> codefHandle = List.of(strings);
        List<UserCF> userResult = CodefHttpClient.getUserInfoByHandle(codefHandle);
        userResult.sort(Comparator.comparing(UserCF::getRating).reversed());
        String message = SendMessageUtil.getAllRatingMessage(userResult);
        SendMessageUtil.sendAnswer(chat.getId(), message, absSender);
    }
}
