package com.rolliedev;

import com.rolliedev.algo.BellmanFord;
import com.rolliedev.algo.Dijkstra;
import com.rolliedev.algo.Johnson;
import com.rolliedev.model.UndirectedGraph;

public class ChinesePostmanRunner {

    public static void main(String[] args) {
        UndirectedGraph graph = UndirectedGraph.getGraphFromAdjMatrix(new int[][]{
//               a  b  c  d  e  f
                {0, 10, 0, 8, 0, 6},
                {10, 0, 12, 0, 0, 5},
                {0, 12, 0, 0, 6, 4},
                {8, 0, 0, 0, 9, 7},
                {0, 0, 6, 9, 0, 0},
                {6, 5, 4, 7, 0, 0}
        });
        ChinesePostman<UndirectedGraph> chinesePostman = new ChinesePostman<>();
        chinesePostman.run(Dijkstra.class, graph, 0);
    }
}
