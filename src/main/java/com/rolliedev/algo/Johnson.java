package com.rolliedev.algo;

import com.rolliedev.model.DirectedGraph;
import com.rolliedev.model.Vertex;

import java.util.Collections;
import java.util.List;

import static com.rolliedev.util.GraphConst.ZERO;

/**
 * This algorithm we will use only for directed graphs
 */
public final class Johnson {

    private Johnson() {
    }

    public static void run(DirectedGraph graph) {
        var q = addNewNode(graph);
        var hOfVertices = runBellman(graph, q);
        if (hOfVertices.isEmpty()) { // negative cycle was found
            System.out.println("Jonson's algorithm is terminated");
            return;
        }
        removeNewNode(graph, q, hOfVertices);
        reWeightGraph(graph, hOfVertices);
        // TODO: 3/26/23 reweight graph to initial state ?
        runDijkstra(graph);
    }

    private static void runDijkstra(DirectedGraph graph) {
        Dijkstra dijkstra = new Dijkstra();
        graph.getVertices()
                .forEach(vertex -> {
                    dijkstra.run(graph, vertex.getIdx());
                    for (Vertex graphVertex : graph.getVertices()) {
                        if (graphVertex.equals(vertex)) continue;
                        System.out.println(dijkstra.getPathFromSrcToDestVertex(graphVertex.getIdx()));
                    }
//                    System.out.println(dijkstra.getMinDistances());
                    System.out.println();
                });
    }

    /**
     * @param graph given graph
     * @param q     new added vertex
     * @return list of minimal distances from source vertex to another. If negative cycle is detected, then an empty list is returned
     */
    private static List<Integer> runBellman(DirectedGraph graph, Vertex q) {
        BellmanFord bellmanFord = new BellmanFord();
        return bellmanFord.run(graph, q.getIdx()) ? bellmanFord.getMinDistances() : Collections.emptyList();
    }

    private static void reWeightGraph(DirectedGraph graph, List<Integer> hOfVertices) {
        graph.getEdges()
                // w'(u, v) = w(u, v) + h(u) - h(v)
                .forEach(edge -> edge.setWeight(edge.getWeight() +
                        hOfVertices.get(edge.getSrcVIdx()) - hOfVertices.get(edge.getDestVIdx())));
    }

    private static Vertex addNewNode(DirectedGraph graph) {
        var newNode = graph.addVertex();
        graph.getVertices().stream()
                .limit(graph.countOfVertices() - 1)
                .forEach(vertex -> graph.addEdge(newNode.getIdx(), vertex.getIdx(), ZERO));
        return newNode;
    }

    private static void removeNewNode(DirectedGraph graph, Vertex q, List<Integer> hOfVertices) {
        hOfVertices.remove(graph.countOfVertices() - 1);
        graph.removeVertex(q.getIdx());
        graph.getVertices().forEach(vertex -> graph.removeEdge(q.getIdx(), vertex.getIdx()));
    }
}
