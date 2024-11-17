package src.pieces;

import java.util.HashSet;

public class Queen extends Piece{

    public Queen(){
        moveset = new HashSet<String>();
    }

    @Override
    public void moves(int row, int col, boolean white, String[][] board, boolean king) {

        moveset.clear();

        //diagonals

        for(int i =row+1, j= col+1; i<8 && j<8; i++, j++){
            if(board[i][j] != null){
                moveset.add(""+i+j);
                break;
            }
            moveset.add(""+i+j);
        }

        for(int i =row+1, j= col-1; i<8 && j>=0; i++, j--){        
            if(board[i][j] != null){
                moveset.add(""+i+j);
                break;
            }
            moveset.add(""+i+j);
        }

        for(int i =row-1, j= col-1; i>=0 && j>=0; i--, j--){           
            if(board[i][j] != null){
                moveset.add(""+i+j);
                break;
            }
            moveset.add(""+i+j);
        }

        for(int i =row-1, j= col+1; i>=0 && j<8; i--, j++){           
            if(board[i][j] != null){
                moveset.add(""+i+j);
                break;
            }
            moveset.add(""+i+j);
        }

        //horizontal and vertical

        for(int i = row+1;i<8;i++){
            if(board[i][col] != null){
                moveset.add(""+i+col);
                break;
            }
            moveset.add(""+i+col);
        }

        for(int i = row-1;i>=0;i--){
            if(board[i][col] != null){
                moveset.add(""+i+col);
                break;
            }
            moveset.add(""+i+col);
        }

        for(int i = col+1;i<8;i++){
            if(board[row][i] != null){
                moveset.add(""+row+i);
                break;
            }
            moveset.add(""+row+i);
        }
        
        for(int i = col-1;i>=0; i--){
            if(board[row][i] != null){
                moveset.add(""+row+i);
                break;
            }
            moveset.add(""+row+i);
        }   
    }
}
    