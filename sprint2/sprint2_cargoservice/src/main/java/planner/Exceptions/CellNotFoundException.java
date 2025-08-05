package main.java.planner.Exceptions;

public class CellNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1334412961346134837L;

	public CellNotFoundException() {
		super();
	}
	
	public CellNotFoundException(String message) {
		super(message);
	}
}
