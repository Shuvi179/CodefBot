package com.company.codef.telegram.command;

import com.company.codef.SpringContext;
import com.company.codef.codeforces.entity.Problem;
import com.company.codef.codeforces.entity.UserCF;
import com.company.codef.codeforces.httpClient.CodefHttpClient;
import com.company.codef.information.entity.ConnUser;
import com.company.codef.information.service.ChatUserService;
import com.company.codef.information.service.ProblemService;
import com.company.codef.utils.AllTags;
import com.company.codef.utils.SendMessageUtil;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomProblemCommand extends BotCommand {
    public RandomProblemCommand(){
        super("randomProblem", "Desc");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        ProblemService problemService = SpringContext.getBean(ProblemService.class);
        if(strings.length < 2){
            SendMessageUtil.sendInvalidRandomProblemParameterMessage(chat, absSender);
            return;
        }
        String rating = strings[strings.length - 1];
        boolean valid = problemService.validateRating(rating);
        if(!valid){
            SendMessageUtil.sendInvalidRandomProblemParameterMessage(chat, absSender);
            return;
        }

        List<String> tags = getTags(strings);

        if(!AllTags.validateTags(tags)){
            SendMessageUtil.sendInvalidTagsMessage(chat, absSender);
            return;
        }

        List<String> validTag = AllTags.getValidTag(tags);
        Problem randomProblem = problemService.getRandomProblemByTagAndRating(validTag, rating);
        if(Objects.isNull(randomProblem)){
            SendMessageUtil.sendInvalidRatingOrTagsMessage(chat, absSender);
            return;
        }
        SendMessageUtil.sendRandomProblemMessage(randomProblem, chat, absSender);
    }

    private List<String> getTags(String[] strings){
        return Arrays.stream(strings, 0, strings.length - 1)
                .collect(Collectors.toList());
    }

}
