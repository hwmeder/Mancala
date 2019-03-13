package tutoring.mancala;

import java.util.Arrays;

public class Player {

	private int mancala = 0;
	private int[] pits;
	private Player opponent;

	public Player(int boardsize) {
		pits = new int[boardsize];
		Arrays.fill(pits, 4);
	}

	public int getMancala() {
		return mancala;
	}

	public int[] getPits() {
		return pits;
	}

	public int incrementMancala(int stones) {
		if (stones > 0) {
			mancala++;
			stones--;
			if (stones == 0) {
				stones--;
			}
		}
		return stones;
	}

	public void sweep() {
		for (int i = 0; i < pits.length; i++) {
			mancala += pits[i];
			pits[i] = 0;
		}
	}

	/**
	 *
	 * Verifies validity of selected pit--checking both that it's in range and that
	 * the selected pit has stones to play.
	 *
	 * @param pit The number of the selected pit
	 * @return true if selected pit is viable to play from
	 *
	 */
	public boolean valid(int pit) {
		if (pit < 1 || pit > pits.length) {
			System.out.println("That is not a valid pit number. Try again.");
			return false;
		} else if (pits[pit - 1] == 0) {
			System.out.println("You can't play from an empty pit. Try again.");
			return false;
		}
		return true;
	}

	public int empty(int pit) {
		int stones = pits[pit];
		pits[pit] = 0;

		int current = pit + 1;
		while (current < pits.length && stones > 0) {
			pits[current++]++;
			stones--;
		}
		steal(stones, current);
		return incrementMancala(stones);
	}

	public int spreadTheirStones(int stones) {
		int current = 0;
		while (current < pits.length && stones > 0) {
			pits[current++]++;
			stones--;
		}
		return stones;
	}

	public int spreadMyStones(int stones) {
		int current = 0;
		while (current < pits.length && stones > 0) {
			pits[current++]++;
			stones--;
		}
		steal(stones, current);
		return incrementMancala(stones);
	}

	private void steal(int stones, int current) {
		if (stones == 0 && current <= pits.length && pits[current-1] == 1) {
			mancala += pits[current - 1];
			pits[current - 1] = 0;
			mancala += opponent.giveMe(pits.length - current);
		}
	}

	private int giveMe(int pit) {
		int stones = pits[pit];
		pits[pit] = 0;
		return stones;
	}

	public void setOpponent(Player player) {
		this.opponent = player;
	}
}
