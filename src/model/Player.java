package model;

public class Player {
	
	//------------------------------------------------------ Attributes
	private String name;
	
	//------------------------------------------------------ Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	//----------------------------------------------------- Constructor
	public Player(String name) {
		this.name = name;
	}
	
	public String toPrint() {
		return name;
	}
}
