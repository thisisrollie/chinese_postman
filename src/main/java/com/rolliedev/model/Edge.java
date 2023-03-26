package com.rolliedev.model;

public abstract class Edge {
    // it's a source vertex
    protected int srcVIdx;
    // it's a destination vertex
    protected int destVIdx;
    // it denotes the weight of edge
    protected int weight;
    // by default, it sets to 1
    protected int frequency;

    protected Edge(int srcVIdx, int destVIdx, int weight) {
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
