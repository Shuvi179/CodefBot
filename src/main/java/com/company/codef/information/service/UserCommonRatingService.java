package com.company.codef.information.service;

import java.util.Map;

public interface UserCommonRatingService {
    Integer getUserCurrentRating(String handle, Long chatId);
    Map<String, Integer> getUsersCommonRating(Long chatId);
}
