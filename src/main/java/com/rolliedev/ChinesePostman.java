package com.rolliedev;

import com.rolliedev.algo.SingleSourceShortestPathAlgo;
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

    public List<Integer> run(SingleSourceShortestPathAlgo pathAlgo, G graph, int startVIdx) {
        var allCombinationsOfPairingOddDegreeVertices = getAllCombinationsOfPairingOddDegreeVertices(graph);
        System.out.println(allCombinationsOfPairingOddDegreeVertices);

        int lengthOfRoute = graph.getSumOfAllEdges();
        if (!allCombinationsOfPairingOddDegreeVertices.isEmpty()) {
            var edgesWithMinWeight = getEdgesWithMinWeight(graph, pathAlgo, allCombinationsOfPairingOddDegreeVertices);
            System.out.println(graph.getSumOfAllEdges());
            lengthOfRoute += edgesWithMinWeight.stream().mapToInt(Edge::getWeight).sum();
            increaseFrequencyOfGraphEdges(graph, edgesWithMinWeight);
            System.out.println(edgesWithMinWeight);
        }

        System.out.println("Chinese postman route: ");
        List<Integer> eulerCircuit = GraphUtils.getEulerCircuit((UndirectedGraph) graph.clone(), startVIdx);
        eulerCircuit.forEach(vIdx -> System.out.print(vIdx + " "));
        System.out.printf("\nThe length of Chinese postman route is %d.\n\n", lengthOfRoute);
        return eulerCircuit;
    }

    private void increaseFrequencyOfGraphEdges(G graph, List<Edge> edges) {
        for (Edge edge : edges) {
            for (Edge graphEdge : graph.getEdges()) {
                if (edge.equals(graphEdge)) {
                    graphEdge.increaseFrequency();
                }
            }
        }
    }

    private List<Edge> getEdgesWithMinWeight(G graph, SingleSourceShortestPathAlgo pathAlgo, List<List<List<Integer>>> allCombinations) {
        List<List<Edge>> edgesFromCombination = new ArrayList<>();

        for (List<List<Integer>> pairsFromCombination : allCombinations) {
            List<Edge> edgeHolderList = new ArrayList<>();
            for (List<Integer> pair : pairsFromCombination) {
                pathAlgo.run(pair.get(0));
                List<Edge> edgesBetweenPair = pathAlgo.getPathFromSrcToDestVertex(pair.get(1));
                edgeHolderList.addAll(edgesBetweenPair);
            }
            edgesFromCombination.add(edgeHolderList);
        }
        return edgesFromCombination.stream()
                .min(Comparator.comparing(edges -> edges.stream().mapToInt(Edge::getWeight).sum()))
                .orElseThrow();
    }

    private List<List<List<Integer>>> getAllCombinationsOfPairingOddDegreeVertices(Graph graph) {
        var oddVIndexes = graph.getOddDegreeVertices().stream()
                .map(Vertex::getIdx)
                .toList();
        return GeneratorUtils.getCombinations(oddVIndexes);
    }
}
