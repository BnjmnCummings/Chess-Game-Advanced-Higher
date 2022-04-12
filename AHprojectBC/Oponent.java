package AHprojectBC;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class Oponent {
    HashMap<Character, Integer> pointMap = new HashMap<Character, Integer>();

    public Oponent(){
        pointMap.put('p', 10);
        pointMap.put('k', 30);
        pointMap.put('b', 30);
        pointMap.put('r', 50);
        pointMap.put('q', 90);
        pointMap.put('x', 900);
    }
    
    //assume that Ai oponent will always play black
    public  Move AiMove(String[][] board, Vector<Move> moves){

        //for each move generate a rating

        Iterator<Move> ite = moves.iterator();

        Move currentMove;

        while(ite.hasNext()){
            currentMove = ite.next();
            //System.out.println("move: "+currentMove);// test

            generateRating(board, currentMove);
        }

        //find maximum value

        //return max move

       
        return null;

    }

    private  void generateRating(String[][] board, Move move){
        move.rating = 0;
        //add value for taking a piece
        String[][] copyBoard = new String[8][8];

        for(int i =0; i<8; i++){
            for(int j =0; j<8; j++){
                copyBoard[i][j] = board[i][j];
            }
            
        }

        //make move on copy board

        copyBoard[move.destY][move.destX] = copyBoard[move.startY][move.startX];
        copyBoard[move.startY][move.startX] = null;

        for(int i =0; i<8; i++){
            for(int j =0; j<8; j++){
                if(copyBoard[i][j] != null){
                    //take away points for every white piece and add points for every black piece
                    if(copyBoard[i][j].charAt(1)=='w'){
                        move.rating -= pointMap.get(move.takenPiece.charAt(0));
                    }else{
                        move.rating += pointMap.get(move.takenPiece.charAt(0));
                    }
                }
            }   
        }
        

        //if move causes check mate
        
        

        //conditions to add/subtract points

    }

}


