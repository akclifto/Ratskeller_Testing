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

    private final List<RouteNode> nodeList;
    private final List<Edge> edgeList;
    private Set<RouteNode> visitedNodes;
    private Set<RouteNode> unvisitedNodes;
    private Map<RouteNode, RouteNode> predecessors;
    private Map<RouteNode, Integer> distance;


    public ShortestPath(Graph graph){
        this.nodeList = new ArrayList<>(graph.getVertices());
        this.edgeList = new ArrayList<>(graph.getEdges());
    }
    

    public void execute(RouteNode source) {

        visitedNodes = new HashSet<>();
        unvisitedNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();

        distance.put(source, 0);
        unvisitedNodes.add(source);

        while (unvisitedNodes.size() > 0) {
            RouteNode node = getMinimum(unvisitedNodes);
            visitedNodes.add(node);
            unvisitedNodes.remove(node);
            findMinDistance(node);
        }
    }

    private int getDistance(RouteNode node, RouteNode target) {
        //TODO
        for (Edge edge : edgeList) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        return -1;
    }

    private List<RouteNode> getNeighbors(RouteNode node) {

        List<RouteNode> neighbors = new ArrayList<>();

        for (Edge edge : edgeList) {
            if (edge.getSource().equals(node) && !isVisited(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private RouteNode getMinimum(Set<RouteNode> vertices) {

        RouteNode min = null;
        for (RouteNode vertex : vertices) {
            if (min == null) {
                min = vertex;
            } else if (getShortestDistance(vertex) < getShortestDistance(min)) {
                min = vertex;
            }
        }
        return min;
    }


    private int getShortestDistance(RouteNode destination) {

        Integer dist = distance.get(destination);
        if (dist == null) {
            return Integer.MAX_VALUE;
        } else {
            return dist;
        }

    }

    private LinkedList<RouteNode> getPath(RouteNode target) {

        LinkedList<RouteNode> path = new LinkedList<>();
        RouteNode step = target;

        if (predecessors.get(step) == null) {
            step = predecessors.get(step);
            path.add(step);
        }

        Collections.reverse(path);
        return path;
    }

    private void findMinDistance(RouteNode node) {

        List<RouteNode> adjNodes = getNeighbors(node);

        for (RouteNode target : adjNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)){
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unvisitedNodes.add(target);
            }
        }
    }

    private void addLane(String laneID, int sourceLocationNo,
                         int destLocationNo, String duration) {

        Edge lane = new Edge(laneID, nodeList.get(sourceLocationNo),
                nodeList.get(destLocationNo), duration);
        edgeList.add(lane);

    }

    /**
     * Method: Check if a node has been visited.
     * Inputs: vertex : RouteNode
     * Returns: void
     * Description: Method to check if a node on the graph has been visited yet.
     */
    private boolean isVisited(RouteNode vertex) {
        return visitedNodes.contains(vertex);
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
        private final RouteNode source;
        private final RouteNode destination;
        private final String duration;

        private Edge(String id, RouteNode source, RouteNode destination, String duration) {
            this.id = id;
            this.source = source;
            this.destination = destination;
            this.duration = duration;
        }

        private String getId() {
            return id;
        }

        private RouteNode getDestination() {
            return destination;
        }

        private RouteNode getSource() {
            return source;
        }

        private String getWeight() {
            return duration;
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

        private final List<RouteNode> vertices;
        private final List<Edge> edges;

        private Graph(List<RouteNode> vertices, List<Edge> edges) {
            this.vertices = vertices;
            this.edges = edges;
        }

        public List<RouteNode> getVertices() {
            return vertices;
        }

        public List<Edge> getEdges() {
            return edges;
        }
    }
}
