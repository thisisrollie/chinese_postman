package com.rolliedev.model;

public abstract class Edge {
    /**
     * It's a source vertex of the edge.
     */
    protected int srcVIdx;
    /**
     * It's a destination vertex of the edge.
     */
    protected int destVIdx;
    /**
     * It denotes the weight of the edge.
     */
    protected int weight;
    /**
     * This field is used in the Chinese Postman Problem, allowing us to traverse an edge multiple times.
     * By default, its value is set to 1.
     * <p>For example, when <b>frequency</b> = 1, it means that we can only traverse the edge once.</p>
     */
    protected int frequency;

    protected Edge(int srcVIdx, int destVIdx, int weight, int frequency) {
        this.srcVIdx = srcVIdx;
        this.destVIdx = destVIdx;
        this.weight = weight;
        this.frequency = frequency;
    }

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isNegativeWeighted() {
        return weight < 0;
    }

    public void increaseFrequency() {
        frequency = frequency + 1;
    }

    public void reduceFrequency() {
        frequency = frequency - 1;
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
