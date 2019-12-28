package com.company.codef.information.service.impl;

import com.company.codef.codeforces.entity.RatingChange;
import com.company.codef.codeforces.httpClient.CodefHttpClient;
import com.company.codef.information.entity.Chat;
import com.company.codef.information.entity.ConnUser;
import com.company.codef.information.entity.ValidRatingConfig;
import com.company.codef.information.repository.ChatRepository;
import com.company.codef.information.service.ChatUserService;
import com.company.codef.information.service.UserCommonRatingService;
import com.company.codef.information.service.ValidRatingChangeService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserCommonRatingServiceImpl implements UserCommonRatingService {

    @Autowired
    private ValidRatingChangeService validRatingChangeService;

    @Autowired
    private ChatUserService chatUserService;

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Integer getUserCurrentRating(String handle, Long chatId) {
        Chat chat = chatRepository.findByChatId(chatId);
        ValidRatingConfig config = validRatingChangeService.getValidRatingConfigByChat(chat);
        if(Objects.isNull(config)){
            return -1;
        }
        List<RatingChange> ratingChanges = CodefHttpClient.getRatingChangeByHandle(handle);
        List<RatingChange> currentChanges = getRatingChangeFromCurrent(ratingChanges, config.getCurrentContestStart());
        List<RatingChange> prevChanges = getRatingChangeForPrevious(ratingChanges,
                config.getCurrentContestStart(), config.getLastContestStart());
        return getCommonRating(currentChanges, prevChanges);

    }

    @Override
    public Map<String, Integer> getUsersCommonRating(Long chatId) {
        List<ConnUser> allUsersInChat = chatUserService.findAllUserByChatId(chatId);

        return allUsersInChat.stream()
                .collect(Collectors.toMap(ConnUser::getHandle, user -> getUserCurrentRating(user.getHandle(), chatId)));
    }

    private List<RatingChange> getRatingChangeFromCurrent(List<RatingChange> allRatingChanges, Long currentContest){
        return allRatingChanges.stream()
                .filter(ratingChange -> ratingChange.getContestUpdateTime() >= currentContest)
                .collect(Collectors.toList());
    }

    private List<RatingChange> getRatingChangeForPrevious(List<RatingChange> allRatingChanges, Long currentContest,
                                                          Long prevContest){
        if(Objects.isNull(prevContest)){
            return Collections.emptyList();
        }

        return allRatingChanges.stream()
                .filter(ratingChange -> ratingChange.getContestUpdateTime() >= prevContest
                        && ratingChange.getContestUpdateTime() < currentContest)
                .collect(Collectors.toList());
    }

    private Integer getCommonRating(List<RatingChange> currentChanges, List<RatingChange> prevChanges){
        if(currentChanges.isEmpty()){
            return 0;
        }
        Integer maxPrev = getMaxRating(prevChanges);
        Integer maxCur = getMaxRating(currentChanges);
        maxPrev = validateMaxPrev(maxPrev, currentChanges);
        return maxCur - maxPrev;
    }

    private Integer getMaxRating(List<RatingChange> changes){
        if(changes.isEmpty()){
            return 0;
        }
        return changes.stream().map(RatingChange::getNewRating).max(Integer::compareTo).get();
    }

    private Integer validateMaxPrev(Integer maxPrev, List<RatingChange> currentChange){
        return maxPrev == 0 ? currentChange.get(0).getOldRating() : maxPrev;
    }


}
