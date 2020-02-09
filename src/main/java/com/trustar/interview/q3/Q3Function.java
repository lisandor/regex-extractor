package com.trustar.interview.q3;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This Class implements a public static method that solves question 3 and a private helper method.
 */
class Q3Function {

    /**
     * As this method receives a blacklist pattern, this is applied first in order to remove those substrings that can
     * also be potential matches to the "positive" patterns.
     * Then return all the "positive" patterns matches.
     *
     * @param patterns
     * @param textToEval
     * @param blacklistedPattern
     * @return
     */
    public static List<String> matchWithBlackList(List<Pattern> patterns, String textToEval, Pattern blacklistedPattern) {
        List<String> result = new ArrayList<>();
        textToEval = removeBlacklisted(textToEval, blacklistedPattern);
        for (Pattern pattern : patterns) {
            Matcher m = pattern.matcher(textToEval);
            while (m.find()) {
                result.add(m.group());
            }
        }
        return result;
    }

    private static String removeBlacklisted(String textToEval, Pattern blacklistedPattern) {
        return blacklistedPattern.matcher(textToEval).replaceAll("");
    }
}
