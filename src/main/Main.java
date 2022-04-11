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
	 * 
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
	 * 
	 */
	public static void option(int options) {
		switch(options) {
			case 1:
				
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
	
	public static String printBoard() {
		String theBoard = "";
		theBoard = board.toPrint();
		return theBoard;
	}
}
