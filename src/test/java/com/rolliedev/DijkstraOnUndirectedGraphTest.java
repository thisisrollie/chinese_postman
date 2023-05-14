package com.rolliedev;

import com.rolliedev.algo.Dijkstra;
import com.rolliedev.model.UndirectedGraph;
import org.junit.Test;

import java.util.List;

import static com.rolliedev.util.GraphConst.INFINITY;
import static org.junit.Assert.assertEquals;

/**
 * For testing I use website: <a href="https://www.cs.usfca.edu/~galles/visualization/Dijkstra.html">Dijkstra Shortest Path Visualization</a>
 */
public class DijkstraOnUndirectedGraphTest {

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
    private static final UndirectedGraph GRAPH_3 = UndirectedGraph.getGraphFromAdjMatrix(new int[][]{
            {0, 0, 7, 0, 8, 0, 0, 0},
            {0, 0, 8, 0, 0, 0, 0, 0},
            {7, 8, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 7, 0, 0},
            {8, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 7, 0, 0, 0, 2},
            {0, 0, 0, 0, 0, 0, 0, 4},
            {0, 0, 0, 0, 0, 2, 4, 0},
    });
    private static final UndirectedGraph GRAPH_4 = UndirectedGraph.getGraphFromAdjMatrix(new int[][]{
            {0, 6, 0, 8, 2, 0, 0, 0},
            {6, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {8, 0, 0, 0, 0, 0, 0, 0},
            {2, 0, 0, 0, 0, 0, 0, 4},
            {0, 0, 0, 0, 0, 0, 5, 0},
            {0, 0, 0, 0, 0, 5, 0, 0},
            {0, 0, 0, 0, 4, 0, 0, 0}
    });
    private static final UndirectedGraph GRAPH_5 = UndirectedGraph.getGraphFromAdjMatrix(new int[][]{
//           0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17
            {0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 6, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 6, 0, 5, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 2},
            {0, 3, 0, 0, 0, 7, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 8, 0, 0, 2, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 5, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 5, 0, 7, 2, 0, 0, 0, 0},
            {0, 0, 0, 3, 0, 0, 8, 0, 0, 5, 0, 0, 0, 3, 0, 0, 0, 2},
            {0, 0, 0, 0, 0, 0, 0, 8, 5, 0, 0, 0, 0, 0, 0, 7, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 5, 0, 0, 5, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3, 0, 5, 0, 0, 0, 5, 6},
            {0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 3, 9},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 0, 3, 0, 9},
            {0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 0, 6, 0, 9, 9, 0}
    });

    @Test
    public void test1ForGraph1() {
        Dijkstra dijkstra = new Dijkstra(GRAPH_1);
        dijkstra.run(0);
        var actual = dijkstra.getMinDistances();
        var expected = List.of(0, 2, 4, 6, 9, 8, 9, 4, 8, 12, 16, 8, 11, 15, 5, 10, 16, 11);
        assertEquals(expected, actual);
    }

    @Test
    public void test2ForGraph1() {
        Dijkstra dijkstra = new Dijkstra(GRAPH_1);
        dijkstra.run(5);
        var actual = dijkstra.getMinDistances();
        var expected = List.of(8, 7, 4, 6, 1, 0, 1, 9, 8, 4, 16, 13, 11, 15, 10, 15, 16, 11);
        assertEquals(expected, actual);
    }

    @Test
    public void test3ForGraph1() {
        Dijkstra dijkstra = new Dijkstra(GRAPH_1);
        dijkstra.run(14);
        var actual = dijkstra.getMinDistances();
        var expected = List.of(5, 7, 9, 11, 9, 10, 11, 1, 5, 9, 21, 3, 8, 12, 0, 5, 11, 16);
        assertEquals(expected, actual);
    }

    @Test
    public void test1ForGraph2() {
        Dijkstra dijkstra = new Dijkstra(GRAPH_2);
        dijkstra.run(6);
        var actual = dijkstra.getMinDistances();
        var expected = List.of(16, 8, 14, 8, 15, 1, 0, 17, 10, 3, 7, 17, 16, 9, 23, 20, 13, 14);
        assertEquals(expected, actual);
    }

    @Test
    public void test1ForGraph3() {
        Dijkstra dijkstra = new Dijkstra(GRAPH_3);
        dijkstra.run(0);
        var actual = dijkstra.getMinDistances();
        var expected = List.of(0, 15, 7, INFINITY, 8, INFINITY, INFINITY, INFINITY);
        assertEquals(expected, actual);
    }

    @Test
    public void test2ForGraph3() {
        Dijkstra dijkstra = new Dijkstra(GRAPH_3);
        dijkstra.run(6);
        var actual = dijkstra.getMinDistances();
        var expected = List.of(INFINITY, INFINITY, INFINITY, 13, INFINITY, 6, 0, 4);
        assertEquals(expected, actual);
    }

    @Test
    public void test1ForGraph4() {
        Dijkstra dijkstra = new Dijkstra(GRAPH_4);
        dijkstra.run(2);
        var actual = dijkstra.getMinDistances();
        var expected = List.of(INFINITY, INFINITY, 0, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY);
        assertEquals(expected, actual);
    }

    @Test
    public void test2ForGraph4() {
        Dijkstra dijkstra = new Dijkstra(GRAPH_4);
        dijkstra.run(7);
        var actual = dijkstra.getMinDistances();
        var expected = List.of(6, 12, INFINITY, 14, 4, INFINITY, INFINITY, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void test1ForGraph5() {
        Dijkstra dijkstra = new Dijkstra(GRAPH_5);
        dijkstra.run(5);
        var actual = dijkstra.getMinDistances();
        var expected = List.of(21, 10, 16, 21, 7, 0, 22, 13, 26, 29, 24, 21, 32, 27, 15, 28, 31, 23);
        assertEquals(expected, actual);
    }

    @Test
    public void test2ForGraph5() {
        Dijkstra dijkstra = new Dijkstra(GRAPH_5);
        dijkstra.run(12);
        var actual = dijkstra.getMinDistances();
        var expected = List.of(31, 22, 16, 11, 25, 32, 16, 23, 15, 7, 8, 15, 0, 5, 25, 8, 5, 10);
        assertEquals(expected, actual);
    }
}
