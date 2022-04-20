package model;

public class Board {
	
	//------------------------------------------------------------- Attributes
	private NodeDL head;
	private int COLUMNS;
	private int ROWS;
	private Dice dice;
	
	//------------------------------------------------------------- Getters and Setters
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
		generateBoard();
		
		// set the player in the first square
		head.setPlayer(new Player("R", p1));
		//head.setPlayer(new Player("M", p2));
	}
	
	//-------------------------------------------------------------- Methods
	
	/*
	 * addMethod
	 * Method that add a new node to the board in the last position
	 */
	private void addNode(NodeDL node) {
		if(head == null) {
			head = node;
			head.setNext(head);
			head.setPrev(head);
		}else {
			NodeDL tail = head.getPrev();
			tail.setNext(node);
			node.setPrev(tail);
			
			node.setNext(head);
			head.setPrev(node);
			
			head = node;
		}
	}
	
	/*
	 * generateBoard
	 * Method that generated all the squares of the board
	 */
	private void generateBoard() {
		// for to generated the board squares (NodeDL)
		for(int i = (COLUMNS*ROWS); i > 0; i--) {
			addNode(new NodeDL(""+(i)));
		}
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
		if(current.getNext() == head) {
			if(current.getP() != null) {
				s += "["+current.getP().toPrint()+"]";
			}else {
				s += "["+current.getNum()+"]";
			}
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
			node.getNext().setPlayer(p);
			node.setPlayer(null);
			theDice--;
		}
		moveSquares(theDice, node.getNext());
	}
}
