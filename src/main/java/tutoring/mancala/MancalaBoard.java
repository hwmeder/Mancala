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

    // if one player's pits are empty, the game is over
    public static final int GAMEOVER[] = {0,0,0,0,0,0};

    // number of pits for each player
    public static final int BOARDSIZE = 6;

    // these values represent the number of 'stones' in the pits and mancalas of players 1 and 2
    // we will treat player 1 as "south" with right mancala, player 2 as "north" with left mancala
    int mancala1, mancala2;
    int[] pits1 = new int[BOARDSIZE];
    int[] pits2 = new int[BOARDSIZE];

    /**
     *
     * Sets all board values to beginning-of-game values.
     *
     */

    void initialize() {
        mancala1 = mancala2 = 0;
        Arrays.fill(pits1, 4);
        Arrays.fill(pits2, 4);
    }

    /**
     *
     * Coordinates the play of one game.
     *
     */

    void play() {
        Scanner kbd = new Scanner(System.in);
        int pit;

        System.out.println("Here's the board; your pits are on the bottom, and your mancala on the right:");
        do {

            do {    // this loop handles "extra" turns for landing in an empty pit
                display();
                do { // this loop makes sure the selected pit is valid for play
                    System.out.print("Which pit will you play from? ");
                    pit = kbd.nextInt();
                } while (!valid(pit));
            } while (playMove(pit) == 0);

        } while (getWinner() < 0);

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

    boolean valid(int pit) {
        if (pit < 1 || pit > BOARDSIZE) {
            System.out.println("That is not a valid pit number. Try again.");
            return false;
        } else if (pits1[pit-1] == 0) {
            System.out.println("You can't play from an empty pit. Try again.");
            return false;
        }
        return true;
    }

    /**
     *
     * Displays the current state of the game.
     *
     */

    void display() {
        System.out.println("   1   2   3   4   5   6  ");
        System.out.println("--------------------------");
        displayPitsNorth(pits2);

        System.out.println();
        System.out.format("%4d", mancala2);
        System.out.print("                ");
        System.out.format("%4d", mancala1);
        System.out.println();
        System.out.println();
        displayPitsSouth(pits1);
        System.out.println("--------------------------");
        System.out.println();
    }

    /**
     *
     * Displays the number of stones in the pits of the "south" player.
     *
     */

    void displayPitsSouth(int[] pits) {
        for (int i=0; i<pits.length; i++) {
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
        for (int i=BOARDSIZE-1; i>=0; i--) {
            System.out.format("%4d", pits[i]);
        }
        System.out.println();
    }

    /**
     *
     * Processes one play of player 1
     *
     * @param pit number to play from
     * @return 0 if another turn is warranted; -1 if problems arose; positive value otherwise
     *
     */

    int playMove(int pit) {

        int stones = pits1[pit-1];  // number of stones to drop
        pits1[pit-1] = 0;           // clear starting pit
        int last = 0;               // number of stones in last pit played (0 means play again)


        /* in the unlikely (impossible?) case that the pit contains enough stones
         * to go around more than once, distribute the "full laps" first */

        int numPits = 2*BOARDSIZE + 2;      // 2 players' pits and mancalas
        if (stones > numPits) {
            incrementAll(stones / numPits);
            stones = stones % numPits;      // what's left
        }

        /* play the remaining stones:
         * in the maximum case, there will be enough stones remaining to deposit on player 1's side,
         * player 1's mancala, player 2's side, player 2's mancala, and then back to player 1 */

        /* play stones on player 1's side */
        while (stones > 0 & pit < BOARDSIZE) {
            last = pits1[pit];
            pits1[pit]++;
            pit++;
            stones--;
        }

        /* play stone in player 1's mancala, if any */

        if (stones != 0) {
            last = mancala1;
            mancala1++;
            stones--;
        }

        /* play any remaining stones on player 2's side */
        /* note that array indices follow direction of play */

        pit = 0;
        while (stones > 0 & pit < BOARDSIZE) {
            last = pits2[pit];
            pits2[pit]++;
            pit++;
            stones--;
        }

        if (stones != 0) {
            last = mancala2;
            mancala2++;
            stones--;
        }

        /* possibly, there are enough stones to get back around to player 1 */

        pit = 0;
        while (stones > 0 & pit < BOARDSIZE) {
            last = pits1[pit];
            pits1[pit]++;
            pit++;
            stones--;
        }

        /* when out of stones, check to see status of last pit played */

        if (stones == 0) {
            if (last == 0) {
                System.out.println("Take another turn!");
                return 0;   // take another turn
            }
            else
                return pit;
        } else {
            System.out.println("I didn't expect to have stones left. Programmer fail.");
            return -1;
        }
    }

    /**
     *
     * Add n stones to each pit and mancala.
     *
     * @param n The number of stones to add.
     *
     **/

    void incrementAll(int n) {
        for (int i=0; i<BOARDSIZE; i++) {
            pits1[i] += n;
            pits2[i] += n;
        }
        mancala1 += n;
        mancala2 += n;
    }

    /**
     *
     * if game is over, return 1 or 2 to indicate winning player or 0 if tie; if not over return -1.
     * @return 1 or 2 to indicate winning player, 0 to indicate tie, or -1 if game not over.
     *
     **/

    int getWinner() {
        if ((Arrays.compare(pits1, GAMEOVER) == 0) || (Arrays.compare(pits2,GAMEOVER) == 0)) {
            sweep();
            if (mancala1 > mancala2)
                return 1;
            else if (mancala2 > mancala1)
                return 2;
            else
                return 0;
        } else
            return -1;
    }

    /**
     *
     * At endgame, player with remaining stones 'captures' them into her mancala.
     *
     **/

    void sweep() {
        for (int i=0; i<BOARDSIZE; i++) {
            mancala1 += pits1[i];
            mancala2 += pits2[i];
            pits1[i] = pits2[i] = 0;
        }
    }
}

