package main;

import java.util.Scanner;

import model.Board;

public class Main {
	
	public static Scanner s = new Scanner(System.in);
	public static Board board;
	
	public static void main(String[] args) {
		int selection;
		
		System.out.println("\nType the number of columns");
		int c = s.nextInt();
		s.nextLine();
		System.out.println("\nType the number of rows");
		int r = s.nextInt();
		s.nextLine();
		
		board = new Board(c, r);
		
		do {
			selection = mainMenu();
			option(selection);
			
		}while(selection != 0);

	}
	
	//----------------------------------------------------- Methods
	
	/*
	 * mainMenu
	 * Method that show the options of the game and return the user option
	 */
	public static int mainMenu() {
		int option = 0;
		System.out.println("\n****** ******"+
						   "\n(1) Roll dice"+
						   "\n(2) See board"+
						   "\n(0) Exit");
		option = s.nextInt();
		s.nextLine();
		return option;
	}
	
	/*
	 * option
	 * Method that redirect to other methods depending on the user's choice
	 */
	public static void option(int options) {
		switch(options) {
			case 1:
				System.out.println(throwDice());
				break;
			case 2:
				System.out.println(printBoard());
				break;
			case 0:
				break;
			default:
				System.out.println("\nNot valid option");
				break;
		}
	}
	
	/*
	 * printBoard
	 * Method that print board status information
	 */
	public static String printBoard() {
		String theBoard = "";
		theBoard = board.toPrint();
		return theBoard;
	}
	
	/*
	 * throwDice
	 * Method that return a random number with the dice of the board
	 */
	public static String throwDice() {
		return board.throwDice();
	}
}
