package tutoring.mancala;

import java.util.Scanner;

/**
 *
 * Main program for a mancala game. The MancalaBoard class contains all the game
 * logic.
 *
 * @author Scott Dexter
 * @version 1.1 2/26/2019
 *
 */

public class PlayMancala {

	/**
	 *
	 * Main method to start game.
	 *
	 * @param args unused
	 *
	 */

	public static void main(String[] args) {
		Scanner kbd = new Scanner(System.in);
		MancalaBoard board = new MancalaBoard(kbd);

		System.out.println("Welcome to Mancala!");

		String input;
		try {
			board.play();
		} finally {
			kbd.close();
		}

		System.out.println("Thanks for playing.");
	}
}
