package com.rolliedev.algo;

import com.rolliedev.model.Graph;
import com.rolliedev.model.Graph.Vertex;

import java.util.Collections;
import java.util.List;

import static com.rolliedev.util.GraphConst.ZERO;

/**
 * This algorithm we will use for directed graphs
 */
public final class Johnson {

    private Johnson() {
    }

    public static void run(Graph graph) {
        var q = addNewNode(graph);
        var hOfVertices = runBellman(graph, q);
        // TODO: 3/19/2023 this list will be empty when negative cycle was found?
        if (hOfVertices.isEmpty()) { // negative cycle was found
            System.out.println("Jonson's algorithm is terminated");
            return;
        }
        removeNewNode(graph, q);
        reWeightGraph(graph, hOfVertices); // TODO: 3/12/2023 figure out how it works for undirected graph
        graph.getEdges().forEach(System.out::println);
        runDijkstra(graph);
    }

    private static void runDijkstra(Graph graph) {
        graph.getVertices()
                .forEach(vertex -> {
                    Dijkstra.run(graph, vertex.getIdx());
                    for (Vertex graphVertex : graph.getVertices()) {
                        if (graphVertex.equals(vertex)) continue;
                        System.out.println(Dijkstra.getPathFromSrcToDestVertex(graphVertex.getIdx()));
                    }
//                    System.out.println(Dijkstra.getMinDistances());
                    System.out.println();
                });
    }

    /**
     * @param graph given graph
     * @param q     new added vertex
     * @return      list of minimal distances from source vertex to another. If negative cycle is detected, then an empty list is returned
     */
    private static List<Integer> runBellman(Graph graph, Vertex q) {
        return BellmanFord.run(graph, q.getIdx()) ? BellmanFord.getMinDistances() : Collections.emptyList();
    }

    private static void reWeightGraph(Graph graph, List<Integer> hOfVertices) {
        hOfVertices.remove(graph.countOfVertices() - 1);
        graph.getEdges()
                // w'(u, v) = w(u, v) + h(u) - h(v)
                .forEach(edge -> edge.setWeight(edge.getWeight() +
                        hOfVertices.get(edge.getSrcVIdx()) - hOfVertices.get(edge.getDestVIdx())));
    }

    private static Vertex addNewNode(Graph graph) {
        var newNode = graph.addVertex();
        graph.getVertices().stream()
                .limit(graph.countOfVertices() - 1)
                .forEach(vertex -> graph.addEdge(newNode.getIdx(), vertex.getIdx(), ZERO));
        return newNode;
    }

    private static void removeNewNode(Graph graph, Vertex q) {
        graph.removeVertex(q.getIdx());
        graph.getVertices().forEach(vertex -> graph.removeEdge(q.getIdx(), vertex.getIdx()));
    }
}
