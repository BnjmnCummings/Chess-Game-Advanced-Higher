package AHprojectBC;

import java.util.HashSet;

public class Knight extends Piece{

    public void test(){
        System.out.println("connected to king successfully");
    }

    public Knight(){
        moveset = new HashSet<String>();
    }

    @Override
    public void moves(int row, int col, boolean white, String[][] board, boolean king) {

        moveset.clear();

        int up = row-2;
        int down = row+2;
        int left = col-2;
        int right = col+2;

        if(row<6){
            if(col<7){
                moveset.add(""+down+(col+1));
            }
            if(col>0){
                moveset.add(""+down+(col-1));
            }
        }

        if(col<6){
            if(row<7){
                moveset.add(""+(row+1)+right);
            }
            if(row>0){
                moveset.add(""+(row-1)+right);
            }
        }

        if(row>1){
            if(col<7){
                moveset.add(""+up+(col+1));
            }
            if(col>0){
                moveset.add(""+up+(col-1));
            }
        }

        if(col>1){
            if(row<7){
                moveset.add(""+(row+1)+left);
            }
            if(row>0){
                moveset.add(""+(row-1)+left);
            }
        }
    }
}
