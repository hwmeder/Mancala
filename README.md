# Multi-Player Mancala
    This version of Mancala allows the game to be configured for 2 or more players.
    The Mancala board can be configured with 1 or more pits per player.
    The pits can be initialized with 1 or more stones per pit.
    Of course, each player has one Mancala located after the player's pits.
    https://endlessgames.com/wp-content/uploads/Mancala_Instructions.pdf
## Help
    PlayMancala <number of pits per player> <number of stones per pit> <player names>
    Missing arguments will be defaulted.
    Defaults: 4 3 Bill Jill Carl
    number of pits per player must be at least one
    number of stones per pit must be at least one
    number of player names must be at least two
## Sample output
    Here's the board; your pits are on the left, followed by your mancala.
    PPPP: < 1  2  3  4 > M < 1  2  3  4 > M < 1  2  3  4 > M 
    Bill: < 3  3  3  3 > 0 < 3  3  3  3 > 0 < 3  3  3  3 > 0 
    Bill: Which pit will you play from? 1

    PPPP: < 1  2  3  4 > M < 1  2  3  4 > M < 1  2  3  4 > M 
    Jill: < 3  3  3  3 > 0 < 3  3  3  3 > 0 < 0  4  4  4 > 0 
    Jill: Which pit will you play from? 1

    PPPP: < 1  2  3  4 > M < 1  2  3  4 > M < 1  2  3  4 > M 
    Carl: < 3  3  3  3 > 0 < 0  4  4  4 > 0 < 0  4  4  4 > 0 
    Carl: Which pit will you play from? 1
    
    PPPP: < 1  2  3  4 > M < 1  2  3  4 > M < 1  2  3  4 > M 
    Bill: < 0  4  4  4 > 0 < 0  4  4  4 > 0 < 0  4  4  4 > 0 
    Bill: Which pit will you play from? 
