package com.rolliedev.model;

import java.util.Objects;

public class DirectedEdge extends Edge {

    public DirectedEdge(int srcVIdx, int destVIdx, int weight) {
        super(srcVIdx, destVIdx, weight);
    }

    @Override
    protected Object clone() {
        return new DirectedEdge(srcVIdx, destVIdx, weight);
    }

    @Override
    public String toString() {
        return String.format("(%d)--%d-->(%d)", srcVIdx, weight, destVIdx);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectedEdge edge = (DirectedEdge) o;
        return (srcVIdx == edge.srcVIdx && destVIdx == edge.destVIdx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(srcVIdx, destVIdx, weight);
    }
}
