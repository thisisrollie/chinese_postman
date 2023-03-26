package com.rolliedev;

import com.rolliedev.algo.Dijkstra;
import com.rolliedev.model.UndirectedGraph;

public class ChinesePostmanRunner {

    public static void main(String[] args) {
        UndirectedGraph graph = UndirectedGraph.getGraphFromAdjMatrix(new int[][]{

        });
        ChinesePostman<UndirectedGraph> chinesePostman = new ChinesePostman<>();
        chinesePostman.run(Dijkstra.class, graph, 0);
    }
}
