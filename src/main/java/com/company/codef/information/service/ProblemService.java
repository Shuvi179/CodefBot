package com.company.codef.information.service;

import com.company.codef.codeforces.entity.Problem;

import java.util.List;

public interface ProblemService {
    Problem getRandomProblemByTagAndRating(List<String> tags, String rating);
    boolean validateRating(String rating);
}
