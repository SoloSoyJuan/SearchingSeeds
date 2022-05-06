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
	public Board(int c, int r, String p1, String p2, int portals, int seeds) {
		this.COLUMNS = c;
		this.ROWS = r;
		this.dice = new Dice();
		this.totalSeeds = seeds;
		
		// generate board squares (NodeDL)
		generateBoard();
		
		putSeedsOnSquares(head, totalSeeds, randomNumber((COLUMNS*ROWS), 1));

		this.printNode = head;
		
		// set the player in the first square
		putPlayersOnSquares(head, new Player("R", p1), randomNumber((COLUMNS*ROWS), 1));
		putPlayersOnSquares(head, new Player("M", p2), randomNumber((COLUMNS*ROWS), 1));
		randomLinked(portals);
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
	
	public void putPlayersOnSquares(NodeDL current,Player p, int squares) {
		if(squares == 0) {
			current.addP(p);
			return;
		}
		putPlayersOnSquares(current.getNext(), p, --squares);
	}

	public Player findPlayer(String name){
		boolean found = false;
		NodeDL nd = head;
		Player playerToFind = null;

		while (found==false){
			if(nd.getP().size()!=0){
				if(nd.getP().size() == 1) {
					if (nd.getP().get(0).getName().equals(name)){
						playerToFind = nd.getP().get(0);
						found=true;
					}
				} else
					if (nd.getP().get(0).getName().equals(name)){
						playerToFind = nd.getP().get(0);
						found=true;
					} else if (nd.getP().get(1).getName().equals(name)){
						playerToFind = nd.getP().get(1);
						found=true;
					}
				}
			nd = nd.getNext();
		}

		return playerToFind;
	}

	/**
	 * Creates a portal between two nodes
	 * @param numLinkeds
	 */
	public void randomLinked(int numLinkeds){
		int valueMax = head.getPrev().getNum();
		NodeDL position = head;
		ArrayList<Integer> numbers =  new ArrayList<>();
		for (int i = 0; i < valueMax; i++) {
			if(position.getP().size()==0) {
				numbers.add(i);
			}
			position = position.getNext();
		}
		char letter = 'A';
		while(numLinkeds>0) {
			int x = (int) (Math.random()*numbers.size());
			int xx = numbers.get(x);
			numbers.remove(x);
			int y = (int) (Math.random()*numbers.size());
			int yy = numbers.get(y);
			numbers.remove(y);
			NodeDL prevX = head;
			NodeDL prevY = head;
			for(int i = 0; i<xx; i++) {
				prevX = prevX.getNext();
			}
			for(int i = 0; i<yy; i++) {
				prevY = prevY.getNext();
			}
			prevX.setLetter(letter+"");
			prevY.setLetter(letter+"");
			prevX.setLinked(prevY);
			prevY.setLinked(prevX);
			numLinkeds--;
			letter++;
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
		node.setLetter(" ");
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

	/**
	 * Prints the board in the form of a snake
	 * @param x defines what type of board is built, whether people with seeds, or just portals
	 * @return the String representing the layout of the board
	 */
	public String print(int x){
		String board = "";

		for (int i = 0; i < ROWS; i++) {
			String s = "";
			if (x==1){
				s = generateRow(printNode, "");
			} else{
				s= generateRowPortals(printNode, "");
			}


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


	private String generateRowPortals(NodeDL current, String s){
		if(current.getNum() % COLUMNS == 0) {
			s += "["+current.getLetter()+"] ";

			printNode = current.getNext();
		} else {
			s += "["+current.getLetter()+"] "+ generateRowPortals(current.getNext(), s);
		}
		return s;
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
	public int throwDice() {
		int total = 0;
		total += dice.getTotal();
		return total;
	}
	
	public void moveSquares(int total, String turn, boolean goBack) {
		if(!goBack) {
			moveSquares(total, head, turn);
		}else {
			moveSquaresBack(total, head, turn);
		}
	}
	
	/*
	 * moveSquares
	 * Method that move the player in the board depending on the dice
	 */
	private void moveSquares(int theDice, NodeDL node, String turn) {
		ArrayList<Player> p = node.getP();
		if(theDice == 0) {
			for(int i = 0; i < p.size(); i++) {
				if(p.get(i).getName().equals(turn)) {
					Player player = p.get(i);
					if (node.getSeed()){
						int numSeeds = player.getCollectedSeeds()+1;
						player.setCollectedSeeds(numSeeds);
						node.setSeed(false);
						totalSeeds--;
					}
					if (node.getLinked()!=null){
						node.getLinked().addP(player);
						node.getP().remove(player);
					}
				}
			}
			return;
		}else if(node.getP().size() != 0) {
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
	
	/*
	 * moveSquaresBack
	 * Method that move the player in the board depending on the dice and player decision
	 */
	private void moveSquaresBack(int theDice, NodeDL node, String turn) {
		ArrayList<Player> p = node.getP();
		if(theDice == 0) {
			for(int i = 0; i < p.size(); i++) {
				if(p.get(i).getName().equals(turn)) {
					Player player = p.get(i);
					if (node.getSeed()){
						int numSeeds = player.getCollectedSeeds()+1;
						player.setCollectedSeeds(numSeeds);
						node.setSeed(false);
						totalSeeds--;
					}
					if (node.getLinked()!=null){
						node.getLinked().addP(player);
						node.getP().remove(player);
					}
				}
			}
			return;
		}else if(node.getP().size() != 0) {
			for(int i = 0; i < p.size(); i++) {
				if(p.get(i).getName().equals(turn)) {
					Player player = p.get(i);
					node.getPrev().addP(player);
					node.getP().remove(player);
					theDice--;
				}
			}
		}
		moveSquaresBack(theDice, node.getPrev(), turn);
	}
}
