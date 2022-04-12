package AHprojectBC;
import java.util.Vector;

public class Oponent {
    //assume that Ai oponent will always play black
    public static Vector<Move> moveList(String[][] board){
       /*  for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(board[i][j] != null){
                    //only look at similarly coloured pieces to colour under attack
                    if(board[i][j].charAt(1)=='b'){    
                        //System.out.println(board[i][j]);
                        //generate its moveset
                        game.pieceMap.get(this.gameBoard[i][j].charAt(0))[1].moves(i,j, (this.gameBoard[i][j].charAt(1)=='w'), this.gameBoard, false);

                        //for each move in their moveset...
                        Iterator<String> ite = pieceMap.get(this.gameBoard[i][j].charAt(0))[1].moveset.iterator();

                        String move;

                        while(ite.hasNext()){
                            move = (String) ite.next();
                            System.out.println(move);

                            //cannot simulate a piece taking a king
                            //
                            if(this.gameBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]!=null){
                                if(this.gameBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))].charAt(0)=='x'){
                                    continue;
                                }
                            } 
                            
                            //pass in false as we dont want to generate the moveset twice
                            if(legalMove(i, j, Character.getNumericValue(move.charAt(0)), Character.getNumericValue(move.charAt(1)), this.gameBoard[i][j], false)){
                                return false;
                            }
                        }
                    }
                }
            }
        } */
        return null;

    }
}
