package main.java.planner.HoldMap;

import java.io.Serializable;

public class Cell implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5095172256672510932L;
	private CellType type;
	private boolean isRobot;
	
	public Cell(CellType type) {
		this.type = type;
		this.isRobot = false;
	}
	
	public Cell(String code) {
		this.type = CellType.fromCode(code);
	}
	
	public Cell() {
		
	}

	public CellType getType() {
		return type;
	}

	public void setType(CellType type) {
		this.type = type;
	}
	
	public boolean isRobot() {
		return isRobot;
	}

	public void setRobot(boolean isRobot) {
		this.isRobot = isRobot;
	}

	@Override
	public String toString() {
		return type.toString();
	}
}
