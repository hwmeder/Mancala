package tutoring.mancala;

import java.util.Scanner;

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

		try {
			board.play();
		} finally {
			kbd.close();
		}

		System.out.println("Thanks for playing.");
	}
}
