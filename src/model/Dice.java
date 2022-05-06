package model;

public class Dice {
	
	//----------------------------------------------------- Attributes
	private double total;
	
	//----------------------------------------------------- Getters and Setters
	public double getTotal() {
		setTotal();
		return total;
	}

	private void setTotal() {
		this.total = 1+(Math.random()*6);
	}
	
	//----------------------------------------------------- Constructor 
	public Dice() {
		total = 0.0;
	}
}
