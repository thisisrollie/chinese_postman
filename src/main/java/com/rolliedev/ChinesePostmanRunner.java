package com.rolliedev;

import com.rolliedev.algo.Dijkstra;
import com.rolliedev.model.Graph;
import com.rolliedev.model.Graph.Edge;
import com.rolliedev.util.GeneratorUtils;
import com.rolliedev.util.GraphUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ChinesePostmanRunner {

    public static void main(String[] args) {
        Graph graph = Graph.getGraphFromAdjMatrix(new int[][]{
                {},
                {}
        });
        run(graph);
    }

    public static void run(Graph graph) {
        var allPairs = getAllPairsOfOddDegreeVertices(graph);
        System.out.println(allPairs);
        var edgesWithMinWeight = getEdgesWithMinWeight(graph, allPairs);
        System.out.println("Sum of all edges: " + graph.getSumOfAllEdges());
        int lengthOfRoute = graph.getSumOfAllEdges() + edgesWithMinWeight.stream().mapToInt(Edge::getWeight).sum();
        System.out.println(edgesWithMinWeight);
        setupEdges(graph, edgesWithMinWeight);
        GraphUtils.getEulerCycle(graph).forEach(value -> System.out.print(value + " "));
        System.out.println("\nThe length of Chinese postman route is " + lengthOfRoute);
    }

    private static void setupEdges(Graph graph, List<Edge> edges) {
        for (Edge edge : edges) {
            for (Edge graphEdge : graph.getEdges()) {
                if (edge.equals(graphEdge)) {
                    graphEdge.addRepeat();
                }
            }
        }
    }

    private static List<Edge> getEdgesWithMinWeight(Graph graph, List<List<List<Integer>>> allPairs) {
        List<List<Edge>> edgePairs = new ArrayList<>();
        for (List<List<Integer>> pairs : allPairs) {
            List<Edge> tmpList = new ArrayList<>();
            for (List<Integer> pair : pairs) {
                var edgesBetweenPair = Dijkstra.run(graph, pair.get(0), pair.get(1));
                tmpList.addAll(edgesBetweenPair);
            }
            edgePairs.add(tmpList);
        }
        return edgePairs.stream()
                .min(Comparator.comparing(edges -> edges.stream().mapToInt(Edge::getWeight).sum()))
                .orElseThrow();
    }

    private static List<List<List<Integer>>> getAllPairsOfOddDegreeVertices(Graph graph) {
        var oddVIndexes = graph.getOddDegreeVertices().stream()
                .map(Graph.Vertex::getIdx)
                .toList();
        return GeneratorUtils.getPairs(oddVIndexes);
    }
}
