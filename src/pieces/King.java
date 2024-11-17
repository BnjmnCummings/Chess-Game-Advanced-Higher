package src.pieces;

import java.util.HashSet;

public class King extends Piece{

    public void test(){
        System.out.println("connected to king successfully");
    }

    public King(){
        moveset = new HashSet<String>();
    }

    @Override
    public void moves(int row, int col, boolean white, String[][] board, boolean king) {

        moveset.clear();

        for(int i = row-1; i<= row+1; i++){
            for(int j = col-1; j<= col+1; j++){

                if(i==row&&j==col){
                    continue;
                }
                if(i==8 || j==8 || i==-1 || j==-1){
                    continue;
                }
                if(board[i][j]!=null){
                    if((board[i][j].charAt(1)=='w') == white){
                        continue;
                    }
                }
                
                moveset.add(""+i+j);
               // System.out.println("    "+i+" "+j);
            }
        }
    }
}
