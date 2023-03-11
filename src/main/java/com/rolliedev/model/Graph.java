package com.rolliedev.model;

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
                    // TODO: 2/22/2023 don't forget about this moment. Do we really need to add one edge twice?
                    edges.add(new Edge(i, j, matrix[i][j]));
                    edges.add(new Edge(j, i, matrix[i][j]));
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
                    for (int i = 0; i < edge.getRepeat(); i++) {
                        vertexList.add(vertices.get(edge.destVIdx));
                    }
                    return vertexList.stream();
                })
                .collect(toList());
    }

    public Vertex getVertexByIdx(int idx) {
        return vertices.get(idx);
    }

    public void addEdge(int srcVIdx, int destVIdx, int weight) {
        edges.add(new Edge(srcVIdx, destVIdx, weight));
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
        return null;
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
        // degree of vertex
        private int degree;

        public Vertex(int idx) {
            this(idx, INFINITY);
        }

        private Vertex(int idx, int minDist) {
            this.idx = idx;
            this.minDist = minDist;
        }

        public void reduceDegree() {
            this.degree -= 1;
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

        public int getDegree() {
            return degree;
        }

        public void setDegree(int degree) {
            this.degree = degree;
        }
    }

    public static class Edge {
        // it's a source vertex
        private int srcVIdx;
        // it's a destination vertex
        private int destVIdx;
        // it denotes the weight of edge
        private int weight;
        // TODO: 3/7/2023 try to rename this variable for better understanding
        private int repeat;

        public Edge(int srcVIdx, int destVIdx, int weight) {
            this.srcVIdx = srcVIdx;
            this.destVIdx = destVIdx;
            this.weight = weight;
            this.repeat = 1;
        }

        public void addRepeat() {
            repeat = repeat + 1;
        }

        public void subRepeat() {
            repeat = repeat - 1;
        }

        @Override
        public String toString() {
            return """
                    %d--%d--%d (%d)""".formatted(srcVIdx, weight, destVIdx, repeat);
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

        public int getRepeat() {
            return repeat;
        }

        public void setRepeat(int repeat) {
            this.repeat = repeat;
        }
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
