package com.rolliedev;

import com.rolliedev.util.GeneratorUtils;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class GeneratorUtilsTest {

    @Test
    public void shouldReturn3() {
        var list = List.of(1, 2, 3, 4);
        var combinations = GeneratorUtils.getCombinations(list);
        assertEquals(3, combinations.size());
    }

    @Test
    public void shouldReturn15() {
        var list = List.of(1, 2, 3, 4, 5, 6);
        var combinations = GeneratorUtils.getCombinations(list);
        assertEquals(15, combinations.size());
    }

    @Test
    public void shouldReturn105() {
        var list = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        var combinations = GeneratorUtils.getCombinations(list);
        assertEquals(105, combinations.size());
    }

    @Test
    public void shouldReturn945() {
        var list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        var combinations = GeneratorUtils.getCombinations(list);
        assertEquals(945, combinations.size());
    }

//    @Test
//    public void shouldReturn10_395() {
//        var list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
//        var combinations = GeneratorUtils.getCombinations(list);
//        assertEquals(10_395, combinations.size());
//    }

    // TODO: 5/16/23 try to run this test
//    @Test
//    public void shouldReturn135_135() {
//        var list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
//        var combinations = GeneratorUtils.getCombinations(list);
//        assertEquals(135_135, combinations.size());
//    }
}
