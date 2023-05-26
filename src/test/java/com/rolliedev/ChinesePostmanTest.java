package com.rolliedev;

import com.rolliedev.algo.Dijkstra;
import com.rolliedev.model.UndirectedGraph;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ChinesePostmanTest {

    private static final int START_VERTEX_IDX = 0; // euler cycle will start with this index of vertex
    private static ChinesePostman<UndirectedGraph> chinesePostman;

    @BeforeClass
    public static void beforeClass() throws Exception {
        chinesePostman = new ChinesePostman<>();
    }

    @Test
    public void test1() {
        UndirectedGraph graph = UndirectedGraph.getGraphFromAdjMatrix(new int[][]{
//               A  B  C  D  E  F  G  H
//               0  1  2  3  4  5  6  7
                {0, 50, 50, 50, 0, 0, 0, 0},
                {50, 0, 0, 50, 70, 50, 0, 0},
                {50, 0, 0, 70, 0, 0, 70, 120},
                {50, 50, 70, 0, 0, 60, 0, 0},
                {0, 70, 0, 0, 0, 70, 0, 0},
                {0, 50, 0, 60, 70, 0, 0, 60},
                {0, 0, 70, 0, 0, 0, 0, 70},
                {0, 0, 120, 0, 0, 60, 70, 0}
        });
        List<Integer> postmanRoute = chinesePostman.run(new Dijkstra(graph), graph, START_VERTEX_IDX);

        int expectedLengthOfRoute = 1000;
        // sum of all edges (their weights) of the euler graph will be a length of postman's route
        int actualLengthOfRoute = graph.getSumOfAllEdges();
        // checks if the postman's route starts and ends at the same vertex
        assertEquals("The postman's route does not start and end at the same vertex", postmanRoute.get(0), postmanRoute.get(postmanRoute.size() - 1));
        assertEquals("Incorrect length of postman's route", expectedLengthOfRoute, actualLengthOfRoute);
    }

    @Test
    public void test2() {
        UndirectedGraph graph = UndirectedGraph.getGraphFromAdjMatrix(new int[][]{
//               A  B  C  D  E  F  G  H
//               0  1  2  3  4  5  6  7
                {0, 6, 6, 7, 0, 0, 0, 0},
                {6, 0, 0, 5, 10, 0, 0, 0},
                {6, 0, 0, 8, 0, 7, 0, 0},
                {7, 5, 8, 0, 6, 11, 9, 0},
                {0, 10, 0, 6, 0, 0, 8, 7},
                {0, 0, 7, 11, 0, 0, 10, 9},
                {0, 0, 0, 9, 8, 10, 0, 5},
                {0, 0, 0, 0, 7, 9, 5, 0}
        });
        List<Integer> postmanRoute = chinesePostman.run(new Dijkstra(graph), graph, START_VERTEX_IDX);

        int expectedLengthOfRoute = 136;
        int actualLengthOfRoute = graph.getSumOfAllEdges();
        assertEquals("The postman's route does not start and end at the same vertex", postmanRoute.get(0), postmanRoute.get(postmanRoute.size() - 1));
        assertEquals("Incorrect length of postman's route", expectedLengthOfRoute, actualLengthOfRoute);
    }

    @Test
    public void test3() {
        UndirectedGraph graph = UndirectedGraph.getGraphFromAdjMatrix(new int[][]{
//               0  1  2  3  4  5  6  7  8  9
//               C  D  E  F  G  I  J  K  L  M
                {0, 120, 0, 0, 0, 250, 0, 0, 0, 0},
                {120, 0, 150, 0, 0, 300, 250, 0, 0, 0},
                {0, 150, 0, 80, 0, 0, 0, 280, 0, 0},
                {0, 0, 80, 0, 100, 0, 0, 0, 250, 0},
                {0, 0, 0, 100, 0, 0, 0, 0, 0, 250},
                {250, 300, 0, 0, 0, 0, 120, 0, 0, 0},
                {0, 250, 0, 0, 0, 120, 0, 80, 0, 0},
                {0, 0, 280, 0, 0, 0, 80, 0, 150, 0},
                {0, 0, 0, 250, 0, 0, 0, 150, 0, 100},
                {0, 0, 0, 0, 250, 0, 0, 0, 100, 0}
        });
        List<Integer> postmanRoute = chinesePostman.run(new Dijkstra(graph), graph, START_VERTEX_IDX);

        int expectedLengthOfRoute = 2830;
        int actualLengthOfRoute = graph.getSumOfAllEdges();
        assertEquals("The postman's route does not start and end at the same vertex", postmanRoute.get(0), postmanRoute.get(postmanRoute.size() - 1));
        assertEquals("Incorrect length of postman's route", expectedLengthOfRoute, actualLengthOfRoute);
    }

    @Test
    public void test4() {
        UndirectedGraph graph = UndirectedGraph.getGraphFromAdjMatrix(new int[][]{
//               a  b  c  d  e  f
                {0, 3, 1, 0, 5, 0},
                {3, 0, 0, 1, 0, 6},
                {1, 0, 0, 0, 2, 0},
                {0, 1, 0, 0, 0, 1},
                {5, 0, 2, 0, 0, 4},
                {0, 6, 0, 1, 4, 0}
        });
        List<Integer> postmanRoute = chinesePostman.run(new Dijkstra(graph), graph, START_VERTEX_IDX);

        int expectedLengthOfRoute = 28;
        int actualLengthOfRoute = graph.getSumOfAllEdges();
        assertEquals("The postman's route does not start and end at the same vertex", postmanRoute.get(0), postmanRoute.get(postmanRoute.size() - 1));
        assertEquals("Incorrect length of postman's route", expectedLengthOfRoute, actualLengthOfRoute);
    }

    @Test
    public void testWithEulerGraphInput() {
        UndirectedGraph triangle = UndirectedGraph.getGraphFromAdjMatrix(new int[][]{
                {0, 2, 3},
                {2, 0, 4},
                {3, 4, 0}
        });
        List<Integer> postmanRoute = chinesePostman.run(new Dijkstra(triangle), triangle, START_VERTEX_IDX);

        int expectedLengthOfRoute = 9;
        int actualLengthOfRoute = triangle.getSumOfAllEdges();
        assertEquals("The postman's route does not start and end at the same vertex", postmanRoute.get(0), postmanRoute.get(postmanRoute.size() - 1));
        assertEquals("Incorrect length of postman's route", expectedLengthOfRoute, actualLengthOfRoute);
    }

    @Test
    public void test5() {
        UndirectedGraph graph = UndirectedGraph.getGraphFromAdjMatrix(new int[][]{
//               a  b  c  d  e  f  g  h
                {0, 16, 3, 0, 0, 0, 0, 6},
                {16, 0, 0, 0, 19, 0, 20, 18},
                {3, 0, 0, 17, 0, 0, 0, 11},
                {0, 0, 17, 0, 0, 0, 14, 0},
                {0, 19, 0, 0, 0, 9, 0, 5},
                {0, 0, 0, 0, 9, 0, 7, 0},
                {0, 20, 0, 14, 0, 7, 0, 0},
                {6, 18, 11, 0, 5, 0, 0, 0}
        });
        List<Integer> postmanRoute = chinesePostman.run(new Dijkstra(graph), graph, START_VERTEX_IDX);

        int expectedLengthOfRoute = 164;
        int actualLengthOfRoute = graph.getSumOfAllEdges();
        assertEquals("The postman's route does not start and end at the same vertex", postmanRoute.get(0), postmanRoute.get(postmanRoute.size() - 1));
        assertEquals("Incorrect length of postman's route", expectedLengthOfRoute, actualLengthOfRoute);
    }
}
