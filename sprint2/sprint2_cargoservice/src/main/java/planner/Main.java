package main.java.planner;

import java.io.IOException;
import java.util.Arrays;

import main.java.planner.Exceptions.CellNotFoundException;
import main.java.planner.HoldMap.CellType;
import main.java.planner.HoldMap.HoldMap;
import main.java.planner.HoldMap.HoldMapLoader;

public class Main {

	public static void main(String[] args) {
		try {
			HoldMapLoader.fromTextToBinFile("./HoldMap.txt");
			
			PlannerForHold planner = new PlannerForHold();
			planner.initRobotState();
			
			HoldMap holdMap = planner.loadMap("HoldMap.bin");
			System.out.println(holdMap.toString());
			
			int[] robotCoords = planner.getRobotCoords();
			System.out.println("Initialized robot coords: " + Arrays.toString(robotCoords));
			
			int[] homeCoords = planner.getCellCoordsByType(CellType.HOME);
			System.out.println("HOME coords: " + Arrays.toString(homeCoords));
			
			int[] ioportCoords = planner.getCellCoordsByType(CellType.IOPORT);
			System.out.println("IOPORT coords: " + Arrays.toString(ioportCoords));
			
			int[] slot2Coords = planner.getCellCoordsByType(CellType.SLOT2);
			System.out.println("SLOT2 coords: " + Arrays.toString(slot2Coords));
			
			String path = planner.findPath(ioportCoords[0], ioportCoords[1]);
			System.out.println(path);
			planner.doPath(path);
			
			robotCoords = planner.getRobotCoords();
			System.out.println("Current robot coords: " + Arrays.toString(robotCoords));
			
			path = planner.findPath(slot2Coords[0], slot2Coords[1]);
			System.out.println("path: " + path);
			planner.doPath(path);
			
			robotCoords = planner.getRobotCoords();
			System.out.println("Current robot coords: " + Arrays.toString(robotCoords));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (CellNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}

}
