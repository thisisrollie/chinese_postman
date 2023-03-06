package com.rolliedev;

import com.rolliedev.model.Graph;
import com.rolliedev.util.GeneratorUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FindPairsTest {

    @Test
    public void shouldGive1() {
        /**
         * source: https://towardsdatascience.com/chinese-postman-in-python-8b1187a3e5a#aaef
         */
        Graph graph = Graph.getGraphFromAdjMatrix(new int[][]{
                {0, 4, 0, 1, 2},
                {4, 0, 5, 0, 0},
                {0, 5, 0, 7, 0},
                {1, 0, 7, 0, 3},
                {2, 0, 0, 3, 0}
        });
        var actual = getCountOfPairs(graph);
        assertEquals(1, actual);
    }

    @Test
    public void shouldGive3() {
        /**
         * source: https://www.savemyexams.co.uk/dp/maths_ai-hl/ib/21/revision-notes/3-geometry--trigonometry/3-10-graph-theory/3-10-4-chinese-postman-problem/
         */
        Graph graph = Graph.getGraphFromAdjMatrix(new int[][]{
                {0, 15, 0, 8, 17},
                {15, 0, 9, 4, 0},
                {0, 9, 0, 10, 6},
                {8, 4, 10, 0, 0},
                {17, 0, 6, 0, 0}
        });
        var actual = getCountOfPairs(graph);
        assertEquals(3, actual);
    }

    private int getCountOfPairs(Graph graph) {
        var oddIndexes = graph.getOddDegreeVertices().stream()
                .map(Graph.Vertex::getIdx)
                .toList();

        var pairs = GeneratorUtils.getPairs(oddIndexes);
        return pairs.size();
    }
}
