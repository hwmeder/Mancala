package tutoring.mancala;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PlayMancala {

	private static final List<String> DEFAULT_playerNames = Arrays.asList(new String[] { "Bill", "Jill", "Carl" });
	private static final int DEFAULT_stonesPerPit = 3;
	private static final int DEFAULT_pitsPerPlayer = 4;

	/**
	 *
	 * Main method to start game.
	 *
	 * @param args unused
	 *
	 */

	public static void main(String[] args) {
		Iterator<String> argsItr = Arrays.asList(args).iterator();

		int pitsPerPlayer;
		if (argsItr.hasNext()) {
			String arg = argsItr.next();
			try {
				pitsPerPlayer = Integer.parseInt(arg);
			} catch (Exception e) {
				System.out.println(arg + " is not a valid number of pits per player!\n" + e.getMessage());
				displayHelp();
				return;
			}
		} else {
			pitsPerPlayer = DEFAULT_pitsPerPlayer;
		}

		int stonesPerPit;
		if (argsItr.hasNext()) {
			String arg = argsItr.next();
			try {
				stonesPerPit = Integer.parseInt(arg);
			} catch (Exception e) {
				System.out.println(arg + " is not a valid number of stones per pit!\n" + e.getMessage());
				displayHelp();
				return;
			}
		} else {
			stonesPerPit = DEFAULT_stonesPerPit;
		}

		List<String> playerNames;
		if (argsItr.hasNext()) {
			playerNames = new ArrayList<>();
			while (argsItr.hasNext()) {
				playerNames.add(argsItr.next());
			}
		} else {
			playerNames = DEFAULT_playerNames;
		}

		Scanner kbd = new Scanner(System.in);
		try {
			if (pitsPerPlayer < 1) {
				System.out.println("Mancala requires at least one pit per player!");
				displayHelp();
				return;
			}
			if (stonesPerPit < 1) {
				System.out.println("Mancala requires at least one stone per pit!");
				displayHelp();
				return;
			}
			if (playerNames.size() < 2) {
				System.out.println("Mancala requires at least two players!");
				displayHelp();
				return;
			}

			MancalaBoard board = new MancalaBoard(playerNames, pitsPerPlayer, stonesPerPit, kbd);
			board.play();
		} finally {
			kbd.close();
			System.out.println("Thanks for playing Mancala.");
		}

	}

	private static void displayHelp() {
		System.out.println(PlayMancala.class.getSimpleName() +
				" <number of pits per player> <number of stones per pit> <player names>\n" +
				"Missing arguments will be defaulted.\n" +
				"Defaults: " + DEFAULT_pitsPerPlayer + " " + DEFAULT_stonesPerPit + " " +
				DEFAULT_playerNames.stream().collect(Collectors.joining(" ")) + "\n" +
				"number of pits per player must be at least one\n" +
				"number of stones per pit must be at least one\n" +
				"number of player names must be at least two");
	}
}
