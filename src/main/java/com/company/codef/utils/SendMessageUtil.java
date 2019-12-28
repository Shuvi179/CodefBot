package com.company.codef.utils;

import com.company.codef.codeforces.entity.Problem;
import com.company.codef.codeforces.entity.UserCF;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class SendMessageUtil {
    private static final String CODEF_URI_PROBLEM = "https://codeforces.com/problemset/problem/";
    public static String getRegisterMessageResult(boolean result, User user, String handle){
        if(result){
            return user.getUserName() + " update handle: " + handle;
        }
        return "Handle is already exist [ "  + handle +  " ], or you are not registered [ " + user.getUserName() + " ]";
    }

    public static String getInvalidHandleErrorMessage(String handle){
        return "Your handle is invalid: [ " + handle + " ]";
    }

    public static String getAllRatingMessage(List<UserCF> userCFS){
        String EMPTY_RATING_LIST = "Rating list is empty";
        return userCFS.isEmpty() ? EMPTY_RATING_LIST : buildMessage(userCFS);
    }

    private static String buildMessage(List<UserCF> userCFS){
        StringBuilder builder = new StringBuilder();
        userCFS.forEach(user -> builder.append(getHelpMessage(user.getHandle(), user.getRating())));
        return builder.toString();
    }

    private static String getHelpMessage(String handle, Integer rating){
        return handle + ", Your rating is: " + rating.toString() + "\n";
    }

    public static void sendAnswer(Long chatId, String message, AbsSender absSender) {
        SendMessage answer = new SendMessage();
        answer.setChatId(chatId);
        answer.setText(message);
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static String getAllCommonRatingMessage(Map<String, Integer> allCommonRating){
        StringBuilder builder = new StringBuilder();
        allCommonRating.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(entry -> builder.append(getHelpMessage(entry.getKey(), entry.getValue())));
        return builder.toString();
    }

    public static void sendInvalidRandomProblemParameterMessage(Chat chat, AbsSender absSender){
        String message =  "Please provide valid input params like [ dp bfs 1500-1700 ]";
        sendAnswer(chat.getId(), message, absSender);
    }

    public static void sendInvalidTagsMessage(Chat chat, AbsSender absSender){
        String message = "Please provide valid tags, use /tags";
        sendAnswer(chat.getId(), message, absSender);
    }

    public static void sendRandomProblemMessage(Problem problem, Chat chat, AbsSender absSender){
        String message = "Your problem is ready : " + CODEF_URI_PROBLEM + problem.getContestId().toString() + "/" + problem.getIndex();
        sendAnswer(chat.getId(), message, absSender);
    }

    public static void sendAllTagsMessage(Chat chat, AbsSender absSender){
        StringBuilder builder = new StringBuilder("Please use tags which КодТян can understand [botTag -> CodefTag] \n");
        AllTags.validCode.forEach((key, value) -> builder.append(getHelpTagMessage(key, value)));
        sendAnswer(chat.getId(), builder.toString(), absSender);
    }

    private static String getHelpTagMessage(String botTag, String codefTag){
        return botTag + "   ->   " + codefTag + "\n";
    }

    public static void sendInvalidRatingOrTagsMessage(Chat chat, AbsSender absSender){
        String message = "Rating is invalid [ Or codef doesn't have problems for you :) ]";
        sendAnswer(chat.getId(), message, absSender);
    }

}
