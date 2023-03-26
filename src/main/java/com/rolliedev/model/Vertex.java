package com.rolliedev.model;

import java.util.Objects;

import static com.rolliedev.util.GraphConst.INFINITY;

public class Vertex implements Comparable<Vertex> {
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
        return new Vertex(idx, minDist);
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
