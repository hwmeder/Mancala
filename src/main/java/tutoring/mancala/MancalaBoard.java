package tutoring.mancala;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class MancalaBoard {

	private static final int NUMBER_OF_STONES_PER_PIT = 3;
	private static final int NUMBER_OF_PITS_PER_PLAYER = 4;
	private static final Iterator<String> PLAYER_NAMES = Arrays.asList(new String[] {"Bill", "Jill", "Carl"}).iterator();

	private Set<Player> players = new HashSet<>();

	public MancalaBoard(Scanner kbd) {
		super();

		Player first = new Player(PLAYER_NAMES.next(), NUMBER_OF_PITS_PER_PLAYER, kbd, NUMBER_OF_STONES_PER_PIT);
		players.add(first);

		Player last = first;
		while (PLAYER_NAMES.hasNext()) {
			last = new Player(PLAYER_NAMES.next(), NUMBER_OF_PITS_PER_PLAYER, kbd, NUMBER_OF_STONES_PER_PIT).withOpponent(last);
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

		sweep();
		displayResults(getWinners());
	}

	private void sweep() {
		for (Player player : players) {
			player.sweep();
		}
	}

	private void displayResults(Set<Player> winners) {
		System.out.println("Winners:");
		for(Player winner : winners) {
			winner.displayBoard();
		}
	}

	private Set<Player> getWinners() {
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
