package com.rolliedev.util;

import com.rolliedev.exceptions.GraphCreationException;
import com.rolliedev.model.*;

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

    /**
     * This method throws exception if the matrix is empty or if it doesn't have square shape (matrix of size V x V)
     *
     * @param matrix given adjacency matrix
     */
    public static void checkAdjMatrix(int[][] matrix) {
        int V = matrix.length;
        long matrixSize = Arrays.stream(matrix).flatMapToInt(Arrays::stream).count();
        if (V == 0 || matrixSize != (long) V * V) {
            throw new GraphCreationException("Failed creating a graph. The adjacency matrix must be a 2D array of size V x V, where V is number of vertices in a graph.\n" +
                    "Current matrix size: " + matrixSize + ".");
        }
    }

    public static int getLengthOfPostmanRoute(UndirectedGraph graph, List<Integer> postmanRoute) {
        int routeLength = 0;
        for (int i = 0; i < postmanRoute.size() - 1; i++) {
            routeLength += graph.getEdge(graph.getVertexByIdx(postmanRoute.get(i)), graph.getVertexByIdx(postmanRoute.get(i + 1))).getWeight();
        }
        return routeLength;
    }

    public static boolean isEuler(Graph graph) {
        return graph.getVertices().stream()
                .map(vertex -> graph.getAllNeighboursOfVertex(vertex).size())
                .noneMatch(not(count -> count % 2 == 0));
    }

    /**
     * Hierholzer's algorithm was used in this function to find the Euler circuit.
     * This method requires an undirected graph and the index of the starting vertex as input.
     */
    public static List<Integer> getEulerCircuit(UndirectedGraph graph, int startVIdx) {
        if (!isEuler(graph)) {
            System.out.println("Graph does not have any Euler cycles.");
            return Collections.emptyList();
        }
        List<Integer> circuit = new ArrayList<>();
        var stack = new Stack<Vertex>();
        stack.push(graph.getVertexByIdx(startVIdx));
        while (!stack.isEmpty()) {
            var currentVertex = stack.peek();
            var neighbours = graph.getAllNeighboursOfVertex(currentVertex);
            if (neighbours.isEmpty()) {
                stack.pop();
                circuit.add(currentVertex.getIdx());
            } else {
                removeEdgeBetweenVertices(graph, currentVertex, neighbours.get(0));
                stack.push(neighbours.get(0));
            }
        }
        Collections.reverse(circuit);
        return circuit;
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

    public static boolean isGraphPositiveWeighted(Graph graph) {
        return graph.getEdges().stream()
                .noneMatch(Edge::isNegativeWeighted);
    }
}
