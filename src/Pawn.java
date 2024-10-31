package src;

import java.util.HashSet;


public class Pawn extends Piece{

    public Pawn(){
        moveset = new HashSet<String>();
    }

    @Override
    public void moves(int row, int col, boolean white, String[][] board, boolean king){

        moveset.clear();

        if(king){
            //to check if its attacking the king only add attacking moveset
            if(white){
                //adds diagonals
                if(col!= 0){
                    moveset.add(""+(row-1)+(col-1));
                }
                if(col!= 7){
                    moveset.add(""+(row-1)+(col+1));
                }
                
            }else{
                if(col!= 0){
                    moveset.add(""+(row+1)+(col-1));
                }
                if(col!= 7){
                    moveset.add(""+(row+1)+(col+1));
                }
    
            }

        }

        //normally just add both sets
        else{
            if(white){
                //cannot take via standard moveset
                if(board[row-1][col]==null){//only if square ahead is empty
                    moveset.add(""+(row-1)+col);
                }
                //starting row
                if(row==6){
                    if(board[4][col]==null && board[5][col] == null){
                        moveset.add("4"+col);
                    }
                    
                }

                //taking

                if(col!= 0){//to take left diagonal
                    if(board[row-1][col-1]!=null){//only if a piece is alreay there
                        moveset.add(""+(row-1)+(col-1));
                    }
                }
                if(col!= 7){//to tqke right diagonal
                    if(board[row-1][col+1]!=null){
                        moveset.add(""+(row-1)+(col+1));
                    }
                }
    
            }
            //for black pieces
            else{

                if(board[row+1][col]==null){
                    moveset.add(""+(row+1)+col);
                }
                //starting row
                if(row==1){
                    if(board[3][col]==null && board[2][col] == null){
                        moveset.add("3"+col);
                    }
                }

                //taking 

                if(col!= 0){
                    if(board[row+1][col-1]!=null){
                        moveset.add(""+(row+1)+(col-1));
                    }
                }
                if(col!= 7){
                    if(board[row+1][col+1]!=null){
                        moveset.add(""+(row+1)+(col+1));
                    }
                }
            }
        }        
    }
}
