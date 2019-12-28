package com.company.codef;

import com.company.codef.telegram.command.*;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CodefBot extends TelegramLongPollingCommandBot {
    private static final String BOT_TOKEN = "ShuviBotToken";

    CodefBot(String botName){
        super(botName);
        register(new RatingCommand());
        register(new RegisterInChatCommand());
        register(new UpdateHandleCommand());
        register(new AllRatingCommand());
        register(new UpdateCurrentSeasonCommand());
        register(new CommonRatingCommand());
        register(new RandomProblemCommand());
        register(new AllTagsCommand());
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message message = update.getMessage();
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}