package main.java.planner;

import java.io.IOException;
import java.util.List;

import main.java.planner.HoldMap.Cell;
import main.java.planner.HoldMap.CellType;
import main.java.planner.HoldMap.HoldMap;
import main.java.planner.HoldMap.HoldMapLoader;
import main.java.planner.Exceptions.CellNotFoundException;
import main.java.planner.Parser.HoldMapParser;
import main.java.planner.RobotState.RobotState;
import main.java.planner.RobotState.RobotState.Direction;
import main.java.planner.SearchAlgorithm.AStarPathfinding;
import main.java.planner.SearchAlgorithm.Node;
import unibo.basicomm23.utils.CommUtils;
//import unibo.planner23.model.RobotState;
//import unibo.planner23.model.RobotState.Direction;

public class PlannerForHold {
	RobotState robotState;
	HoldMap holdMap;
	int[][] holdGridMap;
	
	public PlannerForHold() {
		super();
	}
	
	public void initRobotState() {
		robotState = new RobotState(0, 0, Direction.DOWN);
	}
	
	public HoldMap loadMap(String fileName) throws ClassNotFoundException, IOException {
		holdMap = HoldMapLoader.loadHoldMap(fileName);
		
		holdGridMap = HoldMapParser.toObstacleGrid(holdMap);
		
		return holdMap;
	}
	
	public String findPath(int x, int y) {
		Node start = new Node(robotState.getX(), robotState.getY());
		Node target = new Node(x, y);
		
		if(holdGridMap[x][y] == 1) { // if cell is an obstacle (or a slot)
			if(holdGridMap[x + 1][y] == 0) { // try coming from below
				target = new Node(x + 1, y);
			} else if (holdGridMap[x - 1][y] == 0) { // try coming from above
				target = new Node(x - 1, y);
			} else if (holdGridMap[x][y - 1] == 0) { // try coming from left side
				target = new Node(x, y - 1);
			} else if (holdGridMap[x][y + 1] == 0) { // try coming from right side
				target = new Node(x, y + 1);
			}
		}
		
		List<Node> nodeList = AStarPathfinding.findPath(holdGridMap, start, target);
		String path = AStarPathfinding.fromPathToMoves(nodeList, start, target, robotState.getDirection());

		return path;
	}
	
	public void moveRobotInTheMap() {
        try {
        	int x = robotState.getX();
        	int y = robotState.getY();

			Cell currCell = holdMap.getCell(x, y);
			currCell.setRobot(true);
			holdMap.putCell(x, y, currCell);
		} catch (CellNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public void doMove(char move) {
        Integer x   = robotState.getX();
        Integer y   = robotState.getY();

        try {
			Cell currCell = holdMap.getCell(x, y);
			currCell.setRobot(false);
			holdMap.putCell(x, y, currCell);
		} catch (CellNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
        try {
            switch (move) {
                case 'w' : {
                	robotState = robotState.forward();
                    moveRobotInTheMap();
                    return;
                }
                case 's': {
                	robotState = robotState.backward();
                    moveRobotInTheMap();
                    return;
                }
                case 'a'  : {
                	robotState = robotState.turnLeft();
                    moveRobotInTheMap();
                    return;
                }
                case 'l' : {
                    robotState = robotState.turnLeft();                    
                    moveRobotInTheMap();
                    return;
                }
                case 'd' : {
                    robotState = robotState.turnRight();
                    moveRobotInTheMap();
                    return;
                }
                case 'r' : {
                	robotState = robotState.turnRight();
                    moveRobotInTheMap();
                    return;
                }
            }//when
        } catch (Exception e ) {
            CommUtils.outred("PlannerForHold doMove:" + move + " ERROR:" + e.getMessage());
        }
    }
	
	public void doPath(String path) {
		char[] moves = path.toCharArray();
		
		for(char move : moves) {
			this.doMove(move);
		}
	}
	
	public int[] getRobotCoords() throws CellNotFoundException {
		int[] robotCoords = new int[2];
		
		robotCoords[0] = robotState.getX();
		robotCoords[1] = robotState.getY();
		
		return robotCoords;
	}
	
	public int[] getCellCoordsByType(CellType type) {
		return holdMap.getCellCoordsByType(type);
	}
}
