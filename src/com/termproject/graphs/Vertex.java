package com.termproject.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that implements the vertex structure
 */
public class Vertex<T> {
    private int id;
    private T data;

    // Adjacency list of the vertex
    private List<Vertex<T>> adjList = new ArrayList<>();
    private List<Edge<T>> edges = new ArrayList<>();

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    //Constructor with only value specified
    public Vertex(int value) {
        this.id = value;
    }

//    //Overloaded constructor
//    public Vertex(int value) {
//        this.id = value;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Vertex<T>> getAdjList() {
        return adjList;
    }

//    public void setAdjList(List<Vertex<T>> adjList) {
//        this.adjList = adjList;
//    }

    public void addAdjacentVertex(Edge<T> e, Vertex<T> v){
        edges.add(e);
        adjList.add(v);
    }

    public List<Edge<T>> getEdges(){
        return edges;
    }

    public int getDegree(){
        return edges.size();
    }

    @Override
    public String toString() {
        return "[id=" + id +"]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
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
        Vertex other = (Vertex) obj;
        if (id != other.id)
            return false;
        return true;
    }
}