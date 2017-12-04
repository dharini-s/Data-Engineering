package com.termproject.graphs;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HamiltonianCycle<T> {

    public boolean getHamiltonianCycle(Graph<T> graph, List<Vertex<T>> result, int degree, int weightLimit) {
        Vertex<T> startVertex = graph.getVertices().iterator().next();
        Set<Vertex<T>> visited = new HashSet<>();
        return hamiltonianUtil(startVertex, startVertex, result, visited, graph.getVertices().size(), degree, weightLimit);
    }

    private boolean hamiltonianUtil(Vertex<T> startVertex, Vertex<T> currentVertex,
                                    List<Vertex<T>> result, Set<Vertex<T>> visited,
                                    int totalVertex, int degree, int weightLimit)  {
        if(currentVertex.getDegree() < degree)
            return false;
        for(Edge<T> edge: currentVertex.getEdges())  {
            if(edge.getWeight() > weightLimit )
                return false;
        }

        visited.add(currentVertex);
        result.add(currentVertex);

        for(Vertex<T> child : currentVertex.getAdjList()){
            if(startVertex.equals(child) && totalVertex == result.size()){
                result.add(startVertex);
                return true;
            }
            if(!visited.contains(child)){
                boolean isHamil = hamiltonianUtil(startVertex,child,result,visited,totalVertex, degree, weightLimit);
                if(isHamil){
                    return true;
                }
            }
        }
        result.remove(result.size()-1);
        visited.remove(currentVertex);
        return false;
    }
}
