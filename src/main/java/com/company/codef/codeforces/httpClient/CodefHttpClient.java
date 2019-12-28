package com.company.codef.codeforces.httpClient;

import com.company.codef.codeforces.MethodsConstant;
import com.company.codef.codeforces.entity.Problem;
import com.company.codef.codeforces.entity.RatingChange;
import com.company.codef.codeforces.entity.UserCF;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CodefHttpClient {

    private static final String DEFAULT_URL = "https://codeforces.com/api/";
    private static final String RESULT = "result";
    private static final String HANDLES = "handles";
    private static final String HANDLE = "handle";
    private static final String TAGS = "tags";
    private static final String FAILED = "FAILED";
    private static final String STATUS = "status";
    private static final String PROBLEMS = "problems";
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static List<UserCF> getUserInfoByHandle(List<String> usersHandle){
        Map<String, String> param = getUserHandleParams(usersHandle);
        JSONObject result =  sendGet(param, MethodsConstant.USER_INFO_METHOD);
        if(Objects.isNull(result) || result.get(STATUS).equals(FAILED)) {
            return Collections.emptyList();
        }
        return JsonParser.parseUserInfo(result.getJSONArray(RESULT));
    }

    public static List<Problem> getRandomProblemByTags(List<String> tags){
        Map<String, String> param = getProblemByTagsParam(tags);
        JSONObject result =  sendGet(param, MethodsConstant.PROBLEM_SET_BY_TAG);
        if(Objects.isNull(result) || result.get(STATUS).equals(FAILED)) {
            return Collections.emptyList();
        }
        return JsonParser.parseProblemByTags(result.getJSONObject(RESULT).getJSONArray(PROBLEMS));
    }

    public static List<RatingChange> getRatingChangeByHandle(String handle){
        Map<String, String> param = getRatingChangeParam(handle);
        JSONObject result =  sendGet(param, MethodsConstant.USER_RATING_LIST_METHOD);
        if(Objects.isNull(result) || result.get(STATUS).equals(FAILED)) {
            return Collections.emptyList();
        }
        sleep();
        return JsonParser.parseRatingChange(result.getJSONArray(RESULT));
    }

    private static Map<String, String> getRatingChangeParam(String handle){
        Map<String, String> param = new HashMap <>();
        param.put(HANDLE, handle);
        return param;
    }

    private static Map<String, String> getProblemByTagsParam(List<String> tags){
        Map<String, String> param = new HashMap <>();
        String tagsParam = getParameterListValue(tags);
        param.put(TAGS, tagsParam);
        return param;
    }

    private static JSONObject sendGet(Map<String, String> parameters, String methodsName){
        String Url = getUrl(parameters, methodsName);

        HttpGet httpGet = new HttpGet(Url);
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            String json = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
            return new JSONObject(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getUrl(Map<String, String> parameters, String methodsName){
        try {
            URIBuilder builder = new URIBuilder(DEFAULT_URL + methodsName);
            for(Map.Entry<String, String> entry : parameters.entrySet()){
                builder.addParameter(entry.getKey(), entry.getValue());
            }
            return builder.build().toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "";
    }
    private static String getParameterListValue(List<String> parameters){
        StringBuilder stringBuilder = new StringBuilder();
        parameters.forEach(value -> stringBuilder.append(value).append(";"));
        return stringBuilder.toString();
    }

    public static boolean validateHandle(String[] handles){
        List<String> userHandle = List.of(handles);
        List<UserCF> userCFList = getUserInfoByHandle(userHandle);
        return !userCFList.isEmpty();
    }
    private static Map<String, String> getUserHandleParams(List<String> usersHandle){
        Map<String, String> param = new HashMap <>();
        String handlesValue = getParameterListValue(usersHandle);
        param.put(HANDLES, handlesValue);
        return param;
    }

    private static void sleep(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
