package AHprojectBC;

public class Main{
    public static void main(String [] args){
        //Test github commit 3
        
        Board myBoard = new Board(500);

        //TEST verify correct piece placement
        for(int i =0; i<8; i++){
            for(int j =0; j<8; j++){
                System.out.print(myBoard.game.gameBoard[i][j]);
            }
            System.out.print("\n");
        }
        //TEST methods

        System.out.println(myBoard.game.parseGame());
        //DataBaseConn.dbRead(); */

       /*  */

        //TEST verify correct piece placement
    
        

        /* Game testGame = new Game();

        for(int i =0; i<8; i++){
            for(int j =0; j<8; j++){
                System.out.print(testGame.gameBoard[i][j]);
            }
            System.out.print("\n");
        }
        Game testStringRep = new Game("rb1/kb1/bb1/qb0/xb0/bb0/kb0/rb0/pb0/pb1/pb2/pb3/pb4/pb5/pb6/pb7/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/pw0/pw1/pw2/pw3/pw4/pw5/pw6/pw7/rw1/kw1/bw1/qw1/xw1/bw0/kw0/rw0/", 
                                        true);
        for(int i =0; i<8; i++){
            for(int j =0; j<8; j++){
                System.out.print(testStringRep.gameBoard[i][j]);
            }
            System.out.print("\n");
        }  */

        

        /* for(int i =0; i<8; i++){
            for(int j =0; j<8; j++){
                myBoard.game.gameBoard[i][j] = null;
            }
        }
        // place test piece
        String testPiece = "kw0";
        myBoard.game.gameBoard[0][0] = testPiece;

        Knight knight = new Knight();
        knight.moves(0, 0, true, myBoard.game.gameBoard, false);
        

        System.out.println("knight moveset");
        for(String move : knight.moveset){
            System.out.println(move);
            myBoard.game.gameBoard[Character.getNumericValue(move.charAt(0))]
            [Character.getNumericValue(move.charAt(1))] = " X  ";
        }

        System.out.println("GameBoard: ");

        for(int i =0; i<8; i++){
            for(int j =0; j<8; j++){
                System.out.print(myBoard.game.gameBoard[i][j]);
            }
            System.out.print("\n");
        } */



        /* JFrame frame = new JFrame("test");
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true); */




       
    }
}
