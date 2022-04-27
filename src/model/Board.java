package model;

import java.util.*;

public class Board {
	
	//------------------------------------------------------------- Attributes
	private NodeDL head;
	public final int COLUMNS;
	public final int ROWS;
	private final Dice dice;
	private NodeDL printNode;
	private int totalSeeds;
	
	//------------------------------------------------------------- Getters and Setters

	public Dice getDice() {
		return dice;
	}
	public int getTotalSeeds() {
		return totalSeeds;
	}
	public void setTotalSeeds(int totalSeeds) {
		this.totalSeeds = totalSeeds;
	}
	
	//------------------------------------------------------------- Constructor
	public Board(int c, int r, String p1, String p2, int portals) { // portals doesn`t work yet
		this.COLUMNS = c;
		this.ROWS = r;
		this.dice = new Dice();
		this.totalSeeds = randomNumber((COLUMNS*ROWS), 1);
		
		// generate board squares (NodeDL)
		generateBoard();
		
		putSeedsOnSquares(head, totalSeeds, randomNumber((COLUMNS*ROWS), 1));

		this.printNode = head;
		
		// set the player in the first square
		head.addP(new Player("R", p1));
		head.addP(new Player("M", p2));
	}
	
	//-------------------------------------------------------------- Methods
	
	private int randomNumber(int max, int min) {
		int result = 0;
		result = (int) Math.floor(Math.random()*(max-min+1)+min);
		return result;
	}
	
	public void putSeedsOnSquares(NodeDL current,int seeds, int squares) {
		if(seeds == 0) {
			return;
		}else if(squares < 0) {
			squares = randomNumber((COLUMNS*ROWS),1);
		}else if(squares == 0){
			if(!current.getSeed()) {
				current.setSeed(true);
				seeds--;
			}
		}
		putSeedsOnSquares(current.getNext(), seeds, --squares);
	}
	
	/**
	 * Creates a portal between two nodes
	 * @param numLinkeds
	 */
	public void randomLinked(int numLinkeds){
		int valueMax = head.getPrev().getNum();
		ArrayList<Integer> numbers =  new ArrayList<>();
		for (int i = 0; i < valueMax; i++) {
			numbers.add(i);
		}
		while(numLinkeds>=0) {
			int x = (int) Math.random()*valueMax;
			numbers.remove(x);
			valueMax--;
			int y = (int) Math.random()*valueMax;
			numbers.remove(y);
			valueMax--;
			NodeDL prevX = head;
			NodeDL prevY = head;
			for(int i = 0; i<x; i++) {
				prevX.getNext();
			}
			for(int i = 0; i<y; i++) {
				prevY.getNext();
			}
			prevX.setLinked(prevY);
			prevY.setLinked(prevX);
		}
	}
	
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
		// to generate the board squares (NodeDL)
		for(int i = (COLUMNS*ROWS); i > 0; i--) {
			addNode(new NodeDL(i));
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
			if(current.getP().size() != 0) {
				s += "["+current.getP().get(0).toPrint()+"]";
			}else {
				s += "["+current.getNum()+"]";
			}
		}else {
			if(current.getP().size() != 0) {
				s += "["+current.getP().get(0).toPrint()+"] "+ toPrint(current.getNext(), s);
			}else {
				s += "["+current.getNum()+"] "+ toPrint(current.getNext(), s);
			}
		}

		return s;
	}

	/**
	 * Prints the board in the form of a snake
	 * @return the String representing the layout of the board
	 */
	public String print(){
		String board = "";

		for (int i = 0; i < ROWS; i++) {
			String s = generateRow(printNode, "");

			if (i % 2 == 0) {
				board += s + "\n";
			} else {

				StringBuilder reverser = new StringBuilder();
				String reverseRow = "";

				for (int j = s.length() - 1; j >= 0; j--) {

					reverser.append(s.charAt(j));

					if (s.charAt(j) == '[') {
						reverser.reverse();
						reverseRow += reverser;
						reverser = new StringBuilder();
					}
				}

				board += reverseRow + "\n";
			}
		}

		return board;
	}

	private String generateRow(NodeDL current, String s) {
		if(current.getNum() % COLUMNS == 0) {
			if(current.getP().size() == 1) {
				s += "["+current.getP().get(0).toPrint()+"] ";
			}else if(current.getP().size() == 2){
				s += "[B] ";
			}else if(current.getSeed()) {
				s += "[*] ";
			}else {
				s += "["+current.getNum()+"] ";
			}

			printNode = current.getNext();
		}else {
			if(current.getP().size() == 1) {
				s += "["+current.getP().get(0).toPrint()+"] "+ generateRow(current.getNext(), s);
			}else if (current.getP().size() == 2){
				s += "[B] "+ generateRow(current.getNext(), s);
			}else if(current.getSeed()) {
				s += "[*] "+ generateRow(current.getNext(), s);
			}else {
				s += "["+current.getNum()+"] "+ generateRow(current.getNext(), s);
			}
		}

		return s;
	}
	
	/*
	 * throwDice
	 * Method that return a random number with the dice
	 * And work as a trigger of moveSquares method
	 */
	public int throwDice(String turn) {
		int total = 0;
		total += dice.getTotal();
		moveSquares(total, head, turn);
		return total;
	}
	
	/*
	 * moveSquares
	 * Method that move the player in the board depending on the dice
	 */
	private void moveSquares(int theDice, NodeDL node, String turn) {
		if(theDice == 0) {
			return;
		}else if(node.getP().size() != 0) {
			ArrayList<Player> p = node.getP();
			for(int i = 0; i < p.size(); i++) {
				if(p.get(i).getName().equals(turn)) {
					Player player = p.get(i);
					node.getNext().addP(player);
					node.getP().remove(player);
					theDice--;
				}
			}
		}
		moveSquares(theDice, node.getNext(), turn);
	}
}
