package com.company.codef.codeforces.httpClient;

import com.company.codef.codeforces.entity.Problem;
import com.company.codef.codeforces.entity.RatingChange;
import com.company.codef.codeforces.entity.UserCF;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JsonParser {
    private static String RATING = "rating";
    private static String OLD_RATING = "oldRating";
    private static String NEW_RATING = "newRating";
    private static String CONTEST_ID = "contestId";
    private static String CONTEST_UPDATE_RATING_TIME = "ratingUpdateTimeSeconds";
    private static String NAME = "name";
    private static String INDEX = "index";

    public static List<UserCF> parseUserInfo(JSONArray userJson){
        return IntStream.range(0, userJson.length())
                .mapToObj(i -> parseUserFromJson(userJson.getJSONObject(i)))
                .collect(Collectors.toList());
    }
    private static UserCF parseUserFromJson(JSONObject userJson){
        UserCF user = new UserCF();
        user.setHandle(userJson.getString("handle"));
        user.setRating(getValidRating(userJson, RATING));
        return user;
    }

    public static List<Problem> parseProblemByTags(JSONArray problemJson){
        return IntStream.range(0, problemJson.length())
                .mapToObj(i -> parseProblemJson(problemJson.getJSONObject(i)))
                .collect(Collectors.toList());
    }

    private static Problem parseProblemJson(JSONObject problemJson){
        Problem problem = new Problem();
        problem.setContestId(problemJson.getLong(CONTEST_ID));
        problem.setName(problemJson.getString(NAME));
        problem.setRating(getValidRating(problemJson, RATING));
        problem.setIndex(problemJson.getString(INDEX));
        return problem;
    }

    private static Integer getValidRating(JSONObject userJson, String key){
        return userJson.isNull(key) ? 0 : userJson.getInt(key);
    }

    public static List<RatingChange> parseRatingChange(JSONArray ratingChange){
        return IntStream.range(0, ratingChange.length())
                .mapToObj(i -> parseRatingFormJson(ratingChange.getJSONObject(i)))
                .collect(Collectors.toList());
    }

    private static RatingChange parseRatingFormJson(JSONObject ratingChange){
        RatingChange change = new RatingChange();
        change.setContestId(ratingChange.getLong(CONTEST_ID));
        change.setOldRating(getValidRating(ratingChange, OLD_RATING));
        change.setNewRating(getValidRating(ratingChange, NEW_RATING));
        change.setContestUpdateTime(ratingChange.getLong(CONTEST_UPDATE_RATING_TIME));
        return change;
    }

}
