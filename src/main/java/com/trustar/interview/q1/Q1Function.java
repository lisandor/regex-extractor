package com.trustar.interview.q1;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This Class implements a static method that solves question 1.
 */
public class Q1Function {

    /**
     * This method apply pattern list sequentially and store matches in a List in order to return once it finish.
     *
     * @param patterns
     * @param textToEval
     * @return all the matches for the patterns found in the string.
     */
    public static List<String> matchAll(List<Pattern> patterns, String textToEval) {
        List<String> result = new ArrayList<>();
        for (Pattern pattern : patterns) {
            Matcher m = pattern.matcher(textToEval);
            while (m.find()) {
                result.add(m.group());
            }
        }
        return result;
    }
}
