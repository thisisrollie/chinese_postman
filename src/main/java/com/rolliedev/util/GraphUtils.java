package com.rolliedev.util;

import com.rolliedev.model.Graph;
import com.rolliedev.model.Graph.Vertex;

import java.util.*;
import java.util.stream.IntStream;

import static com.rolliedev.model.Graph.*;
import static java.util.function.Predicate.not;

public final class GraphUtils {

    private GraphUtils() {
    }

    public static List<Vertex> generateListOfVertices(int cntOfNodes) {
        return IntStream.range(0, cntOfNodes)
                .mapToObj(Vertex::new)
                .toList();
    }

    public static boolean isEuler(Graph graph) {
        return graph.getVertices().stream()
                .map(vertex -> graph.getAllNeighbours(vertex).size())
                .noneMatch(not(count -> count % 2 == 0));
    }

    /**
     * Fleur's Algorithm was used in this function
     */
    public static List<Integer> getEulerCycle(Graph graph, int startVIdx) {
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
                    if (new Edge(u.getIdx(), v.getIdx(), 0).equals(edge)) {
                        if (edge.getRepeat() == 1) {
                            return true;
                        } else {
                            edge.subRepeat();
                        }
                    }
                    return false;
                });
                stack.push(v);
            }
        }
        return eulerCycle;
    }

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
