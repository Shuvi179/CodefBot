package com.company.codef.codeforces.entity;

import lombok.Data;

import java.util.List;

@Data
public class Problem {
    Long contestId;
    String name;
    Integer rating;
    String index;
    List<String> tags;
}
