package com.trustar.interview.q4;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class APTExtractorTest {

    @Test
    public void extract() throws Exception {
        APTExtractor extractor = new APTExtractor();
        List<APTInfo> list = extractor.extract();
        for (APTInfo apt : list)
            assertTrue(apt.getName().matches("APT\\d{2}"));
    }

}
