package main.java.dbdriver;

import org.bson.codecs.pojo.annotations.BsonId;

public class Slot {
	
    @BsonId
    private int id;
    private int slotNumber; 
    private int PID;
    
    public Slot() {}

	public Slot(int id, int slotNumber, int PID) {
		this.id = id;
		this.slotNumber = slotNumber;
		this.PID = PID;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(int slotNumber) {
		this.slotNumber = slotNumber;
	}

	public int getPID() {
		return this.PID;
	}

	public void setPID(int PID) {
		this.PID = PID;
	}
}
