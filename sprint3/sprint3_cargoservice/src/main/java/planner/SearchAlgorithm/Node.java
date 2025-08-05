package main.java.planner.SearchAlgorithm;

import java.util.Objects;

public class Node implements Comparable<Node> {
	int x;
    int y;
    double gCost; // Cost from start to this node
    double hCost; // Heuristic cost from this node to end
    double fCost; // gCost + hCost
    Node parent;  // Parent node to reconstruct path

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.gCost = 100; //Double.MAX_VALUE;
        this.hCost = 100; //Double.MAX_VALUE;
        this.fCost = 100; //Double.MAX_VALUE;
        this.parent = null;
    }

    // For comparison in PriorityQueue based on fCost
    @Override
    public int compareTo(Node other) {
        return Double.compare(this.fCost, other.fCost);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")"+gCost+"/"+hCost;
    }
}
