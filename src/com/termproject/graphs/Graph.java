package com.termproject.graphs;

import java.util.*;

/**
 * Implementation of a directed, weighted graph with BFS, DFS and
 * a method to get unreachable nodes from a given node in a graph
 */
public class Graph<T> {
    private Map<Integer, Vertex<T>> vertices;
    private List<Edge<T>> edges;
    private boolean isDirected = false;

    public Collection<Vertex<T>> getVertices() {
        return vertices.values();
    }


    public Graph(boolean isDirected) {
        edges = new ArrayList<>();
        vertices = new HashMap<>();
        this.isDirected = isDirected;
    }

    public void addEdge(int id1, int id2){
        addEdge(id1,id2,1);
    }

    public void addEdge(int srcId,int destId, int weight){
        Vertex<T> src = null;
        if(vertices.containsKey(srcId)) {
            src = vertices.get(srcId);
        }
        else {
            src = new Vertex<T>(srcId);
            vertices.put(srcId, src);
        }

        Vertex<T> dest = null;
        if(vertices.containsKey(destId)) {
            dest = vertices.get(destId);
        }
        else {
            dest = new Vertex<T>(destId);
            vertices.put(destId, dest);
        }

        Edge<T> edge = new Edge<T>(src, dest, weight, isDirected);
        edges.add(edge);
        if(edge == null)
            System.out.println("Edge is null!");
        if(dest == null)
            System.out.println("Vertex is null");
        src.addAdjacentVertex(edge, dest);
        if(!isDirected) {
            dest.addAdjacentVertex(edge, src);
        }
    }
}
