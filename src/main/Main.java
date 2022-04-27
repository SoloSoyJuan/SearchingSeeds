package main;

import java.util.Scanner;

import model.Board;
import model.ScoreboardData;

public class Main {
	
	public static Scanner s = new Scanner(System.in);
	public static Board board;
	public static ScoreboardData scoreboardData = new ScoreboardData();
	
	public static void main(String[] args) {
		scoreboardData.loadJSON();

		int mainSelection = 0;
		int selection = 0;
		String turn = "R";
		
		System.out.println("\nType the name of player #1 (Rick)");
		String player1 = s.nextLine();
		System.out.println("\nType the name of player #2 (Morty)");
		String player2 = s.nextLine();
		System.out.println("\nType the number of columns");
		int c = s.nextInt();
		s.nextLine();
		System.out.println("\nType the number of rows");
		int r = s.nextInt();
		s.nextLine();

		int maxPortals = (int) (0.5*(c*r));
		int portals = 0;

		do {
			System.out.println("\nType the number of portals, remember that they have to be less than " + maxPortals);
			portals = s.nextInt();
		} while (portals >= maxPortals);

		s.nextLine();
		
		board = new Board(c, r, player1, player2, portals);

		do {
			mainSelection = mainMenu();

			if (mainSelection == 1) {

				do {
					if(turn.equals("M") && selection == 1) {
						turn = "R";
					}else if(turn.equals("R") && selection == 1){
						turn = "M";
					}
					selection = gameMenu(turn);
					option(selection, turn);

				} while(selection != 0);

			} else if (mainSelection == 2) {
				System.out.println("\n" + scoreboardData.print());
				System.out.println("Press enter to continue...");
				s.nextLine();
			}
		} while (mainSelection != 0);
		


	}
	
	//----------------------------------------------------- Methods

	public static int mainMenu() {
		int selection = 0;

		System.out.println("\n-----------------------------------------------\n");
		System.out.println("(1) Start the game");
		System.out.println("(2) View Scoreboard");
		System.out.println("(0) Exit");
		System.out.println("\n-----------------------------------------------\n");

		selection = s.nextInt();
		s.nextLine();
		return selection;
	}
	
	/*
	 * mainMenu
	 * Method that show the options of the game and return the user option
	 */
	public static int gameMenu(String turn) {
		int option = 0;
		System.out.println("\n****** turn of "+turn+" ******"+
						   "\n(1) Roll dice"+
						   "\n(2) See board"+
						   "\n(3) See portals"+
						   "\n(4) See points"+
						   "\n(0) Exit the game");
		option = s.nextInt();
		s.nextLine();
		return option;
	}
	
	/*
	 * option
	 * Method that redirect to other methods depending on the user's choice
	 */
	public static void option(int options, String turn) {
		switch(options) {
			case 1:
				System.out.println("Dice: " + throwDice(turn));
				System.out.println("\nB = Both players\nM = Morty\nR = Rick\n\n" + printBoard(1));
				System.out.println("Press enter to continue...");
				s.nextLine();
				break;
			case 2:
				System.out.println("\nB = Both players\nM = Morty\nR = Rick\n\n" + printBoard(1));
				System.out.println("Press enter to continue...");
				s.nextLine();
				break;
			case 3:
				System.out.println(printBoard(2));
				System.out.println("Press enter to continue...");
				s.nextLine();
				break;
			case 4://tener player 1 y 2
				System.out.println("R: "+board.colletedSeedsPlayer("R"));
				System.out.println("M: "+board.colletedSeedsPlayer("M"));
				break;
			case 0:
				break;
			default:
				System.out.println("\nInvalid option");
				System.out.println("\nPress enter to continue...");
				s.nextLine();
				break;
		}
	}
	
	/*
	 * printBoard
	 * Method that print board status information
	 */
	public static String printBoard(int x) {
		String theBoard = "";
		theBoard = board.print(x);
		return theBoard;
	}
	
	/*
	 * throwDice
	 * Method that return a random number with the dice of the board
	 */
	public static int throwDice(String turn) {
		return board.throwDice(turn);
	}
}
