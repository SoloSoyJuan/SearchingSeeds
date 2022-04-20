package model;

public class Player {
	
	//------------------------------------------------------ Attributes
	private String name;
	private String pName;
	private int collectedSeeds;
	
	//------------------------------------------------------ Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getPName() {
		return pName;
	}
	public void setPName(String pName) {
		this.pName = pName;
	}
	public int getCollecteSeeds() {
		return collectedSeeds;
	}
	public void setCollectedSeeds(int collectedSeeds) {
		this.collectedSeeds = collectedSeeds;
	}
	//----------------------------------------------------- Constructor
	public Player(String name, String pName) {
		this.name = name;
		this.pName = pName;
		this.collectedSeeds = 0;
	}
	//----------------------------------------------------- Methods
	/*
	 * to Print
	 * Method that return information of the Player
	 */
	public String toPrint() {
		return name;
	}
	
	/*
	 * addNewSeed
	 * Method that add a new seed to the collectedSeeds
	 */
	public void addNewSeed() {
		setCollectedSeeds(collectedSeeds+1);
	}
}
