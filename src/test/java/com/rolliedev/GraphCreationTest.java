package com.rolliedev;

import com.rolliedev.exceptions.GraphCreationException;
import com.rolliedev.model.UndirectedGraph;
import org.junit.Assert;
import org.junit.Test;

public class GraphCreationTest {

    @Test(expected = GraphCreationException.class)
    public void testGraphCreationWithEmptyAdjMatrix() {
        final UndirectedGraph graph = UndirectedGraph.getGraphFromAdjMatrix(new int[][]{});
    }

    @Test(expected = GraphCreationException.class)
    public void testGraphCreationWithInvalidAdjMatrix() {
        final UndirectedGraph graph = UndirectedGraph.getGraphFromAdjMatrix(new int[][]{
                {0, 2, 3},
                {4, 0, 6},
                {7, 8, 0, 10}
        });
    }

    @Test
    public void testGraphCreationWithValidAdjMatrix() {
        final int[][] validAdjMatrix = new int[][]{
                {0, 1, 2},
                {1, 0, 3},
                {2, 3, 0}
        };
        final UndirectedGraph graph = UndirectedGraph.getGraphFromAdjMatrix(validAdjMatrix);
        int sumOfAllEdges = graph.getSumOfAllEdges();
        Assert.assertEquals(6, sumOfAllEdges);
    }
}
