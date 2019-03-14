package tutoring.mancala;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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

	public void play() {
		System.out.println("Here's the board; your pits are on the left, followed by your mancala.");
		int player = 0;
		do { // this loop handles "extra" turns for landing in an empty pit
		} while (players[(player++) % 2].move());

		Set<Player> winners = getWinners();

		System.out.println("The winner is " + winners);
		System.out.print(winners + ": ");
		int winner = winners.iterator().next().getId();
		players[winner % 2].display(players[winner % 2]);
		System.out.println();
	}

	/**
	 * @return return 0 or 1 to indicate winning player or -1 if tie
	 **/

	private Set<Player> getWinners() {
		for (Player player : players) {
			player.sweep();
		}
		int maxMancala = 0;
		Set<Player> winners = new HashSet<>();
		for (Player player : players) {
			if (player.getMancala() > maxMancala) {
				winners.clear();
				winners.add(player);
				maxMancala = player.getMancala();
			} else if (player.getMancala() == maxMancala) {
				winners.add(player);
			}
		}
		return winners;
	}

}
