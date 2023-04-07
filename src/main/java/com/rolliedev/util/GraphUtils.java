package com.rolliedev.util;

import com.rolliedev.model.*;
import com.rolliedev.model.UndirectedEdge;
import com.rolliedev.model.Graph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.function.Predicate.not;

public final class GraphUtils {

    private GraphUtils() {
    }

    public static List<Vertex> generateListOfVertices(int cntOfNodes) {
        return IntStream.range(0, cntOfNodes)
                .mapToObj(Vertex::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static boolean isEuler(Graph graph) {
        return graph.getVertices().stream()
                .map(vertex -> graph.getAllNeighbours(vertex).size())
                .noneMatch(not(count -> count % 2 == 0));
    }

    /**
     * Hierholzer's algorithm was used in this function.
     * This method requires for input an undirected graph and index of start vertex
     */
    public static List<Integer> getEulerCycle(UndirectedGraph graph, int startVIdx) {
        if (!isEuler(graph)) {
            System.out.println("Graph does not have any Euler cycles");
            return Collections.emptyList();
        }
        List<Integer> eulerCycle = new ArrayList<>();
        var stack = new Stack<Vertex>();
        var currentVertex = graph.getVertexByIdx(startVIdx);
        do {
            var neighbours = graph.getAllNeighbours(currentVertex);
            if (neighbours.isEmpty()) {
                eulerCycle.add(currentVertex.getIdx());
                currentVertex = stack.pop();
            } else {
                stack.push(currentVertex);
                removeEdgeBetweenVertices(graph, currentVertex, neighbours.get(0));
                currentVertex = neighbours.get(0);
            }
        } while (!stack.isEmpty());
        eulerCycle.add(currentVertex.getIdx());
        Collections.reverse(eulerCycle);
        return eulerCycle;
    }

    private static void removeEdgeBetweenVertices(UndirectedGraph graph, Vertex u, Vertex v) {
        graph.getEdges().removeIf(edge -> {
            if (new UndirectedEdge(u.getIdx(), v.getIdx(), 0).equals(edge)) {
                if (edge.getFrequency() == 1) {
                    return true;
                } else {
                    edge.reduceFrequency();
                }
            }
            return false;
        });
    }

    // TODO: 3/19/2023 do we really need this method? If yes then try to improve/rewrite it
    public static void displayPaths(Vertex vertex) {
        System.out.print("Vertex " + vertex.getIdx() + ": ");
        if (vertex.getMinDist() == GraphConst.INFINITY) {
            System.out.println("No path");
            return;
        }
        while (vertex != null) {
            System.out.print(vertex.getIdx() + " ");
            vertex = vertex.getPrev();
        }
        System.out.println();
    }
}
