package com.rolliedev;

import com.rolliedev.model.Graph;
import org.junit.Test;

public class ChinesePostmanTest {

    @Test
    public void test1() {
        /**
         * Worked example 3.2
         * source: http://www.suffolkmaths.co.uk/pages/Maths%20Projects/Projects/Topology%20and%20Graph%20Theory/Chinese%20Postman%20Problem.pdf
         */
        Graph graph = Graph.getGraphFromAdjMatrix(new int[][]{
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
        ChinesePostmanRunner.run(graph);
    }

    @Test
    public void test2() {
        /**
         * Worked example 3.3
         * source: http://www.suffolkmaths.co.uk/pages/Maths%20Projects/Projects/Topology%20and%20Graph%20Theory/Chinese%20Postman%20Problem.pdf
         */
        Graph graph = Graph.getGraphFromAdjMatrix(new int[][]{
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
        ChinesePostmanRunner.run(graph);
    }
}
