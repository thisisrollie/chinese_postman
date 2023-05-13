package com.rolliedev.algo;

import com.rolliedev.model.DirectedGraph;
import com.rolliedev.model.Vertex;

import java.util.Collections;
import java.util.List;

import static com.rolliedev.util.GraphConst.INFINITY;
import static com.rolliedev.util.GraphConst.ZERO;

/**
 * This algorithm we will use only for directed graphs
 */
public final class Johnson {

    private Johnson() {
    }

    public static int[][] run(DirectedGraph graph) {
        var q = addNewNode(graph);
        var hOfVertices = runBellman(graph, q);

        if (hOfVertices.isEmpty()) { // negative cycle was found
            System.out.println("Johnson's algorithm is terminated");
            return new int[][]{};
        }

        removeNewNode(graph, q, hOfVertices);
        reWeightGraph(graph, hOfVertices);
        return runDijkstra(graph, hOfVertices);
    }

    private static int[][] runDijkstra(DirectedGraph graph, List<Integer> hOfVertices) {
        int[][] result = new int[graph.countOfVertices()][graph.countOfVertices()];
        Dijkstra dijkstra = new Dijkstra(graph);
        graph.getVertices()
                .forEach(srcVertex -> {
                    // run dijkstra on each vertex
                    dijkstra.run(srcVertex.getIdx());
                    // display paths from source vertex to other
                    dijkstra.displayPaths();
                    // return back the initial distances
                    List<Integer> minDistances = dijkstra.getMinDistances();
                    for (int i = 0; i < minDistances.size(); i++) {
                        // D(u, v) = distance'(u, v) + h(v) - h(u)
                        result[srcVertex.getIdx()][i] = (minDistances.get(i) == INFINITY) ? INFINITY
                                : minDistances.get(i) + hOfVertices.get(i) - hOfVertices.get(srcVertex.getIdx());
                    }
                });
        return result;
    }

    /**
     * @param graph given graph
     * @param q     new added vertex
     * @return list of minimal distances from source vertex to another. If negative cycle is detected, then an empty list is returned
     */
    private static List<Integer> runBellman(DirectedGraph graph, Vertex q) {
        BellmanFord bellmanFord = new BellmanFord(graph);
        return bellmanFord.run(q.getIdx()) ? bellmanFord.getMinDistances() : Collections.emptyList();
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
