package com.rolliedev;

import com.rolliedev.algo.Dijkstra;
import com.rolliedev.model.DirectedGraph;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DijkstraOnDirectedGraphTest {

    private static Dijkstra dijkstra;

    @BeforeClass
    public static void beforeClass() throws Exception {
        dijkstra = new Dijkstra();
    }

    @Test
    public void test1FotGraph1() {
        DirectedGraph graph = DirectedGraph.getGraphFromAdjMatrix(new int[][]{

        });
        dijkstra.run(graph, 0);
        var actual = dijkstra.getMinDistances();
        var expected = List.of();
        assertEquals(expected, actual);
    }
}
