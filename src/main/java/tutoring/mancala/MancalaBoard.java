package tutoring.mancala;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MancalaBoard {

	private static final int NUMBER_OF_STONES_PER_PIT = 3;
	// number of pits for each player
	public static final int BOARDSIZE = 4;
	public static final int NUMBER_OF_PLAYERS = 3;

	private Set<Player> players = new HashSet<>();

	public MancalaBoard(Scanner kbd) {
		super();
		Player first = new Player("Player1", BOARDSIZE, kbd, NUMBER_OF_STONES_PER_PIT);
		players.add(first);

		Player last = first;

		for (int i = 2; i <= NUMBER_OF_PLAYERS; i++) {
			last = new Player("Player" + i, BOARDSIZE, kbd, NUMBER_OF_STONES_PER_PIT).withOpponent(last);
			players.add(last);
		}

		first.withOpponent(last);
	}

	public void play() {
		System.out.println("Here's the board; your pits are on the left, followed by your mancala.");
		Player player = players.iterator().next();
		while (player.move()) {
			player = player.getOpponent();
		}

		reportResults();
	}

	private void reportResults() {
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

		System.out.println("The winner is " + winners);
		System.out.print(winners + ": ");
		Player winner = winners.iterator().next();
		winner.display(winner);
		System.out.println();
	}

}
