package com.rolliedev.model;

import com.rolliedev.exceptions.EdgeDoesNotExistException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DirectedGraph extends Graph {

    private DirectedGraph(int vertices, List<Edge> edges) {
        super(vertices, edges);
    }

    private DirectedGraph(List<Vertex> vertices, List<Edge> edges) {
        super(vertices, edges);
    }

    public static DirectedGraph getGraphFromAdjMatrix(int[][] matrix) {
        int V = matrix.length;
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (matrix[i][j] != 0) {
                    edges.add(new DirectedEdge(i, j, matrix[i][j]));
                }
            }
        }
        return new DirectedGraph(V, edges);
    }

    @Override
    public Object clone() {
        return new DirectedGraph(getCloneVertices(), getCloneEdges());
    }

    @Override
    public void addEdge(int VIdx, int UIdx, int weight) {
        edges.add(new DirectedEdge(VIdx, UIdx, weight));
    }

    @Override
    public void removeEdge(int VIdx, int UIdx) {
        edges.removeIf(edge -> edge.equals(new DirectedEdge(VIdx, UIdx, 0)));
    }

    @Override
    public Edge getEdge(Vertex srcV, Vertex destV) {
        DirectedEdge anotherEdge = new DirectedEdge(srcV.getIdx(), destV.getIdx(), 0);
        for (Edge edge : edges) {
            if (edge.equals(anotherEdge)) {
                return edge;
            }
        }
        throw new EdgeDoesNotExistException(
                String.format("Between %d and %d does not exist an edge", srcV.getIdx(), destV.getIdx()));
    }

    @Override
    public int getSumOfAllEdges() {
        return edges.stream()
                .mapToInt(Edge::getWeight)
                .sum();
    }

    private List<Edge> getCloneEdges() {
        return edges.stream()
                .map(edge -> (DirectedEdge) edge.clone())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
