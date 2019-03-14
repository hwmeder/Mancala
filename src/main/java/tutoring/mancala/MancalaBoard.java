package tutoring.mancala;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * Game logic for mancala.
 *
 * @author Scott Dexter
 * @version 1.1 2/26/2019
 *
 */

public class MancalaBoard {

	// number of pits for each player
	public static final int BOARDSIZE = 6;

	// if one player's pits are empty, the game is over
	public static final int GAMEOVER[] = new int[BOARDSIZE];

	Player[] players;

	private Scanner kbd;

	public MancalaBoard(Scanner kbd) {
		super();
		this.kbd = kbd;
		players = new Player[] {
				new Player(0, BOARDSIZE, kbd),
				new Player(1, BOARDSIZE, kbd)
		};
		players[0].setOpponent(players[1]);
		players[1].setOpponent(players[0]);
	}

	void play() {

		System.out.println("Here's the board; your pits are on the left, followed by your mancala.");
		int player = 0;
		int pit = -1;
		do { // this loop handles "extra" turns for landing in an empty pit
		} while (players[(player++) % 2].move());
		int winner = getWinner();
		System.out.println("The winner is " + winner);
		System.out.print(winner + ": ");
		players[winner].display();
		players[(winner + 1) % 2].display();
		System.out.println();
	}

	/**
	 *
	 * Displays the number of stones in the pits of the "south" player.
	 *
	 */

	void displayPitsSouth(int[] pits) {
		for (int i = 0; i < pits.length; i++) {
			System.out.format("%4d", pits[i]);
		}
		System.out.println();
	}

	/**
	 *
	 * Displays the number of stones in the pits of the "north" player.
	 *
	 */

	void displayPitsNorth(int[] pits) {
		for (int i = BOARDSIZE - 1; i >= 0; i--) {
			System.out.format("%4d", pits[i]);
		}
		System.out.println();
	}

	/**
	 *
	 * if game is over, return 1 or 2 to indicate winning player or 0 if tie; if not
	 * over return -1.
	 * 
	 * @return 1 or 2 to indicate winning player, 0 to indicate tie, or -1 if game
	 *         not over.
	 *
	 **/

	int getWinner() {
		if ((Arrays.equals(players[0].getPits(), GAMEOVER)) || (Arrays.equals(players[1].getPits(), GAMEOVER))) {
			sweep();
			int mancala1 = players[0].getMancala();
			int mancala2 = players[1].getMancala();

			if (mancala1 > mancala2)
				return 0;
			else if (mancala2 > mancala1)
				return 1;
			else
				return 2;
		} else
			return -1;
	}

	/**
	 *
	 * At endgame, player with remaining stones 'captures' them into her mancala.
	 *
	 **/

	void sweep() {
		for (Player player : players) {
			player.sweep();
		}
	}

}
