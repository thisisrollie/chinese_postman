package com.rolliedev.model;

import com.rolliedev.util.GraphUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;

public abstract class Graph {

    protected final List<Vertex> vertices;
    protected final List<Edge> edges;

    protected Graph(int vertices, List<Edge> edges) {
        this(GraphUtils.generateListOfVertices(vertices), edges);
    }

    protected Graph(List<Vertex> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void addEdge(int VIdx, int UIdx, int weight);

    public abstract void removeEdge(int VIdx, int UIdx);

    public abstract Edge getEdge(Vertex srcV, Vertex destV);

    public abstract int getSumOfAllEdges();

    protected List<Vertex> getCloneVertices() {
        return vertices.stream()
                .map(vertex -> (Vertex) vertex.clone())
                .collect(Collectors.toCollection(ArrayList::new)); // i want to return a mutable list
    }

    public List<Vertex> getAllNeighbours(Vertex vertex) {
        return edges.stream()
                .filter(edge -> edge.getSrcVIdx() == vertex.getIdx())
                .flatMap(edge -> {
                    List<Vertex> vertexList = new ArrayList<>();
                    for (int i = 0; i < edge.getFrequency(); i++) {
                        vertexList.add(vertices.get(edge.getDestVIdx()));
                    }
                    return vertexList.stream();
                })
                .collect(toList());
    }

    public List<Vertex> getOddDegreeVertices() {
        return vertices.stream()
                .filter(not(vertex -> getAllNeighbours(vertex).size() % 2 == 0))
                .collect(toList());
    }

    public Vertex addVertex() {
        var vertex = new Vertex(vertices.size());
        vertices.add(vertex);
        return vertex;
    }

    public void removeVertex(int idxOfVertex) {
        vertices.remove(idxOfVertex);
    }

    public Vertex getVertexByIdx(int idx) {
        return vertices.get(idx);
    }

    public int countOfVertices() {
        return vertices.size();
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
