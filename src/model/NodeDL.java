package model;

public class NodeDL {
	
	//----------------------------------------------------- Attributes
	private String num;
	private Player p;
	private Player p2;
	private NodeDL prev;
	private NodeDL next;
	private NodeDL linked;
	private boolean seed;
	
	//----------------------------------------------------- Getters and Setters
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public Player getP() {
		return p;
	}
	private void setP(Player p) {
		this.p = p;
	}
	public Player getP2() {
		return p2;
	}
	private void setP2(Player p2) {
		this.p2 = p2;
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
	//----------------------------------------------------- Constructor
	public NodeDL(String num) {
		this.num = num;
	}
	
	public void setPlayer(Player p) {
		if(this.p == null) {
			setP(p);
		}else {
			setP2(p);
		}
	}
}
