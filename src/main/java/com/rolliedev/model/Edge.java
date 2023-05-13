package com.rolliedev.model;

public abstract class Edge {
    // TODO: 4/8/23 of edge or of an edge???
    /**
     * It's a source vertex of an edge.
     */
    protected int srcVIdx;
    /**
     * It's a destination vertex of edge.
     */
    protected int destVIdx;
    /**
     * It denotes the weight of edge.
     */
    protected int weight;
    /**
     * This field we need for Chinese Postman Problem, where we can traverse the edge more than once.
     * By default, it sets to 1.
     * <p>For example, <b>frequency</b> = 1 means that we can traverse the edge only once.</p>
     */
    protected int frequency;

    protected Edge(int srcVIdx, int destVIdx, int weight) {
        this.srcVIdx = srcVIdx;
        this.destVIdx = destVIdx;
        this.weight = weight;
        this.frequency = 1;
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
