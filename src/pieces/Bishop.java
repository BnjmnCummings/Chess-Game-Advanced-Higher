package src.pieces;

import java.util.HashSet;

public class Bishop extends Piece{

    public Bishop(){
        moveset = new HashSet<String>();
    }

    @Override
    public void moves(int row, int col, boolean white, String[][] board, boolean king) {

        moveset.clear();
        
        for(int i =row+1, j= col+1; i<8 && j<8; i++, j++){
            moveset.add(""+i+j);
            if(board[i][j] != null){
                break;
            }          
        }

        for(int i =row+1, j= col-1; i<8 && j>=0; i++, j--){   
            moveset.add(""+i+j);       
            if(board[i][j] != null){
                break;
            }
        }

        for(int i =row-1, j= col-1; i>=0 && j>=0; i--, j--){  
            moveset.add(""+i+j);         
            if(board[i][j] != null){
                break;
            }
        }
        for(int i =row-1, j= col+1; i>=0 && j<8; i--, j++){
            moveset.add(""+i+j);
            if(board[i][j] != null){
                break;
            }
        }
    }
}
