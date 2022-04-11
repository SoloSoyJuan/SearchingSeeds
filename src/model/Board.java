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
		for(int i = 0; i < (COLUMNS*ROWS); i++) {
			addNode(new NodeDL(""+(i+1), null));
		}
		
		// set the player in the first square
		head.setP(new Player("j"));
	}
	
	//----------------------------------------------------- Methods
	
	/*
	 * addMethod
	 * Method that add a new node to the board in the last position
	 */
	private void addNode(NodeDL node) {
		if(tail == null) {
			head = node;
		}else {
			tail.setNext(node);
			node.setPrev(tail);
		}
		tail = node;
	}
	
	/*
	 * toString
	 * Method that return a String with the board status
	 */
	public String toPrint() {
		String theBoard = "";
		
		theBoard = toPrint(head, theBoard);
		return theBoard;
	}
	
	private String toPrint(NodeDL current, String s) {
		if(current == null) {
			s+="";
		}else {
			if(current.getP() != null) {
				s += "["+current.getP().toPrint()+"] "+ toPrint(current.getNext(), s);
			}else {
				s += "["+current.getNum()+"] "+ toPrint(current.getNext(), s);
			}
		}
		return s;
	}
}
