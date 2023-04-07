package com.rolliedev.algo;

import com.rolliedev.model.Graph;
import com.rolliedev.model.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public final class Dijkstra extends SingleSourceShortestPathAlgo {

    public Dijkstra() {
    }

    /**
     * This method runs Dijkstra algorithm
     *
     * @param originalGraph the given graph
     * @param srcVIdx       index of source vertex - starting vertex
     */
    public void run(Graph originalGraph, int srcVIdx) {
        graph = (Graph) originalGraph.clone(); // we need to make clone, because we want to save our graph in original state
        processGraph(graph, srcVIdx);

        PriorityQueue<Vertex> queue = new PriorityQueue<>(List.of(graph.getVertexByIdx(srcVIdx)));
        List<Vertex> visited = new ArrayList<>();
        while (!queue.isEmpty()) {
            Vertex vertex = queue.remove();
            if (visited.contains(vertex)) continue;
            var neighbours = graph.getAllNeighbours(vertex);
            neighbours.removeAll(visited);
            setMinDistances(graph, vertex, neighbours);
            queue.addAll(neighbours);
            visited.add(vertex);
        }
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
    private void setMinDistances(Graph graph, Vertex srcV, List<Vertex> neighbours) {
        neighbours.forEach(neighbour -> {
            int newMinDist = srcV.getMinDist() + graph.getEdge(srcV, neighbour).getWeight();
            if (newMinDist < neighbour.getMinDist()) {
                neighbour.setMinDist(newMinDist);
                neighbour.setPrev(srcV);
            }
        });
    }
}
