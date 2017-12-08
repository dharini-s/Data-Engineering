package com.termproject.graphs;

/**
 * Class that implements the edge structure
 */
public class Edge<T> {
    private Vertex<T> src;
    private Vertex<T> dest;
    private int weight;
    private boolean isDirected;

    /**
     * Constructor that sets source and destination nodes
     * Used for unweighted graphs
     */
    public Edge(Vertex<T> from, Vertex<T> to) {
        // Setting default weight for unweighted graph as 1
        this(from, to, 1);
        isDirected = false;
    }

    /**
     * Constructor that sets source, destination and weight of the edge
     * Used for weighted graphs
     */
    public Edge(Vertex<T> from, Vertex<T> to, int weight) {
        this(from, to, weight, false);
    }
    public Edge(Vertex<T> from, Vertex<T> to, int weight, boolean isDirected) {
        this.src = from;
        this.dest = to;
        this.weight = weight;
        this.isDirected = isDirected;
    }

    int getWeight(){
        return weight;
    }

    @Override
    public String toString() {
        return "[src=" + src.getData()+ ", dest=" + dest.getData() + ", weight=" + weight + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result += ((src == null) ? 0 : src.hashCode());
        result = prime * result + ((dest == null) ? 0 : dest.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Edge other = (Edge) obj;
        if (src == null) {
            if (other.src != null)
                return false;
        } else if (!src.equals(other.src))
            return false;
        if (dest == null) {
            if (other.dest != null)
                return false;
        } else if (!dest.equals(other.dest))
            return false;
        return true;
    }
}
