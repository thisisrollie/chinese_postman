package com.rolliedev.model;

import com.rolliedev.exceptions.EdgeDoesNotExistException;
import com.rolliedev.exceptions.GraphCreationException;
import com.rolliedev.util.GraphUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents <b>undirected graph</b>.
 */
public class UndirectedGraph extends Graph {

    private UndirectedGraph(int vertices, List<Edge> edges) {
        super(vertices, edges);
    }

    private UndirectedGraph(List<Vertex> vertices, List<Edge> edges) {
        super(vertices, edges);
    }

    public static UndirectedGraph getGraphFromAdjMatrix(int[][] matrix) {
        GraphUtils.checkAdjMatrix(matrix);

        int V = matrix.length;
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < V - 1; i++) {
            for (int j = i + 1; j < V; j++) {
                if (matrix[i][j] != 0) {
                    edges.add(new UndirectedEdge(i, j, matrix[i][j]));
                    UndirectedEdge.increment--;
                    edges.add(new UndirectedEdge(j, i, matrix[i][j]));
                }
            }
        }
        return new UndirectedGraph(V, edges);
    }

    @Override
    public Object clone() {
        return new UndirectedGraph(getCloneVertices(), getCloneEdges());
    }

    @Override
    public void addEdge(int VIdx, int UIdx, int weight) {
        edges.add(new UndirectedEdge(VIdx, UIdx, weight));
        UndirectedEdge.increment--;
        edges.add(new UndirectedEdge(UIdx, VIdx, weight));
    }

    @Override
    public void removeEdge(int VIdx, int UIdx) {
        edges.removeIf(edge -> edge.equals(new UndirectedEdge(VIdx, UIdx, 0)));
    }

    @Override
    public Edge getEdge(Vertex srcV, Vertex destV) {
        for (Edge edge : edges) {
            if (edge.srcVIdx == srcV.getIdx() && edge.destVIdx == destV.getIdx()) {
                return edge;
            }
        }
        throw new EdgeDoesNotExistException(
                String.format("Between %d and %d does not exist an edge", srcV.getIdx(), destV.getIdx()));
    }

    @Override
    public int getSumOfAllEdges() {
        return edges.stream()
                .distinct()
                .mapToInt(Edge::getWeight)
                .sum();
    }

    private List<Edge> getCloneEdges() {
        return edges.stream()
                .map(edge -> (UndirectedEdge) edge.clone())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
