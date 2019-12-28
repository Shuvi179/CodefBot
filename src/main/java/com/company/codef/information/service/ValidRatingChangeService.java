package com.company.codef.information.service;

import com.company.codef.information.entity.Chat;
import com.company.codef.information.entity.ValidRatingConfig;

public interface ValidRatingChangeService {
    ValidRatingConfig getValidRatingConfigByChat(Chat chat);
    void updateRatingConfig(Long chatId);
}
