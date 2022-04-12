package AHprojectBC;

import java.util.HashSet;

abstract class Piece {

    public HashSet<String> moveset;
    public abstract void moves(int row, int col, boolean white, String[][] board, boolean king);
    
}
