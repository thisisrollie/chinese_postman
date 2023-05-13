package com.rolliedev;

import com.rolliedev.algo.BellmanFord;
import com.rolliedev.model.UndirectedGraph;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BellmanFordOnUndirectedGraphTest {

    private static final UndirectedGraph GRAPH_1 = UndirectedGraph.getGraphFromAdjMatrix(new int[][]{
            {0, 2, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0},
            {2, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {4, 3, 0, 2, 0, 4, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
            {0, 0, 0, 0, 0, 1, 0, 8, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 4, 0, 1, 0, 1, 0, 9, 4, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 9, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {4, 0, 0, 0, 8, 0, 0, 0, 4, 0, 0, 5, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 7, 9, 0, 4, 0, 4, 0, 0, 3, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 4, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 5},
            {0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 4, 0, 4, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 4, 0, 0, 0, 1, 0},
            {6, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 3, 0, 0, 0, 5, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 5, 0, 6, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 6, 0, 0},
            {0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0}
    });
    private static final UndirectedGraph GRAPH_2 = UndirectedGraph.getGraphFromAdjMatrix(new int[][]{
            {0, 8, 8, 0, 7, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {8, 0, 8, 0, 7, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {8, 8, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 6, 0, 0, 0, 8, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 7},
            {7, 7, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 7, 0, 0, 0, 0, 1, 0, 9, 2, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 8, 0, 1, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 6, 0, 0, 0, 7, 0, 0, 9, 0, 0, 8, 0, 0, 0},
            {0, 0, 0, 0, 0, 9, 0, 7, 0, 8, 0, 7, 9, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 2, 0, 0, 8, 0, 4, 0, 0, 6, 0, 0, 0, 0},
            {0, 0, 0, 2, 0, 0, 8, 0, 0, 4, 0, 0, 0, 9, 0, 0, 0, 8},
            {0, 0, 0, 0, 0, 0, 0, 9, 7, 0, 0, 0, 9, 0, 6, 4, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 9, 0, 0, 0, 0, 3, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 9, 0, 0, 0, 0, 0, 4, 5},
            {0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 6},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 4, 0, 0, 0, 0},
            {0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 8, 0, 0, 5, 0, 6, 0, 0}
    });

    @Test
    public void test1ForGraph1() {
        BellmanFord bellman = new BellmanFord(GRAPH_1);
        bellman.run(0);
        var actual = bellman.getMinDistances();
        var expected = List.of(0, 2, 4, 6, 9, 8, 9, 4, 8, 12, 16, 8, 11, 15, 5, 10, 16, 11);
        assertEquals(expected, actual);
    }

    @Test
    public void test2ForGraph1() {
        BellmanFord bellman = new BellmanFord(GRAPH_1);
        bellman.run(5);
        var actual = bellman.getMinDistances();
        var expected = List.of(8, 7, 4, 6, 1, 0, 1, 9, 8, 4, 16, 13, 11, 15, 10, 15, 16, 11);
        assertEquals(expected, actual);
    }

    @Test
    public void test3ForGraph1() {
        BellmanFord bellman = new BellmanFord(GRAPH_1);
        bellman.run(14);
        var actual = bellman.getMinDistances();
        var expected = List.of(5, 7, 9, 11, 9, 10, 11, 1, 5, 9, 21, 3, 8, 12, 0, 5, 11, 16);
        assertEquals(expected, actual);
    }

    @Test
    public void test1ForGraph2() {
        BellmanFord bellman = new BellmanFord(GRAPH_2);
        bellman.run(6);
        var actual = bellman.getMinDistances();
        var expected = List.of(16, 8, 14, 8, 15, 1, 0, 17, 10, 3, 7, 17, 16, 9, 23, 20, 13, 14);
        assertEquals(expected, actual);
    }
}
