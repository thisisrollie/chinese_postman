package com.rolliedev;

import com.rolliedev.algo.Dijkstra;
import com.rolliedev.model.DirectedGraph;
import org.junit.Test;

import java.util.List;

import static com.rolliedev.util.GraphConst.INFINITY;
import static org.junit.Assert.assertEquals;

public class DijkstraOnDirectedGraphTest {

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
    private static final DirectedGraph GRAPH_3 = DirectedGraph.getGraphFromAdjMatrix(new int[][]{
            {0, 3, 0, 0, 1, 0, 0, 0},
            {2, 0, 0, 2, 0, 9, 9, 0},
            {0, 2, 0, 0, 5, 0, 0, 0},
            {0, 7, 0, 0, 0, 0, 0, 8},
            {0, 0, 4, 0, 0, 0, 2, 0},
            {0, 0, 0, 0, 0, 0, 0, 4},
            {0, 0, 0, 0, 4, 4, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0}
    });
    private static final DirectedGraph GRAPH_4 = DirectedGraph.getGraphFromAdjMatrix(new int[][]{
            {0, 1, 8, 0, 0, 0, 0, 0},
            {0, 0, 2, 7, 0, 0, 0, 0},
            {0, 3, 0, 0, 4, 0, 0, 0},
            {0, 0, 0, 0, 0, 9, 0, 9},
            {0, 0, 0, 0, 0, 0, 0, 3},
            {0, 9, 0, 5, 0, 0, 8, 0},
            {0, 0, 6, 0, 6, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
    });

    @Test
    public void test1ForGraph1() {
        Dijkstra dijkstra = new Dijkstra(GRAPH_1);
        dijkstra.run(9);
        var actual = dijkstra.getMinDistances();
        var expected = List.of(INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, 16, 8, INFINITY, 9, 0, INFINITY, INFINITY, 5, 8, INFINITY, 12, 10, INFINITY);
        assertEquals(expected, actual);
    }

    @Test
    public void test2ForGraph1() {
        Dijkstra dijkstra = new Dijkstra(GRAPH_1);
        dijkstra.run(8);
        var actual = dijkstra.getMinDistances();
        var expected = List.of(INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, 0, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY);
        assertEquals(expected, actual);
    }

    @Test
    public void test1ForGraph2() {
        Dijkstra dijkstra = new Dijkstra(GRAPH_2);
        dijkstra.run(0);
        var actual = dijkstra.getMinDistances();
        var expected = List.of(0, 5, 4, INFINITY, 9, 13, 8, 5, 12, 21, 17, 3, 21, 19, 2, 5, 13, 11);
        assertEquals(expected, actual);
    }

    @Test
    public void test1ForGraph3() {
        Dijkstra dijkstra = new Dijkstra(GRAPH_3);
        dijkstra.run(1);
        var actual = dijkstra.getMinDistances();
        var expected = List.of(2, 0, 7, 2, 3, 9, 5, 10);
        assertEquals(expected, actual);
    }

    @Test
    public void test1ForGraph4() {
        Dijkstra dijkstra = new Dijkstra(GRAPH_4);
        dijkstra.run(0);
        var actual = dijkstra.getMinDistances();
        var expected = List.of(0, 1, 3, 8, 7, 17, 25, 10);
        assertEquals(expected, actual);
    }
}
