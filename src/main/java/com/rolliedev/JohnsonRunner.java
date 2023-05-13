package com.rolliedev;

import com.rolliedev.algo.Johnson;
import com.rolliedev.model.DirectedGraph;

import static com.rolliedev.util.GraphConst.INFINITY;

public class JohnsonRunner {

    public static void main(String[] args) {
        DirectedGraph graph = DirectedGraph.getGraphFromAdjMatrix(new int[][]{
                {0, 4, 2, 0},
                {0, 0, 0, -2},
                {0, 1, 0, 3},
                {0, 0, 0, 0}
        });
        int[][] D = Johnson.run(graph);
        for (int i = 0; i < D.length; i++) {
            for (int j = 0; j < D.length; j++) {
                System.out.printf((D[i][j] == INFINITY) ? "  x " : "%3d ", D[i][j]);
            }
            System.out.println();
        }
    }
}
