package main;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

import model.Board;
import model.Player;
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
		
		int seeds = 0;
		maxPortals -= 2;
		do {
			System.out.println("\nType the number of seeds, remember that they have to be more than " + maxPortals);
			seeds = s.nextInt();
		} while (seeds < 1 && seeds >= maxPortals);

		s.nextLine();

		do {
			board = new Board(c, r, player1, player2, portals, seeds);
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

					// Check the winning condition
					if(!stillSeeds()) {
						System.out.println("Game over: Winner is " + theWinner());
						System.out.println("\nPress enter to continue...");
						s.nextLine();
						selection = 0;
					}

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
		Instant startOfTurn = Instant.now();

		int option = 0;
		System.out.println("\n****** turn of "+turn+" ******"+
						   "\n(1) Roll dice"+
						   "\n(2) See board"+
						   "\n(3) See portals"+
						   "\n(4) See points"+
						   "\n(0) Exit the game");
		option = s.nextInt();
		s.nextLine();

		long secondsOfTurn = ChronoUnit.SECONDS.between(startOfTurn, Instant.now());
		Player actualPlayer;

		// Calculate the seconds played at the moment
		if (turn.equals("R")) {
			actualPlayer = board.findPlayer("R");
			actualPlayer.setSecondsPlayed(actualPlayer.getSecondsPlayed() + secondsOfTurn);
		} else {
			actualPlayer = board.findPlayer("M");
			actualPlayer.setSecondsPlayed(actualPlayer.getSecondsPlayed() + secondsOfTurn);
		}

		return option;
	}
	
	/*
	 * option
	 * Method that redirect to other methods depending on the user's choice
	 */
	public static void option(int options, String turn) {
		switch(options) {
			case 1:
				int moves = throwDice();
				System.out.println("Dice: "+ moves);
				System.out.println("where to go (1)-forward (2)-backward");
				int goBack = s.nextInt();
				s.nextLine();
				boolean go = false;
				if(goBack != 1) {
					go = true;
				}
				moveSquares(moves, go, turn);
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
			case 4:
				System.out.println("R: "+board.findPlayer("R").getCollectedSeeds());
				System.out.println("M: "+board.findPlayer("M").getCollectedSeeds());
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
	public static int throwDice() {
		return board.throwDice();
	}
	
	public static void moveSquares(int moves, boolean goBack, String turn) {
		board.moveSquares(moves, turn, goBack);
	}
	
	public static boolean stillSeeds() {
		int seeds = board.getTotalSeeds();
		boolean thereAre = false;
		if(seeds > 0) {
			thereAre = true;
		}
		return thereAre;
	}
	
	public static String theWinner() {
		String name = "";
		Player player1 = board.findPlayer("R");
		Player player2 = board.findPlayer("M");
		int scoreP1 = 0;
		int scoreP2 = 0;

		scoreP1 = (player1.getCollectedSeeds() * 120) - (int) player1.getSecondsPlayed();
		scoreP2 = (player2.getCollectedSeeds() * 120) - (int) player2.getSecondsPlayed();

		player1.setScore(scoreP1);
		player2.setScore(scoreP2);

		// Checks the collected amount of seeds to determine the winner
		if (player1.getCollectedSeeds() == player2.getCollectedSeeds()) {

			// Tiebreaker by score
			if (scoreP1 > scoreP2) {
				// Adds to scoreboard and gets data
				name = "Rick | " + player1.getPName() + " | Score: " + scoreP1;
				scoreboardData.addPlayer(player1);
			} else {
				// Adds to scoreboard and gets data
				name = "Morty | " + player2.getPName() + " | Score: " + scoreP2;
				scoreboardData.addPlayer(player2);
			}
		} else if(player1.getCollectedSeeds() < player2.getCollectedSeeds()) {
			// Adds to scoreboard and gets data
			name = "Morty | " + player2.getPName() + " | Score: " + scoreP2;
			scoreboardData.addPlayer(player2);
		}else {
			// Adds to scoreboard and gets data
			name = "Rick | " + player1.getPName() + " | Score: " + scoreP1;
			scoreboardData.addPlayer(player1);
		}

		return name;
	}
}
