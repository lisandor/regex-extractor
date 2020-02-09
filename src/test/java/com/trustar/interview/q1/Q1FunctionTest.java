package com.trustar.interview.q1;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Q1FunctionTest {

    @Test
    void test1() {

        //given
        List<Pattern> patterns = ImmutableList.of(Pattern.compile("(the)"),
                Pattern.compile("fox|(f.nce)"));
        String textToEvaluate = "The fox jump over the fence";
        // when
        List<String> result = Q1Function.matchAll(patterns, textToEvaluate);


        //then
        assertEquals(3, result.size());
        assertEquals("the", result.get(0));
        assertEquals("fox", result.get(1));
        assertEquals("fence", result.get(2));
    }


    @Test
    void test2() {

        //given
        List<Pattern> patterns = ImmutableList.of(Pattern.compile("(th.)"),
                Pattern.compile("he"),
                Pattern.compile("fox|(f.nce)"));
        String textToEvaluate = "The fox jump over the fence";
        // when
        List<String> result = Q1Function.matchAll(patterns, textToEvaluate);


        //then
        assertEquals(5, result.size());
        assertEquals("the", result.get(0));
        assertEquals("he", result.get(1)); //this match is the "he" part of "The"
        assertEquals("he", result.get(2)); //this match is the "he" part of "the"
        assertEquals("fox", result.get(3));
        assertEquals("fence", result.get(4));
    }

}
