package com.rolliedev.algo;

import com.rolliedev.model.Edge;
import com.rolliedev.model.Graph;
import com.rolliedev.model.Vertex;
import com.rolliedev.util.GraphConst;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SingleSourceShortestPathAlgo {

    protected Graph graph;

    public SingleSourceShortestPathAlgo(Graph graph) {
        this.graph = graph;
    }

    public abstract boolean run(int startVIdx);

    public void displayPaths() {
        graph.getVertices()
                .forEach(vertex -> {
                    System.out.println(getPathFromSrcToDestVertex(vertex.getIdx()));
                    System.out.println();
                });
    }

    public List<Edge> getPathFromSrcToDestVertex(int destVIdx) {
        List<Edge> path = new ArrayList<>();
        Vertex vertex = graph.getVertexByIdx(destVIdx), prevVertex;
        while ((prevVertex = vertex.getPrev()) != null) {
            path.add(graph.getEdge(prevVertex, vertex));
            vertex = prevVertex;
        }
        return path;
    }

    public List<Integer> getMinDistances() {
        return graph.getVertices().stream()
                .map(Vertex::getMinDist)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * This method sets graph to initial state for future running of algorithm.
     * Sets minimal distances to infinity for all vertices except the starting vertex, which minimal distance will be set to zero
     *
     * @param startVertex index of start vertex
     */
    protected void processGraph(int startVertex) {
        graph.getVertices()
                .forEach(vertex -> {
                    vertex.setPrev(null);
                    vertex.setMinDist(GraphConst.INFINITY);
                });
        graph.getVertexByIdx(startVertex).setMinDist(GraphConst.ZERO);
    }
}
