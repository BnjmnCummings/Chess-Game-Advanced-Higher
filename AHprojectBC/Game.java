package AHprojectBC;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;
public class Game {

    public String [][] gameBoard;

    //used for generating future moves - copies current board with out damaging its value
    private String [][] copyBoard;
    private String [][] copyBoardBlkMove;
    private String [][] copyBoardWhtMove;

    public boolean whiteTurn;
    public boolean whiteCheck;
    public boolean blackCheck;

    private HashMap<Character, Piece[]> pieceMap;
    HashMap<Character, Integer> pointMap = new HashMap<Character, Integer>();

    //set of taken pieces
    private HashSet<String> takeSet;

    //for keeping track of moves
    public Stack<Move> moveStack = new Stack<Move>();
    public Stack<Move> redoStack = new Stack<Move>();

    public final int LENGTH = 8;

    public Game(){

        gameBoard = new String[LENGTH][LENGTH];
        copyBoard = new String[LENGTH][LENGTH];
        copyBoardBlkMove = new String[LENGTH][LENGTH];
        copyBoardWhtMove = new String[LENGTH][LENGTH];

        pointMap.put('p', 10);
        pointMap.put('k', 30);
        pointMap.put('b', 30);
        pointMap.put('r', 50);
        pointMap.put('q', 90);
        pointMap.put('x', 900);

        whiteTurn = true;
        whiteCheck = false;
        blackCheck = false;
        pieceMap = new HashMap<Character, Piece[]>();
        takeSet = new HashSet<String> ();

        //place pieces

            //pawns
            for(int i=0; i<LENGTH; i++){
                gameBoard[6][i] = "pw"+i;
                gameBoard[1][i] = "pb"+i;
            }
            Pawn[] pawn = {new Pawn(), new Pawn()};

            pieceMap.put('p', pawn);

            //rookswh
            gameBoard[7][0] = "rw1";
            gameBoard[7][7] = "rw0";
            gameBoard[0][0] = "rb1";
            gameBoard[0][7] = "rb0";

            Rook[] rook = {new Rook(), new Rook()};

            pieceMap.put('r', rook);

            //knights
            gameBoard[7][1] = "kw1";
            gameBoard[7][6] = "kw0";
            gameBoard[0][1] = "kb1";
            gameBoard[0][6] = "kb0";

            Knight[] knight = {new Knight(), new Knight()};

            pieceMap.put('k',knight);

            //bishops
            gameBoard[7][2] = "bw1";
            gameBoard[7][5] = "bw0";
            gameBoard[0][2] = "bb1";
            gameBoard[0][5] = "bb0";

            Bishop[] bishop = {new Bishop(), new Bishop()};

            pieceMap.put('b', bishop);
            

            //queens
            gameBoard[7][3] = "qw1";
            gameBoard[0][3] = "qb0";

            Queen[] queen = {new Queen(), new Queen()};

            pieceMap.put('q', queen);
            

            //kings
            gameBoard[7][4] = "xw1";
            gameBoard[0][4] = "xb0";

            King[] king = {new King(), new King()};

            pieceMap.put('x', king);

    }

    public Game(String loadGame, boolean currentWhiteTurn){

        gameBoard = new String[LENGTH][LENGTH];
        copyBoard = new String[LENGTH][LENGTH];
        copyBoardBlkMove = new String[LENGTH][LENGTH];
        copyBoardWhtMove = new String[LENGTH][LENGTH];

        pointMap.put('p', 10);
        pointMap.put('k', 30);
        pointMap.put('b', 30);
        pointMap.put('r', 50);
        pointMap.put('q', 90);
        pointMap.put('x', 900);

        whiteTurn = currentWhiteTurn;
        whiteCheck = false;
        blackCheck = false;
        pieceMap = new HashMap<Character, Piece[]>();
        takeSet = new HashSet<String> ();

        try (Scanner scan = new Scanner(loadGame)) {
            scan.useDelimiter("/");
            String current;
            for(int i=0; i<LENGTH; i++){
                for(int j=0; j<LENGTH; j++){
                    current = scan.next();
                    if(current.equals("0")){
                        continue;
                    }
                    this.gameBoard[i][j] = current;
                }
            }
            System.out.println("parsed succesfully");
        }
            Pawn[] pawn = {new Pawn(), new Pawn()};

            pieceMap.put('p', pawn);

            Rook[] rook = {new Rook(), new Rook()};

            pieceMap.put('r', rook);

            Knight[] knight = {new Knight(), new Knight()};

            pieceMap.put('k',knight);

            Bishop[] bishop = {new Bishop(), new Bishop()};

            pieceMap.put('b', bishop);

            Queen[] queen = {new Queen(), new Queen()};

            pieceMap.put('q', queen);

            King[] king = {new King(), new King()};

            pieceMap.put('x', king);
            

        if(kingInCheck(this.gameBoard, true)){
            whiteCheck = true;
        }

        if(kingInCheck(this.gameBoard, false)){
            blackCheck = true;
        }
        
        System.out.println(">>>>>>>");//test
        System.out.println("Blackcheck: "+blackCheck);
        System.out.println("Whitecheck: "+whiteCheck);
        //S0ystem.out.println(pieceMap.get('x').length);
    }








    
    public boolean movePiece(int row, int col, int newRow, int newCol, String piece){

        //FOR TESTING 

        /* this.gameBoard[newRow][newCol] = this.gameBoard[row][col];
        this.gameBoard[row][col] = null;

        System.out.println(piece+" moved from "+row+""+col+
                            " to "+newRow+""+newCol);

        for(int i =0; i<8; i++){
            for(int j =0; j<8; j++){
                System.out.print(this.gameBoard[i][j]);
            }
            System.out.print("\n");
        }

        return true; */

        

        if(legalMove(row, col, newRow, newCol, piece, true, whiteTurn)){

            //start both checks at false
            blackCheck = false;
            whiteCheck = false;

            //add move to the stack
            String target = this.gameBoard[newRow][newCol];
            moveStack.push(new Move(row, col, newRow, newCol, piece, target));

            System.out.println("moved");
            
            //do the move
            this.gameBoard[newRow][newCol] = this.gameBoard[row][col];
            this.gameBoard[row][col] = null;

            redoStack.clear();

            

            //are any kings in check
            if(kingInCheck(this.gameBoard, true)){
                whiteCheck = true;
            }

            if(kingInCheck(this.gameBoard, false)){
                blackCheck = true;
            }

            System.out.println(">>>>>>>");//test
            System.out.println("Blackcheck: "+blackCheck);
            System.out.println("Whitecheck: "+whiteCheck);

            //change turn
            whiteTurn = !whiteTurn;

            if(whiteTurn){
                System.out.println("its whites turn");
            }else{
                System.out.println("its blacks turn");
            }

            //test AI>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

            System.out.println(">>> black calculating move");

            Move opponentMove = AiMove(this.gameBoard);

            this.gameBoard[opponentMove.destY][opponentMove.destX] = this.gameBoard[opponentMove.startY][opponentMove.startX];
            this.gameBoard[opponentMove.startY][opponentMove.startX] = null;

            for(int i =0; i<8; i++){
                for(int j =0; j<8; j++){
                    System.out.print(gameBoard[i][j]);
                }
                System.out.print("\n");
            }

            //change turn
            whiteTurn = !whiteTurn;

            if(whiteTurn){
                System.out.println("its whites turn");
            }else{
                System.out.println("its blacks turn");
            }


            //check if its check mate

            //System.out.println(">>>>>Checking Now>>>>>>");
            if(isCheckMate()){
                if(whiteTurn){
                    //black wins
                    System.out.println("CHECKMATE BLACK WINS");
                }else{
                    //white wins
                    System.out.println("CHECKMATE WHITE WINS");
                }

            }

            return true;
        }
        return false; 
    }










    private boolean legalMove(int row, int col, int newRow, int newCol, String piece, boolean realBoard, boolean turn){ 

        //1 validate correct turn
            if(piece.charAt(1)=='b'&& turn){
                if(realBoard){
                    System.out.println("incorrect turn");
                }
                return false;
            }

            if(piece.charAt(1)=='w'&& !turn){
                if(realBoard){
                    System.out.println("incorrect turn");
                }
                return false;
            }
        
        //2 validate standard illegal moves
            if(row == newRow && col == newCol){
                if(realBoard){
                    System.out.println("cannot move to its own square");
                }
                return false;
            }

            if(this.gameBoard[newRow][newCol] != null){
                if(this.gameBoard[newRow][newCol].charAt(0)=='x'){
                    if(realBoard){
                        System.out.println("cannot take a king");
                    }          
                    return false;
                }
            }

        //3 validate moveset
            
            char tempPiece = piece.charAt(0);// dont necesarily need this
            char wb = piece.charAt(1);

            //generate moveset
            pieceMap.get(tempPiece)[0].moves(row, col, (wb=='w'), gameBoard, false);

            if(!(pieceMap.get(tempPiece)[0].moveset.contains(""+newRow+newCol))){
                if(realBoard){
                    System.out.println("move does not lie in moveset");
                }
                return false;
            }
             
            
        
        //4 check if current turn is in check
            if(causesCheck(row, col, newRow, newCol, piece)){
                if(realBoard){
                    System.out.println("cannot put your own king in check");
                }
                return false;
            } 
         
        //5 cannot take ally pieces - adding to take set must be done last
            if(pieceExists(newRow, newCol)){
                if((piece.charAt(1)=='w') == whiteAt(newRow, newCol)){
                    if(realBoard){
                        System.out.println("cannot take own colour");
                    }
                    return false;
                }
                else{
                    if(realBoard){
                        takeSet.add(pieceAt(newRow, newCol));
                    }
                }
            }
            /* System.out.println("takeSet: ");
            for(String take : takeSet){
                System.out.println(take);
            } */
            

        return true;
    }









    public boolean pieceExists(int row, int col){
        if(gameBoard[row][col]==null){
            return false;
        }else{
            return true;
        }
    }

    public boolean whiteAt(int row, int col){
        char colour = gameBoard[row][col].charAt(1);
        if(colour =='w'){
            return true;
        }else{
            return false;
        }
    }

    public String kingCoords(boolean white, String[][] board){
        char colour;
        if(white){
            colour = 'w';
        }else{
            colour = 'b';
        }
        //returns coords of the whitr  king when white = true
        
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(board[i][j]!= null){
                    //if its a king and is the correct colour
                    if((board[i][j]).charAt(0)=='x' && (board[i][j]).charAt(1)==colour){
                        return (""+i+j);
                    }
                }    
            }
        }
        return null;
    }

    public String pieceAt(int row, int col){
        return this.gameBoard[row][col];
    }

   /*  public String wherePiece(String pieceName){
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(this.gameBoard[i][j].equals(pieceName)){
                    return (""+i+j);
                }
            }
        }

        //null if piece doesn't exist

        return null;
    } */









    private boolean kingInCheck(String[][] board, boolean whiteAttackedAt){
        //whiteAttackedAt = true   >> find pieces that are putting WHITE KING in check
        HashSet<String> checkSet = new HashSet<String>();
        checkSet.clear();

        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(board[i][j] != null){
                    //only look at oppositely coloured attacking pieces
                    if((board[i][j].charAt(1)=='b')==whiteAttackedAt){

                        //update current piece movesetmoveset 
                        pieceMap.get(board[i][j].charAt(0))[0].moves(i,j, (board[i][j].charAt(1)=='w'), board, true);

                        //if moveset contains king of the opposite colour

                        if(pieceMap.get(board[i][j].charAt(0))[0].moveset.contains(kingCoords((board[i][j].charAt(1)=='b'), board))){// king coords passes in white = true for a blakc piece attacking and white = false for a white piece attacking 
                            checkSet.add(board[i][j]);
                        }
                    }
                }
            }
        }

        //remove pieces from checkset that have been taken(abundant in takeSet)
        // dont actually need this but use to avoid errors
        Iterator<String> ite = checkSet.iterator();

        String current;

        while(ite.hasNext()){
            current = ite.next();
            if(takeSet.contains(current)){
                ite.remove();
            }
        }
    
        //check all the pieces in checkset

       // System.out.println("pieces in checkset");
        
        for (String currentPiece : checkSet) {

            //System.out.println(currentPiece);

            //want to find coords of opositely coloured king

            if(kingCoords((currentPiece.charAt(1)=='b'), board) != null){
                if((currentPiece.charAt(1) == 'b')==whiteAttackedAt){// if piece is white and whiteAttackedAt is flase then we a check has occurred
                    return true;
                }
            }
        } 
        return false;
    }

    private boolean isCheckMate() {
        //checks if colour is in checkmate after a move is made and the turn has been changed to this colour
        //for each square
        System.out.println("<<Pieces to Check>>");
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(this.gameBoard[i][j] != null){
                    //only look at similarly coloured pieces to colour under attack
                    if((this.gameBoard[i][j].charAt(1)=='w')==whiteTurn){    
                        System.out.println(this.gameBoard[i][j]);
                        //generate its moveset
                        pieceMap.get(this.gameBoard[i][j].charAt(0))[1].moves(i,j, (this.gameBoard[i][j].charAt(1)=='w'), this.gameBoard, false);

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
                            if(legalMove(i, j, Character.getNumericValue(move.charAt(0)), Character.getNumericValue(move.charAt(1)), this.gameBoard[i][j], false, whiteTurn)){
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }


    private boolean causesCheck(int row, int col, int newRow, int newCol, String piece){

        //generate the board and the moves
        for(int i =0; i<8; i++){
            for(int j =0; j<8; j++){
                this.copyBoard[i][j] = this.gameBoard[i][j];
            }
            
        }

        copyBoard[newRow][newCol] = copyBoard[row][col];
        copyBoard[row][col] = null;

        /* System.out.println("Copy Board:");

        for(int i =0; i<8; i++){
            for(int j =0; j<8; j++){
                System.out.print(this.copyBoard[i][j]);
            }
            System.out.print("\n");
        } */
        
       // if its white's turn then we want to check if WHITE is under attack etc ~~ turns dont change until after move validation
        if(kingInCheck(copyBoard, whiteTurn)){
            System.out.println("<<< check inside copyBoard >>>");
            return true;
        } 

        return false;

    }








  
    public String parseGame(){
        String output="";
        for(int i=0; i<LENGTH; i++){
            for(int j=0; j<LENGTH; j++){
                if(this.gameBoard[i][j]== null){
                    output = output+"0/";
                }else{
                    output = output+this.gameBoard[i][j]+"/";
                }
            }
        }

        return output;
    }

    //move stack methods

    public void undo(){
        //keep stack of undo moves
        Move recentMove = moveStack.peek();
        redoStack.push(recentMove);

        //
        System.out.println("undoing");
        System.out.println(recentMove.piece);

        System.out.println("redo stack");
        System.out.println(redoStack.size());
        //undo the move
        this.gameBoard[recentMove.startY][recentMove.startX] = this.gameBoard[recentMove.destY][recentMove.destX];
        this.gameBoard[recentMove.destY][recentMove.destX] = recentMove.takenPiece;

        if(recentMove.takenPiece != null){
            takeSet.remove(recentMove.takenPiece);
        }

        moveStack.pop();

        whiteCheck = false;
        blackCheck = false;

        if(kingInCheck(this.gameBoard, true)){
            whiteCheck = true;
        }

        if(kingInCheck(this.gameBoard, false)){
            blackCheck = true;
        }
        System.out.println(">>>>>>>");//test
        System.out.println("Blackcheck: "+blackCheck);
        System.out.println("Whitecheck: "+whiteCheck);

        whiteTurn = ! whiteTurn;
    }

    public void redo(){
        Move tempMove = redoStack.peek();
        moveStack.push(tempMove);
        //need a test case for this

        // redo the move
        this.gameBoard[tempMove.destY][tempMove.destX] = tempMove.piece;
        this.gameBoard[tempMove.startY][tempMove.startX] = null;

        if(tempMove.takenPiece != null){
            takeSet.add(tempMove.takenPiece);
        }

        redoStack.pop();

        //Test
        System.out.println("redo stack");
        System.out.println(redoStack.size());

        whiteCheck = false;
        blackCheck = false;

        if(kingInCheck(this.gameBoard, true)){
            whiteCheck = true;
        }

        if(kingInCheck(this.gameBoard, false)){
            blackCheck = true;
        }

        System.out.println(">>>>>>>");//test
        System.out.println("Blackcheck: "+blackCheck);
        System.out.println("Whitecheck: "+whiteCheck);
        whiteTurn = ! whiteTurn;

        if(isCheckMate()){
            if(whiteTurn){
                //black wins
                System.out.println("CHECKMATE BLACK WINS");
            }else{
                //white wins
                System.out.println("CHECKMATE WHITE WINS");
            }
        }
    }





    // oponent methods

    public Vector<Move> generateMoveList(String[][] board, boolean white){

        //initialise moveList
        Vector<Move> moveList = new Vector<Move>();
        int newRow;
        int newCol;

        System.out.println("printing white = "+white);

        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(board[i][j] != null){
                    //only look at similarly coloured pieces to colour under attack
                    if((board[i][j].charAt(1)=='w') == white){    
                        System.out.println(this.gameBoard[i][j]);
                        //generate its moveset
                        pieceMap.get(board[i][j].charAt(0))[1].moves(i,j,
                                     /* false for black colour */(board[i][j].charAt(1)=='w'), board, false);

                        //for each move in their moveset...
                        Iterator<String> ite = pieceMap.get(board[i][j].charAt(0))[1].moveset.iterator();

                        String move;

                        System.out.println(board[i][j]);

                        while(ite.hasNext()){
                            move = (String) ite.next();
                            System.out.println(move);

                            newRow = Character.getNumericValue(move.charAt(0));
                            newCol = Character.getNumericValue(move.charAt(1));

                            System.out.println(board[i][j]);
                            
                            //check if move is legal
                            if(legalMove(i, j, newRow, newCol, board[i][j], false, white)){ // white = true then we work out moveset for white pieces and therefore the turn must be white
                                //add to vecor
                                System.out.println(board[i][j]+"before");
                                moveList.add(new Move(i, j, newRow, newCol, board[i][j], board[newRow][newCol]));
                                System.out.println(move);
                                System.out.println(board[i][j]);
                            }
                        }
                    }
                }
                System.out.println("successful iteration");
            }
        }

        return moveList;
    }
    public Move AiMove(String[][] board){

        Move returnMove;

        Vector<Move> blackMoves = generateMoveList(this.gameBoard, false);

        //for each move generate a rating

        Iterator<Move> a = blackMoves.iterator();

        Move currentBlackMove;
        Move currentWhiteMove;

        if(blackMoves.size()==0){
            System.out.println("CHECK MATE WHITE WINS");
            return null;
        }

        while(a.hasNext()){
            currentBlackMove = a.next();
            //reset copyBoard
            for(int i =0; i<8; i++){
                for(int j =0; j<8; j++){
                    copyBoardBlkMove[i][j] = board[i][j];
                }
                
            }
            //make black move on copy board
            copyBoardBlkMove[currentBlackMove.destY][currentBlackMove.destX] = copyBoardBlkMove[currentBlackMove.startY][currentBlackMove.startX];
            copyBoardBlkMove[currentBlackMove.startY][currentBlackMove.startX] = null;


            //generate whitemoves after black move
            Vector<Move> whiteMoves = generateMoveList(copyBoardBlkMove, true);

            //if white can make no moves after this move then AI wins
            if(whiteMoves.size() == 0){
                System.out.println("CHECK MATE BLACK WINS");
                return currentBlackMove;
            }



            //else iterate through white moves and generate a rating
            Iterator<Move> b = whiteMoves.iterator();

            while(b.hasNext()){
                currentWhiteMove = b.next();
                //get white rating
                generateRating(copyBoardBlkMove,  currentWhiteMove);
                
            }
            
            //find minimum
            int min = whiteMoves.elementAt(0).rating;

            for(Move m : whiteMoves){
                if(m.rating < min){
                    min = m.rating;
                }
            }

            //assign min rating to black move
            currentBlackMove.rating = min;

            //add points when move puts white in check

            for(Move m : blackMoves){
                if(kingInCheck(copyBoardBlkMove, true )){
                    m.rating += pointMap.get('x');
                }
            }

        } 
        

        int max = blackMoves.elementAt(0).rating;
        returnMove = blackMoves.elementAt(0);

        for(Move m : blackMoves){
            if(m.rating > max){
                max = m.rating;
                returnMove = m;
            }
        }
       
        return returnMove;

    }

    private void generateRating(String[][] board,  Move whtMove){
        whtMove.rating = 0;
        //add value for taking a piece

        //generate current white move on auxillary copy board
        
        for(int i =0; i<8; i++){
            for(int j =0; j<8; j++){
                copyBoardWhtMove[i][j] = board[i][j];
            }
            
        }

        //make move on auxillary copy board

        copyBoardWhtMove[whtMove.destY][whtMove.destX] = copyBoardWhtMove[whtMove.startY][whtMove.startX];
        copyBoardWhtMove[whtMove.startY][whtMove.startX] = null;

        for(int i =0; i<8; i++){
            for(int j =0; j<8; j++){
                if(copyBoardWhtMove[i][j] != null){
                    //take away points for every white piece and add points for every black piece
                    if(copyBoardWhtMove[i][j].charAt(1)=='w'){
                        whtMove.rating -= pointMap.get(copyBoardWhtMove[i][j].charAt(0));
                    }else{
                        whtMove.rating += pointMap.get(copyBoardWhtMove[i][j].charAt(0));
                    }
                }
            }   
        }

        //if move causes check

        if(kingInCheck(copyBoardWhtMove, false)){// black attacked
            whtMove.rating -= pointMap.get('x');
        } 
    }








}   
