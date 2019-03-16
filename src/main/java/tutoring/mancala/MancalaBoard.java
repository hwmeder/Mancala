package tutoring.mancala;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MancalaBoard {

	private final List<Player> players = new ArrayList<>();
	private final Player firstPlayer;

	public MancalaBoard(List<String> playerNames, int pitsPerPlayer, int stonesPerPit, Scanner kbd) {
		super();
		Collections.reverse(playerNames);
		Iterator<String> playerNameItr = playerNames.iterator();

		Player first = new Player(playerNameItr.next(), pitsPerPlayer, kbd, stonesPerPit);
		players.add(first);

		Player last = first;
		while (playerNameItr.hasNext()) {
			last = new Player(playerNameItr.next(), pitsPerPlayer, kbd, stonesPerPit).withOpponent(last);
			players.add(last);
		}

		first.withOpponent(last);
		firstPlayer = last;
	}

	public void play() {
		System.out.println("Here's the board; your pits are on the left, followed by your mancala.");

		Player player = firstPlayer;
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
		for (Player winner : winners) {
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
