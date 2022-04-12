package AHprojectBC;

public class Move{
    
    public String piece;
    public int startY;
    public int startX;

    public int destY;
    public int destX;

    public int rating;

    public String takenPiece;

    public Move(int row, int col, int newRow, int newCol, String pce, String tknPiece){
        piece = pce;
        takenPiece = tknPiece;

        startY = row;
        startX = col;
        
        destY = newRow;
        destX = newCol;

        rating = 0;
    }
}
