package tutoring.mancala;

import java.util.Arrays;
import java.util.Scanner;

public class Player {
	private String name;
	private int[] pits;
	private int mancala = 0;
	private Player opponent;
	private Scanner kbd;

	public Player(String string, int boardsize, Scanner kbd, int numStones) {
		this.name = string;
		this.pits = new int[boardsize];
		Arrays.fill(pits, numStones);
		this.kbd = kbd;
	}

	public int getMancala() {
		return mancala;
	}

	public Player withOpponent(Player player) {
		this.opponent = player;
		return this;
	}

	public Player getOpponent() {
		return opponent;
	}

	public boolean move() {
		int stones = -1;
		while (stones < 0) {
			if(isGameOver()) {
				return false;
			}
			displayBoard();
			int pit = getPitToEmpty();
			stones = empty(pit - 1);
			if (stones < 0) {
				continue; // Take another turn.
			}
			if(!isGameOver()) {
				return true;
			}
		}
		return false; // Game over.
	}

	private boolean isGameOver() {
		boolean gameOver = true;
		for (int pit1 : pits) {
			if (pit1 > 0) {
				gameOver = false;
			}
		}
		return gameOver;
	}

	public void displayBoard() {
		System.out.print(name.replaceAll(".", "P"));
		System.out.print(": ");
		displayPlayerLabels(this);
		System.out.println();
		System.out.print(name + ": ");
		displayPlayerPits(this);
		System.out.println();
	}

	public void sweep() {
		for (int i = 0; i < pits.length; i++) {
			mancala += pits[i];
			pits[i] = 0;
		}
	}

	private void displayPlayerLabels(Player currentPlayer) {
		StringBuilder sb = new StringBuilder();
		sb.append("<");
		for (int i = 0; i < pits.length; i++) {
			sb.append(' ');
			if (pits[i] > 9) {
				sb.append(' ');
			}
			sb.append(i + 1);
			sb.append(' ');
		}
		sb.append('>');
		sb.append(" M");
		if (mancala > 99) {
			sb.append('M');
		}
		if (mancala > 9) {
			sb.append('M');
		}
		sb.append(' ');
		System.out.print(sb.toString());
		if (currentPlayer != opponent) {
			opponent.displayPlayerLabels(currentPlayer);
		}
	}

	private void displayPlayerPits(Player currentPlayer) {
		StringBuilder sb = new StringBuilder();
		sb.append("<");
		for (int pit : pits) {
			sb.append(' ').append(pit).append(' ');
		}
		sb.append('>');
		sb.append(' ').append(mancala).append(' ');
		System.out.print(sb.toString());
		if (currentPlayer != opponent) {
			opponent.displayPlayerPits(currentPlayer);
		}
	}

	private int getPitToEmpty() {
		int pit;
		do {
			System.out.print(name + ": Which pit will you play from? ");
			pit = kbd.nextInt();
		} while (!valid(pit));
		System.out.println();
		return pit;
	}

	private boolean valid(int pit) {
		if (pit < 1 || pit > pits.length) {
			System.out.println("That is not a valid pit number. Try again.");
			return false;
		} else if (pits[pit - 1] == 0) {
			System.out.println("You can't play from an empty pit. Try again.");
			return false;
		}
		return true;
	}

	private int empty(int pit) {
		int stones = pits[pit];
		pits[pit] = 0;

		int current = pit + 1;
		while (current < pits.length && stones > 0) {
			pits[current++]++;
			stones--;
		}
		steal(stones, current);
		stones = incrementMancala(stones);
		if (stones > 0) {
			stones = opponent.spreadStones(stones, this);
		}
		return stones;
	}

	private int spreadStones(int stones, Player currentPlayer) {
		int current = 0;
		while (current < pits.length && stones > 0) {
			pits[current++]++;
			stones--;
		}
		if (this == currentPlayer) {
			steal(stones, current);
			stones = incrementMancala(stones);
		}
		if (stones > 0) {
			stones = opponent.spreadStones(stones, currentPlayer);
		}
		return stones;
	}

	private void steal(int stones, int current) {
		if (stones == 0 && current <= pits.length && pits[current - 1] == 1) {
			mancala += pits[current - 1];
			pits[current - 1] = 0;

			int targetPit = pits.length - current;
			int stolen = opponent.giveMe(targetPit);

			mancala += stolen;
			System.out.println("Stole " + stolen + " stones from pit " + (targetPit + 1) + ".");
		}
	}

	private int giveMe(int pit) {
		int stones = pits[pit];
		pits[pit] = 0;
		return stones;
	}

	private int incrementMancala(int stones) {
		if (stones > 0) {
			mancala++;
			stones--;
			if (stones == 0) {
				stones--;
			}
		}
		return stones;
	}
}
