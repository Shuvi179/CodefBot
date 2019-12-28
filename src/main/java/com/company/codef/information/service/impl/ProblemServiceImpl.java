package com.company.codef.information.service.impl;

import com.company.codef.codeforces.entity.Problem;
import com.company.codef.codeforces.httpClient.CodefHttpClient;
import com.company.codef.information.service.ProblemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Override
    public Problem getRandomProblemByTagAndRating(List<String> tags, String rating) {
        String[] needRating = rating.split("-");
        Long low = Long.valueOf(needRating[0]);
        Long high = Long.valueOf(needRating[1]);
        List<Problem> problemByTag = CodefHttpClient.getRandomProblemByTags(tags);
        List<Problem> validProblem = getValidProblemByRating(problemByTag, low, high);
        return getRandomProblem(validProblem);
    }

    public boolean validateRating(String rate){
        String[] rating = rate.split("-");
        if(rating.length != 2){
            return false;
        }
        Long low = Long.valueOf(rating[0]);
        Long high = Long.valueOf(rating[1]);
        return high >= low;
    }

    private List<Problem> getValidProblemByRating(List<Problem> problems, Long low, Long high){
        return problems.stream()
                .filter(problem -> problem.getRating() >= low && problem.getRating() <= high)
                .collect(Collectors.toList());
    }

    private Problem getRandomProblem(List<Problem> problems){
        if(problems.isEmpty()){
            return null;
        }
        int index = (int) (Math.random() * problems.size());
        return problems.get(index);
    }

}
