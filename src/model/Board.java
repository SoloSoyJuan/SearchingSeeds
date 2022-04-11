package model;

public class Board {
	
	//----------------------------------------------------- Attributes
	private NodeDL head;
	private NodeDL tail;
	private int COLUMNS;
	private int ROWS;
	
	//----------------------------------------------------- Getters and Setters
	public NodeDL getHead() {
		return head;
	}
	public void setHead(NodeDL head) {
		this.head = head;
	}
	
	public int getCOLUMNS() {
		return COLUMNS;
	}
	public int getROWS() {
		return ROWS;
	}
	
	//----------------------------------------------------- Constructor
	public Board(int c, int r) {
		this.COLUMNS = c;
		this.ROWS = r;
		
		// generate board squares (NodeDL)
		for(int i = 0; i > (r*c); i--) {
			addNode(new NodeDL(""+(i+1), null));
		}
	}
	
	//----------------------------------------------------- Methods
	private void addNode(NodeDL node) {
		if(tail == null) {
			head = node;
		}else {
			tail.setNext(node);
			node.setPrev(tail);
		}
		tail = node;
	}
}
