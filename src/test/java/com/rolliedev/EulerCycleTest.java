package com.rolliedev;

import com.rolliedev.model.Graph;
import com.rolliedev.util.GraphUtils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EulerCycleTest {

    @Test
    public void test1() {
        Graph graph = Graph.getGraphFromAdjMatrix(new int[][]{
//               0  1  2  3  4
                {0, 1, 1, 1, 1},
                {1, 0, 1, 0, 0},
                {1, 1, 0, 0, 0},
                {1, 0, 0, 0, 1},
                {1, 0, 0, 1, 0}
        });
        checkMethod(graph);
    }

    @Test
    public void test2() {
        Graph graph = Graph.getGraphFromAdjMatrix(new int[][]{
//               A  B  C  D  E  F
                {0, 1, 0, 0, 1, 1},
                {1, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 1, 0},
                {0, 0, 1, 0, 1, 1},
                {1, 1, 1, 1, 0, 1},
                {1, 0, 0, 1, 1, 0}
        });
        graph.getEdge(graph.getVertexByIdx(0), graph.getVertexByIdx(4)).addRepeat();
        graph.getEdge(graph.getVertexByIdx(4), graph.getVertexByIdx(0)).addRepeat();
        graph.getEdge(graph.getVertexByIdx(1), graph.getVertexByIdx(2)).addRepeat();
        graph.getEdge(graph.getVertexByIdx(2), graph.getVertexByIdx(1)).addRepeat();
        graph.getEdge(graph.getVertexByIdx(3), graph.getVertexByIdx(5)).addRepeat();
        graph.getEdge(graph.getVertexByIdx(5), graph.getVertexByIdx(3)).addRepeat();

        checkMethod(graph);
    }

    private void checkMethod(Graph graph) {
        var sum = graph.getVertices().stream()
                .mapToInt(vertex -> graph.getAllNeighbours(vertex).size())
                .sum();
        var eulerCycle = GraphUtils.getEulerCycle(graph);

        assertTrue(eulerCycle.get(0).equals(eulerCycle.get(eulerCycle.size() - 1))
                && eulerCycle.size() == sum / 2 + 1);
    }
}
