package tutoring.mancala;

import java.util.Scanner;

public class MancalaBoard {

	// number of pits for each player
	public static final int BOARDSIZE = 6;

	// if one player's pits are empty, the game is over
	public static final int GAMEOVER[] = new int[BOARDSIZE];

	private Player[] players;

	public MancalaBoard(Scanner kbd) {
		super();
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
		do { // this loop handles "extra" turns for landing in an empty pit
		} while (players[(player++) % 2].move());

		int winner = getWinner();

		System.out.println("The winner is " + winner);
		System.out.print(winner + ": ");
		players[winner % 2].display();
		players[(winner + 1) % 2].display();
		System.out.println();
	}

	/**
	 * @return return 0 or 1 to indicate winning player or -1 if tie
	 **/

	private int getWinner() {
		for (Player player : players) {
			player.sweep();
		}
		int mancala1 = players[0].getMancala();
		int mancala2 = players[1].getMancala();

		if (mancala1 > mancala2) {
			return 0;
		} else if (mancala2 > mancala1) {
			return 1;
		}
		return -1;
	}

}
