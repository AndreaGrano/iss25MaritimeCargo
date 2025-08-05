package main.java.planner.HoldMap;

public enum CellType {
	FREE("1"), OBSTACLE("X"), HOME("H"), IOPORT("I"), SLOT1("A"), SLOT2("B"), SLOT3("C"), SLOT4("D"), SLOT5("E"), NONE("-");
	
	private String code;
	private CellType(String code) {
		this.code = code;
	}
	
	public static CellType fromCode(String code) {
		switch (code) {
			case "1":
				return CellType.FREE;
			case "X":
				return CellType.OBSTACLE;
			case "H":
				return CellType.HOME;
			case "I":
				return CellType.IOPORT;
			case "A":
				return CellType.SLOT1;
			case "B":
				return CellType.SLOT2;
			case "C":
				return CellType.SLOT3;
			case "D":
				return CellType.SLOT4;
			case "E":
				return CellType.SLOT5;
			default:
				return CellType.NONE;
		}
	}
	
	@Override
	public String toString() {
		return code;
	}
}
