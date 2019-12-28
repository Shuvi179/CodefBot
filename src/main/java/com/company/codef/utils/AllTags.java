package com.company.codef.utils;

import java.util.*;
import java.util.stream.Collectors;

public class AllTags {
    public static Map<String, String> validCode = new HashMap <>();
    static {
        validCode = new HashMap <>();
        validCode.put("2sat", "2-sat");
        validCode.put("bs", "binary search");
        validCode.put("meet", "meet-in-the-middle");
        validCode.put("graphs", "graphs");
        validCode.put("trees", "trees");
        validCode.put("math", "math");
        validCode.put("combinatorics", "combinatorics");
        validCode.put("dsu", "dsu");
        validCode.put("matrices", "matrices");
        validCode.put("geometry", "geometry");
        validCode.put("games", "games");
        validCode.put("fft", "fft");
        validCode.put("crt", "chinese remainder theorem");
        validCode.put("ts", "ternary search");
        validCode.put("nt", "number theory");
        validCode.put("sort", "sortings");
        validCode.put("constructive", "constructive algorithms");
        validCode.put("hashing", "hashing");
        validCode.put("divide", "divide and conquer");
        validCode.put("interactive", "interactive");
        validCode.put("strings", "strings");
        validCode.put("implementation", "implementation");
        validCode.put("brute", "brute force");
        validCode.put("dfs", "dfs and similar");
        validCode.put("struct", "data structures");
        validCode.put("sp", "shortest paths");
        validCode.put("greedy", "greedy");
        validCode.put("probabilities", "probabilities");
        validCode.put("dp", "dp");
    }

    public static boolean validateTags(List<String> tags){
        return tags.stream().noneMatch(tag -> Objects.isNull(validCode.get(tag)));
    }

    public static List<String> getValidTag(List<String> botTags){
        return botTags.stream().map(tag -> validCode.get(tag)).collect(Collectors.toList());
    }
}
