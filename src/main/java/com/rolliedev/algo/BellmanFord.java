package com.rolliedev.algo;

import com.rolliedev.model.Graph;

import static com.rolliedev.model.Graph.Edge;
import static com.rolliedev.model.Graph.Vertex;
import static com.rolliedev.util.GraphConst.INFINITY;
import static com.rolliedev.util.GraphConst.ZERO;

public final class BellmanFord {

    private BellmanFord() {
    }

    public static void run(Graph originalGraph, int startVertex) {
        Graph graph = (Graph) originalGraph.clone();
        processGraph(graph, startVertex);

        for (int i = 0; i < graph.countOfVertices() - 1; i++) {
            for (Edge edge : graph.getEdges()) {
                if (graph.getVertexByIdx(edge.getSrcVIdx()).getMinDist() != INFINITY) {
                    update(graph, edge);
                }
            }
        }
        // checks if there exist negative cycles in graph G
        for (Edge edge : graph.getEdges()) {
            if (graph.getVertexByIdx(edge.getSrcVIdx()).getMinDist() != INFINITY
                    && graph.getVertexByIdx(edge.getDestVIdx()).getMinDist() > graph.getVertexByIdx(edge.getSrcVIdx()).getMinDist() + edge.getWeight()) {
                System.out.println("Negative cycle exists in the graph, no solution found...");
                return;
            }
        }
//        graph.displayMinDistances();
//        graph.displayPaths();
    }

    private static void update(Graph graph, Edge edge) {
        Vertex srcVertex = graph.getVertexByIdx(edge.getSrcVIdx());
        Vertex destVertex = graph.getVertexByIdx(edge.getDestVIdx());
        int minDist = Math.min(destVertex.getMinDist(), srcVertex.getMinDist() + edge.getWeight());
        if (destVertex.getMinDist() != minDist) {
            destVertex.setMinDist(minDist);
            destVertex.setPrev(srcVertex);
        }
    }

    private static void processGraph(Graph graph, int startVertex) {
        graph.getVertexByIdx(startVertex).setMinDist(ZERO);
    }
}
