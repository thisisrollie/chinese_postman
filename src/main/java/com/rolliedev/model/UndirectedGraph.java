package com.rolliedev.model;

import com.rolliedev.exceptions.EdgeDoesNotExistException;

import java.util.ArrayList;
import java.util.List;

public class UndirectedGraph extends Graph {

    private UndirectedGraph(int vertices, List<Edge> edges) {
        super(vertices, edges);
    }

    private UndirectedGraph(List<Vertex> vertices, List<Edge> edges) {
        super(vertices, edges);
    }

    public static UndirectedGraph getGraphFromAdjMatrix(int[][] matrix) {
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
        // for deep clone we need to clone all edges too
        return new UndirectedGraph(getCloneVertices(), new ArrayList<>(edges));
    }

    @Override
    public void addEdge(int VIdx, int UIdx, int weight) {
        edges.add(new UndirectedEdge(VIdx, UIdx, weight)); // 1
        UndirectedEdge.increment--;
        edges.add(new UndirectedEdge(UIdx, VIdx, weight));
    }

    @Override
    public void removeEdge(int VIdx, int UIdx) {
        edges.removeIf(edge -> edge.equals(new UndirectedEdge(VIdx, UIdx, 0)));
    }

    @Override
    public Edge getEdge(Vertex srcV, Vertex destV) {
        UndirectedEdge anotherEdge = new UndirectedEdge(srcV.getIdx(), destV.getIdx(), 0);
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
                .distinct()
                .mapToInt(Edge::getWeight)
                .sum();
    }
}
