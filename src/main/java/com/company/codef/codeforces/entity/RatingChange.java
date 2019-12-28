package com.company.codef.codeforces.entity;

import lombok.Data;

@Data
public class RatingChange {
    Long contestId;
    Integer oldRating;
    Integer newRating;
    Long contestUpdateTime;
}
