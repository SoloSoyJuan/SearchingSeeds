package model;

import java.util.ArrayList;

public class NodeDL {
	
	//----------------------------------------------------- Attributes
	private int num;
	private ArrayList<Player> p = new ArrayList<>();
	private NodeDL prev;
	private NodeDL next;
	private NodeDL linked;
	private boolean seed;
	private String letter;
	
	//----------------------------------------------------- Getters and Setters
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public ArrayList<Player> getP() {
		return p;
	}
	public void addP(Player p) {
		this.p.add(p);
	}
	public NodeDL getPrev() {
		return prev;
	}
	public void setPrev(NodeDL prev) {
		this.prev = prev;
	}
	public NodeDL getNext() {
		return next;
	}
	public void setNext(NodeDL next) {
		this.next = next;
	}
	public NodeDL getLinked() {
		return linked;
	}
	public void setLinked(NodeDL linked) {
		this.linked = linked;
	}
	public boolean getSeed() {
		return seed;
	}
	public void setSeed(boolean seed) {
		this.seed = seed;
	}
	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	//----------------------------------------------------- Constructor
	public NodeDL(int num) {
		this.num = num;
		seed = false;
	}
}
