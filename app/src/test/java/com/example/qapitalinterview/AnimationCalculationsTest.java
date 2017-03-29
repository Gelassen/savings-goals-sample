package com.example.qapitalinterview;


import org.junit.Test;

import static org.junit.Assert.*;

public class AnimationCalculationsTest {

    @Test
    public void testRatioCreation() throws Exception {
        final int input = 150;
        final int totalSize = 288;

        final float result = (float) 150 / 288;
        assertEquals(0.52, result, 0.001);
    }
}
