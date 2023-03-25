package com.rolliedev.model;

import com.rolliedev.exceptions.EdgeDoesNotExistException;
import com.rolliedev.util.GraphUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.rolliedev.util.GraphConst.INFINITY;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;

/**
 * This class represents undirected graph
 */
public class Graph {
    private final List<Vertex> vertices;
    private final List<Edge> edges;

    private Graph(int vertices, List<Edge> edges) {
        this(GraphUtils.generateListOfVertices(vertices), edges);
    }

    private Graph(List<Vertex> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public static Graph getGraphFromAdjMatrix(int[][] matrix) {
        int V = matrix.length;
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < V - 1; i++) {
            for (int j = i + 1; j < V; j++) {
                if (matrix[i][j] != 0) {
                    edges.add(new Edge(i, j, matrix[i][j]));
                    edges.add(new Edge(j, i, matrix[i][j]));
                }
            }
        }
        return new Graph(V, edges);
    }

    public static Graph getDirectedGraphFromAdjMatrix(int[][] matrix) {
        int V = matrix.length;
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (matrix[i][j] != 0) {
                    edges.add(new Edge(i, j, matrix[i][j]));
                }
            }
        }
        return new Graph(V, edges);
    }

    public List<Vertex> getOddDegreeVertices() {
        return vertices.stream()
                .filter(not(vertex -> getAllNeighbours(vertex).size() % 2 == 0))
                .collect(toList());
    }

    public List<Vertex> getAllNeighbours(Vertex vertex) {
        return edges.stream()
                .filter(edge -> edge.srcVIdx == vertex.idx)
                .flatMap(edge -> {
                    List<Vertex> vertexList = new ArrayList<>();
                    for (int i = 0; i < edge.getFrequency(); i++) {
                        vertexList.add(vertices.get(edge.destVIdx));
                    }
                    return vertexList.stream();
                })
                .collect(toList());
    }

    public Vertex getVertexByIdx(int idx) {
        return vertices.get(idx);
    }

    /**
     * Invoke this method only if it is an undirected graph
     */
//    public void addEdge(int VIdx, int UIdx, int weight) {
//        edges.add(new Edge(VIdx, UIdx, weight));
//        edges.add(new Edge(UIdx, VIdx, weight));
//    }

    public void addEdge(int VIdx, int UIdx, int weight) {
        edges.add(new Edge(VIdx, UIdx, weight));
    }

    public void removeEdge(int VIdx, int UIdx) {
        edges.removeIf(edge -> edge.equals(new Edge(VIdx, UIdx, 0)));
    }

    public Vertex addVertex() {
        var vertex = new Vertex(vertices.size());
        vertices.add(vertex);
        return vertex;
    }

    public void removeVertex(int idxOfVertex) {
        vertices.remove(idxOfVertex);
    }

    /**
     * We invoke this method only if edge truly exists between these vertexes
     *
     * @param srcV  source vertex
     * @param destV destination vertex
     * @return an edge between these vertexes
     */
    public Edge getEdge(Vertex srcV, Vertex destV) {
        Edge anotherEdge = new Edge(srcV.idx, destV.getIdx(), 0);
        for (Edge edge : edges) {
            if (edge.equals(anotherEdge)) {
                return edge;
            }
        }
        throw new EdgeDoesNotExistException(
                String.format("Between %d and %d does not exist an edge", srcV.idx, destV.idx));
    }

    public int getSumOfAllEdges() {
        return edges.stream()
                .distinct()
                .mapToInt(Edge::getWeight)
                .sum();
    }

    public int countOfVertices() {
        return vertices.size();
    }

    @Override
    public Object clone() {
        var cloneVertices = vertices.stream()
                .map(vertex -> (Vertex) vertex.clone())
                .toList();
        return new Graph(cloneVertices, new ArrayList<>(edges));
    }

    public static class Vertex implements Comparable<Vertex> {
        // index of vertex
        private final int idx;
        // current distance from the source vertex to this vertex
        private int minDist;
        // previous vertex in the shortest path
        private Vertex prev;

        public Vertex(int idx) {
            this(idx, INFINITY);
        }

        private Vertex(int idx, int minDist) {
            this.idx = idx;
            this.minDist = minDist;
        }

        @Override
        protected Object clone() {
            return new Vertex(this.idx);
        }

        @Override
        public int compareTo(Vertex o) {
            return Integer.compare(this.minDist, o.minDist);
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "idx=" + idx +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex vertex = (Vertex) o;
            return idx == vertex.idx;
        }

        @Override
        public int hashCode() {
            return Objects.hash(idx);
        }

        public int getIdx() {
            return idx;
        }

        public int getMinDist() {
            return minDist;
        }

        public void setMinDist(int minDist) {
            this.minDist = minDist;
        }

        public Vertex getPrev() {
            return prev;
        }

        public void setPrev(Vertex prev) {
            this.prev = prev;
        }
    }

    public static class Edge {
        // it's a source vertex
        private int srcVIdx;
        // it's a destination vertex
        private int destVIdx;
        // it denotes the weight of edge
        private int weight;
        // by default, it sets to 1
        private int frequency;

        public Edge(int srcVIdx, int destVIdx, int weight) {
            this.srcVIdx = srcVIdx;
            this.destVIdx = destVIdx;
            this.weight = weight;
            this.frequency = 1;
        }

        public void increaseFrequency() {
            frequency = frequency + 1;
        }

        public void reduceFrequency() {
            frequency = frequency - 1;
        }

        @Override
        public String toString() {
            return String.format("(%d)--%d--(%d)", srcVIdx, weight, destVIdx);

//            return """
//                    (%d)--%d--(%d)""".formatted(srcVIdx, weight, destVIdx);

//            return "Edge{" +
//                    "srcVIdx=" + srcVIdx +
//                    ", destVIdx=" + destVIdx +
//                    ", weight=" + weight +
//                    '}';
        }

        /**
         * Equals method implementation for undirected edge.
         * For example, edge A-B equals to B-A, it is the same edge.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return ((srcVIdx == edge.srcVIdx && destVIdx == edge.destVIdx) ||
                    (srcVIdx == edge.destVIdx && destVIdx == edge.srcVIdx));
        }

        @Override
        public int hashCode() {
            return Objects.hash(srcVIdx + destVIdx, weight);
        }

        public int getSrcVIdx() {
            return srcVIdx;
        }

        public void setSrcVIdx(int srcVIdx) {
            this.srcVIdx = srcVIdx;
        }

        public int getDestVIdx() {
            return destVIdx;
        }

        public void setDestVIdx(int destVIdx) {
            this.destVIdx = destVIdx;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
