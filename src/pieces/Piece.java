package src.pieces;

import java.util.HashSet;

public abstract class Piece {

    public HashSet<String> moveset;
    public abstract void moves(int row, int col, boolean white, String[][] board, boolean king);
    
}
