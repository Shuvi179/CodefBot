package com.company.codef.information.service.impl;

import com.company.codef.information.entity.Chat;
import com.company.codef.information.entity.ValidRatingConfig;
import com.company.codef.information.repository.ChatRepository;
import com.company.codef.information.repository.ValidRatingConfigRepository;
import com.company.codef.information.service.ValidRatingChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ValidRatingChangeServiceImpl implements ValidRatingChangeService {

    @Autowired
    private ValidRatingConfigRepository validRatingConfigRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public ValidRatingConfig getValidRatingConfigByChat(Chat chat){
        return validRatingConfigRepository.getByChat(chat);
    }

    @Override
    public void updateRatingConfig(Long chatId) {
        Long currentContest = System.currentTimeMillis() / 1000L;
        Chat chat = chatRepository.findByChatId(chatId);
        ValidRatingConfig currentConfig = getValidRatingConfigByChat(chat);
        if(Objects.isNull(currentConfig)){
            addNewConfig(chat, currentContest);
        } else {
            Long lastContest = currentConfig.getCurrentContestStart();
            currentConfig.setLastContestStart(lastContest);
            currentConfig.setCurrentContestStart(currentContest);
            validRatingConfigRepository.save(currentConfig);
        }
    }

    private void addNewConfig(Chat chat, Long currentContest){
        ValidRatingConfig config = new ValidRatingConfig(chat, currentContest);
        validRatingConfigRepository.save(config);
    }


}
