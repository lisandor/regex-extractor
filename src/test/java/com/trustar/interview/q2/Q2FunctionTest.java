package com.trustar.interview.q2;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Q2FunctionTest {

    @Test
    void test1() {
        //given
        List<Pattern> patterns = ImmutableList.of(Pattern.compile("th."),
                Pattern.compile("he"),
                Pattern.compile("fox"));
        String textToEvaluate = "The fox jump over the fence";
        // when
        List<String> result = Q2Function.matchFirstsOcurrencies(patterns, textToEvaluate);

        //then
        assertEquals(3, result.size());
        assertEquals("the", result.get(0));
        assertEquals("he", result.get(1));
        assertEquals("fox", result.get(2));
    }

    @Test
    void test2() {
        //given
        List<Pattern> patterns = ImmutableList.of(
                Pattern.compile("night"), // this match night
                Pattern.compile(".ight")); // this should match light and night, but night was consumed by previous pattern
        String textToEvaluate = "Into the light of the dark black night";
        // when
        List<String> result = Q2Function.matchFirstsOcurrencies(patterns, textToEvaluate);

        //then
        assertEquals(2, result.size());
        assertEquals("night", result.get(0));
        assertEquals("light", result.get(1));
    }

    @Test
    void test3() {
        //given
        List<Pattern> patterns = ImmutableList.of(
                Pattern.compile("ing"), // this match twice
                Pattern.compile("in")); // this should match  's(in)g(ing) (in)', but the two firsts (in) were consumed by previous pattern
        String textToEvaluate = "Blackbird singing in the dead of night";
        // when
        List<String> result = Q2Function.matchFirstsOcurrencies(patterns, textToEvaluate);

        //then
        assertEquals(3, result.size());
        assertEquals("ing", result.get(0));
        assertEquals("ing", result.get(1));
        assertEquals("in", result.get(2));

    }


}
