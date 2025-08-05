package main.java.planner.SearchAlgorithm;

import java.util.*;

import main.java.planner.RobotState.RobotState.Direction;
//import unibo.planner23.model.RobotState.Direction;
import unibo.basicomm23.utils.CommUtils;
 


/*
 * Algoritmo A* che trova il path ma non indica le mosse del robot
 * 
 * La sequenza di mosse del DDR robot è determinata ialla procedura
 * FromPathToMoves, che richiede l'uso di RobotDir
 */
public class AStarPathfinding {

    // Heuristic function (Manhattan distance for grid-based)
    private static double calculateHeuristic(Node node, Node targetNode) {
        return Math.abs(node.x - targetNode.x) + Math.abs(node.y - targetNode.y);
        // For diagonal movement, you might use Euclidean distance:
        // return Math.sqrt(Math.pow(node.x - targetNode.x, 2) + Math.pow(node.y - targetNode.y, 2));
    }

    // A* algorithm implementation
    public static List<Node> findPath(int[][] grid, Node startNode, Node targetNode) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Open list: stores nodes to be evaluated, sorted by fCost
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        // Closed list: stores nodes already evaluated
        Set<Node> closedSet = new HashSet<>();

        // Map to store Node objects created, useful for consistent references
        // and retrieving nodes by coordinates
        Map<String, Node> allNodes = new HashMap<>();

        // Initialize start node
        startNode.gCost = 0;
        startNode.fCost = calculateHeuristic(startNode, targetNode);
        openSet.add(startNode);
        allNodes.put(startNode.x + "," + startNode.y, startNode);

        // Possible movements (up, down, left, right)
        // For diagonal movement, add: {-1,-1}, {-1,1}, {1,-1}, {1,1}
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        while (!openSet.isEmpty()) {
           //CommUtils.waitTheUser("&&&&&&&&&&&&& HIT fot next"); 
           
           System.out.println();
           CommUtils.outgreen("openSet before=" + openSet);
           Node current = openSet.poll(); // Get node with lowest fCost          
            CommUtils.outblack("--------------------------------------------------");
            CommUtils.outmagenta("selected current=" + current);
           CommUtils.outgreen("openSet after=" + openSet);
           CommUtils.outgreen("closedSet=" + closedSet);
 
            // If we reached the target
            if (current.equals(targetNode)) {
                return reconstructPath(current);
            }

            closedSet.add(current); // Move current to closed set

            // Explore neighbors
            for (int i = 0; i < 4; i++) { // Change 4 to 8 for diagonal movement
                int neighborX = current.x + dx[i];
                int neighborY = current.y + dy[i];

                // Check if neighbor is within grid bounds and not an obstacle (1 in grid represents obstacle)
                if (neighborX >= 0 && neighborX < rows &&
                    neighborY >= 0 && neighborY < cols &&
                    grid[neighborX][neighborY] == 0) { // Assuming 0 is traversable, 1 is obstacle

                    Node neighbor = allNodes.getOrDefault(
                    	neighborX + "," + neighborY, new Node(neighborX, neighborY));
                    allNodes.put(neighborX + "," + neighborY, neighbor); // Ensure it's in our map for consistent access

                    if (closedSet.contains(neighbor)) {
                    	CommUtils.outyellow("already evaluated (since in closedSet):" + neighbor);
                        continue; // Already evaluated this neighbor
                    }

                    // Calculate tentative gCost for neighbor
                    double tentativeGCost = current.gCost + 1; // Assuming cost of 1 to move to adjacent cell

//                    boolean tentativeIsBetter;
//                    if(!openSet.contains(neighbor)) {
//                    	CommUtils.outblue(i + " adding in openSet neighbor:" + neighbor + " tentativeGCost=" + tentativeGCost );
//                    	openSet.add(neighbor);
//                    	
//                    	tentativeIsBetter = true;
//                    } else if(tentativeGCost < neighbor.gCost) {
//                    	tentativeIsBetter = true;
//                    } else {
//                    	tentativeIsBetter = false;
//                    }
//                    
//                    if(tentativeIsBetter) {
//                    	neighbor.parent = current;
//                    	neighbor.gCost = tentativeGCost;
//                    	neighbor.hCost = calculateHeuristic(neighbor, targetNode);
//                      	neighbor.fCost = neighbor.gCost + neighbor.hCost;
//                    }
                    
                    // If we found a shorter path to this neighbor
                    if (tentativeGCost < neighbor.gCost) {
                        neighbor.parent = current;
                        neighbor.gCost = tentativeGCost;
                        neighbor.hCost = calculateHeuristic(neighbor, targetNode);
                        neighbor.fCost = neighbor.gCost + neighbor.hCost;

                        if (!openSet.contains(neighbor)) { // Add to openSet if not already there
                            CommUtils.outblue(i + " adding in openSet neighbor:" + neighbor + " tentativeGCost=" + tentativeGCost );
                            openSet.add(neighbor);
                        } else {
                            // If it's already in openSet but we found a better path,
                            // we need to update its position in the PriorityQueue.
                            // The easiest way is to remove and re-add.
                            openSet.remove(neighbor);
                            openSet.add(neighbor);
                        }
                    }
                }
            }
        }

        return null; // No path found
    }

    // Reconstructs the path from the target node back to the start node
    private static List<Node> reconstructPath(Node current) {
        List<Node> path = new ArrayList<>();
        while (current != null) {
            path.add(0, current); // Add to the beginning to get path in correct order
            current = current.parent;
        }
        return path;
    }

    public static void printPath(List<Node> path) {
        for (Node node : path) {
            System.out.print(node + " -> ");
        }   	
    }
    
    public static void showPathInGrid(int[][] grid, List<Node> path, Node start, Node target) {
        char[][] displayGrid = new char[grid.length][grid[0].length];
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                //if (grid[r][c] == 1) {
                if (grid[r][c] == 1) {	
                    displayGrid[r][c] = '#'; // Obstacle
                } else {
                    displayGrid[r][c] = '.'; // Traversable
                }
            }
        }
        for (Node p : path) {
            displayGrid[p.x][p.y] = '*'; // Path
        }
        displayGrid[start.x][start.y] = 'S'; // Start
        displayGrid[target.x][target.y] = 'T'; // Target

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                System.out.print(displayGrid[r][c] + " ");
            }
            System.out.println();
        }   	
    }
    
    protected static String changeX( boolean down  ) {
    	switch (dir) {
    		case DOWN  : if( down ) {return "w"; }
    		             else { dir =  Direction.UP; return "rrw"; }
    		case RIGHT : if( down ) { dir =  Direction.DOWN; return "rw"; }
    		             else { dir =  Direction.UP; return "lw";}
    		case LEFT  : if( down ) { dir =  Direction.DOWN; return "lw";}
    		             else { dir =  Direction.UP; return "rw"; }
    		case UP    : if( down ) { dir =  Direction.DOWN; return "rrw"; }
    		             else { dir =  Direction.UP; return "w"; }
    		default    : return "";
    	}
    }
 
    protected static String changeY( boolean right  ) {
    	switch (dir) {
    		case DOWN  : if( right ) { dir =  Direction.RIGHT; return "lw";} 
    		             else {  dir =  Direction.LEFT;  return "rw"; }
    		case RIGHT : if( right ) { return "w"; } 
    		             else { dir =  Direction.LEFT; return "rrw";  }
    		case LEFT  : if( right ) { dir =  Direction.RIGHT; return "rrw"; }
    					 else {  return "w"; }
    		case UP    : if( right ) {  dir =  Direction.RIGHT; return "rw"; }
    					 else {  dir = Direction.LEFT; return "lw"; }
    		default    : return "";
    	}
    }

    private static Direction dir;
    
    public static String fromPathToMoves(List<Node> path,Node start, Node target, Direction robotDir) {
    	StringBuilder  moves = new StringBuilder("");
    	if( path.size() == 1) return moves.toString();
    	dir = robotDir;

        while(  path.size() > 1 ) {
        	Node current = path.get(0);
            Node next    = path.get(1);    	
            
            if( (next.x == current.x) ) { moves.append( changeY(next.y > current.y)  ); }
            if( (next.y == current.y) ) { moves.append( changeX(next.x > current.x)  ); }
            
            path.remove(0);           	 
        }  
    	return moves.toString();
    }
}
