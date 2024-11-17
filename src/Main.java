package src;


public class Main{
    public static void main(String [] args){
        
        Board myBoard = new Board(500);

        for(int i =0; i<8; i++){
            for(int j =0; j<8; j++){
                System.out.print(myBoard.game.gameBoard[i][j]);
            }
            System.out.print("\n");
        }

        System.out.println(myBoard.game.parseGame());
        //DataBaseConn.dbRead(); */
    }
}
