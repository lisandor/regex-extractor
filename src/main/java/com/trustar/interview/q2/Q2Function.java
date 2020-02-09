package com.trustar.interview.q2;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This Class implements a static method that solves question 2.
 */
class Q2Function {

    /**
     * It takes a list of patterns and a string to evaluate and return a List of substring that matches those patterns.
     * There is a restriction that says if a substring matches one pattern, cannot be used to match any subsequent pattern.
     * To avoid substring reuse, every matched substring is stored and then removed from textToEval before another match/pattern is applied.
     *
     * @param patterns
     * @param textToEval
     * @return the list of matches that satisfy the following rule: A matched substring cannot be used by any subsequent pattern
     */
    public static List<String> matchFirstsOcurrencies(List<Pattern> patterns, String textToEval) {
        List<String> result = new ArrayList<>();
        for (Pattern pattern : patterns) {
            Matcher m = pattern.matcher(textToEval);
            while (m.find()) {
                result.add(m.group());
                textToEval = textToEval.replaceFirst(pattern.pattern(), "");
            }
        }
        return result;
    }
}
