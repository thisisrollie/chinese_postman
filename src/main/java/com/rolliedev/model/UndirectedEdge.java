package com.rolliedev.model;

import java.util.Objects;

public class UndirectedEdge extends Edge {

    protected static int increment = 1;
    private final int id; // TODO: 3/26/23 do not forget about id

    public UndirectedEdge(int srcVIdx, int destVIdx, int weight) {
        this(increment++, srcVIdx, destVIdx, weight);
    }

    private UndirectedEdge(int id, int srcVIdx, int destVIdx, int weight) {
        super(srcVIdx, destVIdx, weight);
        this.id = id;
    }

    @Override
    protected Object clone() {
        return new UndirectedEdge(id, srcVIdx, destVIdx, weight);
    }

    @Override
    public String toString() {
        return String.format("(%d)--%d--(%d)", srcVIdx, weight, destVIdx);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UndirectedEdge edge = (UndirectedEdge) o;
        return ((srcVIdx == edge.srcVIdx && destVIdx == edge.destVIdx) ||
                (srcVIdx == edge.destVIdx && destVIdx == edge.srcVIdx));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }
}
