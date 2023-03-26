package com.rolliedev;

import com.rolliedev.algo.ShortestSinglePathAlgo;
import com.rolliedev.model.Edge;
import com.rolliedev.model.Graph;
import com.rolliedev.model.UndirectedGraph;
import com.rolliedev.model.Vertex;
import com.rolliedev.util.GeneratorUtils;
import com.rolliedev.util.GraphUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class ChinesePostman<G extends UndirectedGraph> {

    public ChinesePostman() {
    }

    public void run(Class<? extends ShortestSinglePathAlgo> algoClass, G graph, int startVIdx) {
        var allPairs = getAllPairsOfOddDegreeVertices(graph);
        System.out.println(allPairs);
        var edgesWithMinWeight = getEdgesWithMinWeight(graph, algoClass, allPairs);
        System.out.println("Sum of all edges: " + graph.getSumOfAllEdges());
        int lengthOfRoute = graph.getSumOfAllEdges() + edgesWithMinWeight.stream().mapToInt(Edge::getWeight).sum();
        System.out.println(edgesWithMinWeight);
        setupEdges(graph, edgesWithMinWeight);
        GraphUtils.getEulerCycle(graph, startVIdx).forEach(value -> System.out.print(value + " "));
        System.out.println("\nThe length of Chinese postman route is " + lengthOfRoute);
    }

    private void setupEdges(G graph, List<Edge> edges) {
        for (Edge edge : edges) {
            for (Edge graphEdge : graph.getEdges()) {
                if (edge.equals(graphEdge)) {
                    graphEdge.increaseFrequency();
                }
            }
        }
    }

    private List<Edge> getEdgesWithMinWeight(G graph, Class<? extends ShortestSinglePathAlgo> algoClass, List<List<List<Integer>>> allPairs) {
        List<List<Edge>> edgePairs = new ArrayList<>();
        try {
            ShortestSinglePathAlgo algoInstance = algoClass.getConstructor().newInstance();
            for (List<List<Integer>> pairs : allPairs) {
                List<Edge> tmpList = new ArrayList<>();
                for (List<Integer> pair : pairs) {

                    var runMethod = algoClass.getMethod("run", graph.getClass().getSuperclass(), int.class);
                    runMethod.invoke(algoInstance, graph, pair.get(0));
                    var getPathMethod = algoClass.getMethod("getPathFromSrcToDestVertex", int.class);
                    var edgesBetweenPair = (List<Edge>) getPathMethod.invoke(algoInstance, pair.get(1));
                    tmpList.addAll(edgesBetweenPair);
                }
                edgePairs.add(tmpList);
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        return edgePairs.stream()
                .min(Comparator.comparing(edges -> edges.stream().mapToInt(Edge::getWeight).sum()))
                .orElseThrow();
    }

    private List<List<List<Integer>>> getAllPairsOfOddDegreeVertices(Graph graph) {
        var oddVIndexes = graph.getOddDegreeVertices().stream()
                .map(Vertex::getIdx)
                .toList();
        return GeneratorUtils.getPairs(oddVIndexes);
    }
}
