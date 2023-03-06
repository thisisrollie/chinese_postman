package com.rolliedev;

import com.rolliedev.util.GeneratorUtils;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class GeneratorUtilsTest {

    @Test
    public void shouldReturn3() {
        var list = List.of(1, 2, 3, 4);
        var pairs = GeneratorUtils.getPairs(list);
        assertEquals(3, pairs.size());
    }

    @Test
    public void shouldReturn15() {
        var list = List.of(1, 2, 3, 4, 5, 6);
        var pairs = GeneratorUtils.getPairs(list);
        assertEquals(15, pairs.size());
    }

    @Test
    public void shouldReturn945() {
        var list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        var pairs = GeneratorUtils.getPairs(list);
        assertEquals(945, pairs.size());
    }
}
