package com.rolliedev.algo;

import com.rolliedev.model.Edge;
import com.rolliedev.model.Graph;
import com.rolliedev.model.Vertex;
import com.rolliedev.util.GraphConst;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ShortestSinglePathAlgo {

    protected Graph graph;

    public ShortestSinglePathAlgo() {
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
     * This method sets minimal distance to zero for given vertex
     *
     * @param graph       given graph
     * @param startVertex index of start vertex
     */
    protected void processGraph(Graph graph, int startVertex) {
        graph.getVertexByIdx(startVertex).setMinDist(GraphConst.ZERO);
    }
}
