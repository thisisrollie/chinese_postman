package com.rolliedev;

import com.rolliedev.algo.Johnson;
import com.rolliedev.model.DirectedGraph;

public class JohnsonRunner {

    public static void main(String[] args) {
        DirectedGraph graph = DirectedGraph.getGraphFromAdjMatrix(new int[][]{
                {0, 4, 2, 0},
                {0, 0, 0, -2},
                {0, 1, 0, 3},
                {0, 0, 0, 0}
        });
        Johnson.run(graph);
    }
}
