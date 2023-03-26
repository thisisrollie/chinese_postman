package com.rolliedev.util;

import com.rolliedev.model.*;
import com.rolliedev.model.UndirectedEdge;
import com.rolliedev.model.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
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
     * Fleur's Algorithm was used in this function.
     * This method requires for input an undirected graph
     */
    public static List<Integer> getEulerCycle(UndirectedGraph graph, int startVIdx) {
        if (!isEuler(graph)) {
            System.out.println("Graph does not have any Euler cycles");
            return Collections.emptyList();
        }
        List<Integer> eulerCycle = new ArrayList<>();
        var stack = new Stack<Vertex>();
        stack.push(graph.getVertexByIdx(startVIdx));
        while (!stack.isEmpty()) {
            var u = stack.peek();
            if (graph.getAllNeighbours(u).size() == 0) {
//                System.out.print(stack.pop().getIdx() + " ");
                eulerCycle.add(stack.pop().getIdx());
            } else {
                var v = graph.getAllNeighbours(u).get(0);
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
                stack.push(v);
            }
        }
        return eulerCycle;
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
