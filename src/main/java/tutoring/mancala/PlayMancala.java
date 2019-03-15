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
		String[] playerNames = new String[] {"Bill", "Jill", "Carl"};
		int pitsPerPlayer = 4;
		int stonesPerPit = 3;

		try {
			if (playerNames.length < 2) {
				System.out.println("Mancala requires at least two players!");
				return;
			}
			if (pitsPerPlayer < 1) {
				System.out.println("Mancala requires at least one pit per player!");
				return;
			}
			if (stonesPerPit < 1) {
				System.out.println("Mancala requires at least one stone per pit!");
				return;
			}
			MancalaBoard board = new MancalaBoard(playerNames, pitsPerPlayer, stonesPerPit, kbd);
			board.play();
		} finally {
			kbd.close();
			System.out.println("Thanks for playing.");
		}

	}
}
