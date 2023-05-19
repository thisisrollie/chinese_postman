package com.rolliedev;

import com.rolliedev.exceptions.GraphCreationException;
import com.rolliedev.model.DirectedGraph;
import com.rolliedev.model.UndirectedGraph;
import org.junit.Assert;
import org.junit.Test;

public class GraphCreationTest {

    @Test(expected = GraphCreationException.class)
    public void testUndirectedGraphCreationWithEmptyAdjMatrix() {
        final UndirectedGraph graph = UndirectedGraph.getGraphFromAdjMatrix(new int[][]{});
    }

    @Test(expected = GraphCreationException.class)
    public void testDirectedGraphCreationWithEmptyAdjMatrix() {
        final DirectedGraph graph = DirectedGraph.getGraphFromAdjMatrix(new int[][]{});
    }

    @Test(expected = GraphCreationException.class)
    public void testUndirectedGraphCreationWithInvalidAdjMatrix() {
        final UndirectedGraph graph = UndirectedGraph.getGraphFromAdjMatrix(new int[][]{
                {0, 2, 3},
                {2, 0, 6},
                {3, 6, 0, 10}
        });
    }

    @Test(expected = GraphCreationException.class)
    public void testDirectedGraphCreationWithInvalidAdjMatrix() {
        final DirectedGraph graph = DirectedGraph.getGraphFromAdjMatrix(new int[][]{
                {0, 2, 3},
                {0, 0, 6},
                {0, 0, 0, 10}
        });
    }

    @Test
    public void testUndirectedGraphCreationWithValidAdjMatrix() {
        final int[][] validAdjMatrix = new int[][]{
                {0, 1, 2},
                {1, 0, 3},
                {2, 3, 0}
        };
        final UndirectedGraph graph = UndirectedGraph.getGraphFromAdjMatrix(validAdjMatrix);
        int sumOfAllEdges = graph.getSumOfAllEdges();
        Assert.assertEquals("Wrong sum of all edges", 6, sumOfAllEdges);
    }

    @Test
    public void testDirectedGraphCreationWithValidAdjMatrix() {
        final int[][] validAdjMatrix = new int[][]{
                {0, 2, 3},
                {0, 0, 6},
                {0, 0, 0}
        };
        final DirectedGraph graph = DirectedGraph.getGraphFromAdjMatrix(validAdjMatrix);
        int sumOfAllEdges = graph.getSumOfAllEdges();
        Assert.assertEquals("Wrong sum of all edges", 11, sumOfAllEdges);
    }
}
