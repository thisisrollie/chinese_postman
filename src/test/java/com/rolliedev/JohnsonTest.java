package com.rolliedev;

import com.rolliedev.algo.Johnson;
import com.rolliedev.model.DirectedGraph;
import org.junit.Test;

import static com.rolliedev.util.GraphConst.INFINITY;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class JohnsonTest {

    private static final DirectedGraph GRAPH_1 = DirectedGraph.getGraphFromAdjMatrix(new int[][]{
//           0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17
            {0, 9, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 9, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
            {4, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 8, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 5, 8, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 7, 9, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 2, 0, 0, 0, 2, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 5, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 1, 0, 0, 4, 0}
    });
    private static final DirectedGraph GRAPH_2 = DirectedGraph.getGraphFromAdjMatrix(new int[][]{
            {0, 4, 2, 8, 9, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 3, 0},
            {0, 0, 0, 0, 3, 0, 1, 0},
            {0, 0, 0, 0, 0, 6, 0, 0},
            {0, 0, 6, 0, 0, 0, 0, 1},
            {0, 0, 0, 8, 0, 0, 0, 0},
            {0, 0, 5, 0, 8, 0, 0, 0},
            {0, 0, 0, 0, 0, 8, 1, 0}
    });
    private static final DirectedGraph GRAPH_3 = DirectedGraph.getGraphFromAdjMatrix(new int[][]{
//           0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17
            {0, 0, 4, 0, 9, 0, 0, 5, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0},
            {0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 9, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 5, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0},
            {0, 0, 0, 0, 7, 6, 0, 0, 0, 9, 0, 0, 9, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 2, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 8, 6},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0}
    });
    private static final DirectedGraph GRAPH_WITH_NEG_CYCLE = DirectedGraph.getGraphFromAdjMatrix(new int[][]{
            {0, 1, 0, 0},
            {0, 0, -1, 0},
            {0, 0, 0, -1},
            {-1, 0, 0, 0}
    });

    @Test
    public void testForGraph1() {
        int[][] minDistances = Johnson.run(GRAPH_1);
        int[] expected8Line = new int[]{INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, 0, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY};
        int[] expected9Line = new int[]{INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, 16, 8, INFINITY, 9, 0, INFINITY, INFINITY, 5, 8, INFINITY, 12, 10, INFINITY};
        assertArrayEquals(expected8Line, minDistances[8]);
        assertArrayEquals(expected9Line, minDistances[9]);
    }

    @Test
    public void testForGraph2() {
        int[][] expected = new int[][]{
                {0, 4, 2, 8, 5, 14, 3, 6},
                {1, 0, 3, 9, 6, 15, 3, 7},
                {INFINITY, INFINITY, 0, 20, 3, 12, 1, 4},
                {INFINITY, INFINITY, INFINITY, 0, INFINITY, 6, INFINITY, INFINITY},
                {INFINITY, INFINITY, 6, 17, 0, 9, 2, 1},
                {INFINITY, INFINITY, INFINITY, 8, INFINITY, 0, INFINITY, INFINITY},
                {INFINITY, INFINITY, 5, 25, 8, 17, 0, 9},
                {INFINITY, INFINITY, 6, 16, 9, 8, 1, 0}
        };
        int[][] actualMinDist = Johnson.run(GRAPH_2);
        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals(expected[i], actualMinDist[i]);
        }
    }

    @Test
    public void testForGraph3() {
        int[][] minDistances = Johnson.run(GRAPH_3);
        int[] expected1Line = new int[]{0, 5, 4, INFINITY, 9, 13, 8, 5, 12, 21, 17, 3, 21, 19, 2, 5, 13, 11};
        assertArrayEquals(expected1Line, minDistances[0]);
    }

    @Test
    public void testForGraphWithNegCycleShouldReturnEmpty2DMatrix() {
        int[][] minDist = Johnson.run(GRAPH_WITH_NEG_CYCLE);
        assertEquals(0, minDist.length);
    }
}
