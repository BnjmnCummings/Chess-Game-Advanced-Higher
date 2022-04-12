package AHprojectBC;
import java.util.Iterator;
import java.util.Vector;

public class Oponent {
    //assume that Ai oponent will always play black
    public static Move AiMove(String[][] board, Vector<Move> moves){

        //for each move generate a rating

        Iterator<Move> ite = moves.iterator();

        Move currentMove;

        while(ite.hasNext()){
            currentMove = ite.next();
            System.out.println("move: "+currentMove);
        }

        //find maximum value

        //return max move

       
        return null;

    }

    private static void generateRating(Move move){

    }

}


