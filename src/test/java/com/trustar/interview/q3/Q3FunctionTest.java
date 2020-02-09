package com.trustar.interview.q3;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Q3FunctionTest {

    @Test
    void test() {
        //given
        List<Pattern> patterns = ImmutableList.of(Pattern.compile("(the)"),
                Pattern.compile("fox|fence"), Pattern.compile("abc"));
        String textToEvaluate = "The fox jump over the fence";
        // when
        List<String> result = Q3Function.matchWithBlackList(patterns, textToEvaluate, Pattern.compile("f.x"));


        //then
        assertEquals(2, result.size());
        assertEquals("the", result.get(0));
        assertEquals("fence", result.get(1));
    }

    @Test
    void test2() {
        //given
        List<Pattern> patterns = ImmutableList.of(
                Pattern.compile("(m.)"), //should 'my' and 'me' but 'me' is blacklisted
                Pattern.compile("in.iting"), //should 'inciting' and 'inviting'
                Pattern.compile("ea\\S*")); //should match ears
        String textToEvaluate = "Through my open ears inciting and inviting me";
        // when
        List<String> result = Q3Function.matchWithBlackList(patterns, textToEvaluate, Pattern.compile("me"));


        //then
        assertEquals(4, result.size());
        assertEquals("my", result.get(0));
        assertEquals("inciting", result.get(1));
        assertEquals("inviting", result.get(2));
        assertEquals("ears", result.get(3));
    }


}
