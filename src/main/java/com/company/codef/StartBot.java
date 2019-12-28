package com.company.codef;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class StartBot {
    private static final String BOT_NAME = "Codef_bot";
    public static void start() {

            ApiContextInitializer.init();
            TelegramBotsApi telegramBotsApi = createTelegramBotsApi();
            try {
                telegramBotsApi.registerBot(new CodefBot(BOT_NAME));
            }
            catch (TelegramApiException e){
                e.printStackTrace();
            }
    }

    private static TelegramBotsApi createTelegramBotsApi() {
        return createLongPollingTelegramBotsApi();
    }

    private static TelegramBotsApi createLongPollingTelegramBotsApi() {
        return new TelegramBotsApi();
    }
}
