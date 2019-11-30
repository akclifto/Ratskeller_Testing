import java.util.*;

/**
 * File:	    ShortestPath.java
 * Author:    Adam Clifton (akclifto@asu.edu)
 * Date:      2019.11.29
 *
 * <p>Description:  This class file will node RouteNodes given in the system and calculate
 * the shortest path from the source node to the destination node.  This is done using
 * Dijkstra's Algorithm to find the shortest path.
 */
public class ShortestPath {

    private RouteNode node;
    private CalculateRoute calc;
    private HashMap<Integer, RouteNode> nodeHashMap = new HashMap<>();
    private final List<Vertex> nodeList;
    private final List<Edge> edgeList;
    private Set<Vertex> visitedNodes;
    private Set<Vertex> unvisitedNodes;
    private Map<Vertex, Vertex> predecessors;
    private Map<Vertex, Integer> distance;


    public ShortestPath(Graph graph){
        this.nodeList = new ArrayList<>(graph.getVertices());
        this.edgeList = new ArrayList<>(graph.getEdges());
    }
    

    public void execute(Vertex source) {

        visitedNodes = new HashSet<>();
        unvisitedNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();

        distance.put(source, 0);
        unvisitedNodes.add(source);

        while (unvisitedNodes.size() > 0) {
            Vertex node = getMinimum(unvisitedNodes);
            visitedNodes.add(node);
            unvisitedNodes.remove(node);
            findMinDistance(node);
        }
    }

    private int getDistance(Vertex node, Vertex target) {
        //TODO
        for (Edge edge : edgeList) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        return -1;
    }

    private List<Vertex> getNeighbors(Vertex node) {

        List<Vertex> neighbors = new ArrayList<>();

        for (Edge edge : edgeList) {
            if (edge.getSource().equals(node) && !isVisited(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private Vertex getMinimum(Set<Vertex> vertices) {

        Vertex min = null;
        for (Vertex vertex : vertices) {
            if (min == null) {
                min = vertex;
            } else if (getShortestDistance(vertex) < getShortestDistance(min)) {
                min = vertex;
            }
        }
        return min;
    }


    private int getShortestDistance(Vertex destination) {

        Integer dist = distance.get(destination);
        if (dist == null) {
            return Integer.MAX_VALUE;
        } else {
            return dist;
        }

    }

    private LinkedList<Vertex> getPath(Vertex target) {

        LinkedList<Vertex> path = new LinkedList<>();
        Vertex step = target;

        if (predecessors.get(step) == null) {
            step = predecessors.get(step);
            path.add(step);
        }

        Collections.reverse(path);
        return path;
    }

    private void findMinDistance(Vertex node) {

        List<Vertex> adjNodes = getNeighbors(node);

        for (Vertex target : adjNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)){
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unvisitedNodes.add(target);
            }
        }
    }

    /**
     * Method: Check if a node has been visited.
     * Inputs: vertex : Vertex
     * Returns: void
     * Description: Method to check if a node on the graph has been visited yet.
     */
    private boolean isVisited(Vertex vertex) {
        return visitedNodes.contains(vertex);
    }


    /**
     * File:	  Vertex
     * Author:    Adam Clifton (akclifto@asu.edu)
     * Date:      2019.11.29
     * <p>
     * Description:  Private nested class that will contain the graph vertices or nodes used to
     * find the shortest path.
     */
    private class Vertex {

        final private String id;
        final private String name;

        private Vertex(String id, String name) {
            this.id = id;
            this.name = name;
        }

        private String getId() {
            return id;
        }

        private String getName() {
            return name;
        }

        @Override
        public int hashCode(){
            final int prime = 17;
            int result = 1;
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object object) {

            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (getClass() != object.getClass()) {
                return false;
            }
            Vertex other = (Vertex) object;
            if (id == null) {
                if (other.id != null) {
                    return false;
                }
            } else if (!id.equals(other.id)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return name;
        }
    }


    /**
     * File:	  Edge
     * Author:    Adam Clifton (akclifto@asu.edu)
     * Date:      2019.11.29
     *
     * <p>Description:  Private nested class that will contain the edges between each route node used
     * to find the shortest path.
     */
    private class Edge {

        private final String id;
        private final Vertex source;
        private final Vertex destination;
        private final int weight;

        private Edge(String id, Vertex source, Vertex destination, int weight) {
            this.id = id;
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        private String getId() {
            return id;
        }

        private Vertex getDestination() {
            return destination;
        }

        private Vertex getSource() {
            return source;
        }

        private int getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return source + " " + destination;
        }
    }

    /**
     * File:	  Graph
     * Author:    Adam Clifton (akclifto@asu.edu)
     * Date:      2019.11.29
     *
     * <p>Description:  Private nested class that will contain the graph of the nodes presented
     * to find the shortest path.
     */
    private class Graph {

        private final List<Vertex> vertices;
        private final List<Edge> edges;

        private Graph(List<Vertex> verticex, List<Edge> edges) {
            this.vertices = verticex;
            this.edges = edges;
        }

        public List<Vertex> getVertices() {
            return vertices;
        }

        public List<Edge> getEdges() {
            return edges;
        }
    }





}
