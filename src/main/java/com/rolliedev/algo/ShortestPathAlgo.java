package com.rolliedev.algo;

import com.rolliedev.model.Graph;
import com.rolliedev.util.GraphConst;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.rolliedev.model.Graph.*;

public abstract class ShortestPathAlgo {

    protected static Graph graph;

    public ShortestPathAlgo() {
    }

    public static List<Edge> getPathFromSrcToDestVertex(int destVIdx) {
        List<Edge> path = new ArrayList<>();
        Vertex vertex = graph.getVertexByIdx(destVIdx), prevVertex;
        while ((prevVertex = vertex.getPrev()) != null) {
            path.add(graph.getEdge(prevVertex, vertex));
            vertex = prevVertex;
        }
        return path;
    }

    public static List<Integer> getMinDistances() {
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
    protected static void processGraph(Graph graph, int startVertex) {
        graph.getVertexByIdx(startVertex).setMinDist(GraphConst.ZERO);
    }
}
