package main.java.dbdriver;

import org.bson.codecs.pojo.annotations.BsonId;

public class Product {
	
    @BsonId
    private int PID;
    private String name;
    private double weight;
    
    public Product() {}

	public Product(int PID, String name, double weight) {
		this.PID = PID;
		this.name = name;
		this.weight = weight;
	}

	public int getPID() {
		return this.PID;
	}

	public void setPID(int PID) {
		this.PID = PID;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
