package com.company.codef.telegram.command;

import com.company.codef.SpringContext;
import com.company.codef.information.service.ValidRatingChangeService;
import com.company.codef.utils.SendMessageUtil;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;


public class UpdateCurrentSeasonCommand extends BotCommand {
    public UpdateCurrentSeasonCommand(){
        super("updateSeason", "Desc");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        ValidRatingChangeService validRatingChangeService = SpringContext.getBean(ValidRatingChangeService.class);
        validRatingChangeService.updateRatingConfig(chat.getId());
        String message = "Season contest was successfully updated";
        SendMessageUtil.sendAnswer(chat.getId(), message, absSender);
    }
}
