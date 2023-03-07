package com.rolliedev.algo;

import com.rolliedev.model.Graph;
import com.rolliedev.model.Graph.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static com.rolliedev.model.Graph.Edge;

public final class Dijkstra extends ShortestPathAlgo {

    private Dijkstra() {
    }

    /**
     * @param originalGraph the given graph
     * @param srcVIdx       index of source vertex - starting vertex
     * @param destVIdx      index of destination vertex - finishing vertex
     * @return              list of edges, which we need to traverse to reach destination vertex from source vertex
     */
    public static List<Edge> run(Graph originalGraph, int srcVIdx, int destVIdx) {
        Graph graph = (Graph) originalGraph.clone(); // we need to make clone, because we want to save our graph in original state
        processGraph(graph, srcVIdx);

        PriorityQueue<Vertex> queue = new PriorityQueue<>(List.of(graph.getVertexByIdx(srcVIdx)));
        List<Vertex> visited = new ArrayList<>();

        while (!queue.isEmpty()) {
            Vertex vertex = queue.remove();
            var neighbours = graph.getAllNeighbours(vertex);
            neighbours.removeAll(visited);
            setMinDistances(graph, vertex, neighbours);
            queue.addAll(neighbours);
            visited.add(vertex);
        }
        return getPathFromSrcToDestVertex(graph, destVIdx);
//        return graph.getVertices().stream().map(Vertex::getMinDist).toList();
    }

    /**
     * This method sets minimal distances for all neighbours of vertex.
     * If current minimal distance of neighbour d(neighbourV) is bigger than sum of minimal distance of source vertex d(srcV) and
     * weight of edge between these vertices w(srcV, neighbourV)
     *
     * @param graph      given graph
     * @param srcV       source vertex
     * @param neighbours list of vertices - neighbours of source vertex
     */
    private static void setMinDistances(Graph graph, Vertex srcV, List<Vertex> neighbours) {
        neighbours.forEach(neighbour -> {
            int newMinDist = srcV.getMinDist() + graph.getEdge(srcV, neighbour).getWeight();
            if (newMinDist < neighbour.getMinDist()) {
                neighbour.setMinDist(newMinDist);
                neighbour.setPrev(srcV);
            }
        });
    }
}
