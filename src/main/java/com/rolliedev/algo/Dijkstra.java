package com.rolliedev.algo;

import com.rolliedev.model.Graph;
import com.rolliedev.model.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public final class Dijkstra extends SingleSourceShortestPathAlgo {

    public Dijkstra(Graph graph) {
        super(graph);
    }

    /**
     * This method runs Dijkstra algorithm.
     *
     * @param startVIdx index of starting vertex - source vertex
     */
    public boolean run(int startVIdx) {
        processGraph(startVIdx);

        PriorityQueue<Vertex> queue = new PriorityQueue<>(List.of(graph.getVertexByIdx(startVIdx)));
        List<Vertex> visited = new ArrayList<>();
        while (!queue.isEmpty()) {
            Vertex vertex = queue.remove();
            if (visited.contains(vertex)) continue;
            var neighbours = graph.getAllNeighboursOfVertex(vertex);
            neighbours.removeAll(visited);
            setMinDistances(graph, vertex, neighbours);
            queue.addAll(neighbours);
            visited.add(vertex);
        }
        return true;
    }

    /**
     * This method sets minimal distances for all neighbours of the vertex.
     * If the current minimal distance of the neighbour d(neighbourV) is greater than the sum of the minimal distance of the source vertex d(srcV)
     * and the weight of the edge between these vertices w(srcV, neighbourV).
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
