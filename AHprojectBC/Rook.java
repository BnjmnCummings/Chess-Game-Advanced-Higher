package AHprojectBC;

import java.util.HashSet;

public class Rook extends Piece{

    
    public Rook(){
        moveset = new HashSet<String>();
    }

    @Override  
    public void moves(int row, int col, boolean white, String[][] board, boolean king) {

        moveset.clear();

        //vertical

        for(int i = row+1;i<8;i++){
            moveset.add(""+i+col);
            if(board[i][col] != null){
                break;
            }
            
        }

        for(int i = row-1;i>=0;i--){
            moveset.add(""+i+col);
            if(board[i][col] != null){
                break;
            }
        }

        //horizontal

        for(int i = col+1;i<8;i++){
            moveset.add(""+row+i);
            if(board[row][i] != null){
                break;
            }
        }

        for(int i = col-1;i>=0; i--){
            moveset.add(""+row+i);
            if(board[row][i] != null){
                break;
            }
        }       
    }  
}
