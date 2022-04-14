package model;

public class Board {
	
	//------------------------------------------------------------- Attributes
	private NodeDL head;
	private NodeDL tail;
	private int COLUMNS;
	private int ROWS;
	private Dice dice;
	
	//------------------------------------------------------------- Getters and Setters
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
	public Dice getDice() {
		return dice;
	}
	
	//------------------------------------------------------------- Constructor
	public Board(int c, int r, String p1, String p2, int portals) { // portals doesn`t work yet
		this.COLUMNS = c;
		this.ROWS = r;
		this.dice = new Dice();
		
		// generate board squares (NodeDL)
		for(int i = 0; i < (COLUMNS*ROWS); i++) {
			addNode(new NodeDL(""+(i+1), null));
		}
		
		// set the player in the first square
		head.setP(new Player("R", p1));
		tail.setP(new Player("M", p2));
	}
	
	//-------------------------------------------------------------- Methods
	
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
	
	/*
	 * toPrint
	 * Method that return all the board status information
	 */
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
	
	/*
	 * throwDice
	 * Method that return a random number with the dice
	 * And work as a trigger of moveSquares method
	 */
	public String throwDice() {
		String total = "";
		total += ""+(int)dice.getTotal();
		moveSquares(Integer.parseInt(total), head);
		return total;
	}
	
	/*
	 * moveSquares
	 * Method that move the player in the board depending on the dice
	 */
	private void moveSquares(int theDice, NodeDL node) {
		if(theDice == 0 || node.getNext() == null) {
			return;
		}else if(node.getP() != null) {
			Player p = node.getP();
			node.getNext().setP(p);
			node.setP(null);
			theDice--;
		}
		moveSquares(theDice, node.getNext());
	}
}
