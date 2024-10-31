package src;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.util.Vector; 

public class Board{

    public String NameOfSave;
    public Game game;

    private boolean selected;
    private String selectedPiece;

    private int selY;
    private int selX;

    private int bkY;
    private int bkX;
    private int wkY;
    private int wkX;

    private int panelSize;
    private int squareSize;

    private Color colourW;// = Color.decode("#FFFFC0");
    private Color colourB;// = Color.decode("#F4B0A2");

    // gui variables that need to be declared globaly since we did not extend JFrame, not declared in design

    private static final int LENGTH = 8;
    private JButton[][] chessBoardSquares = new JButton[LENGTH][LENGTH];

    private JFrame frame;
    private JPanel panel;
    private JMenu file, edit, help, view;
    private JMenuItem f1,f2,f3,f4,f5;
    private JMenuItem e1, e2, e3, e4;
    private JMenuItem v1,v2;


    public Board(int size){

        selected = false;

        NameOfSave = null;

        //setup

        frame = new JFrame("chess");
        panel = new JPanel();       

        colourW = Color.decode("#FFFFC0");
        colourB = Color.decode("#F4B0A2");

        JMenuBar menuBar = new JMenuBar();
        file=new JMenu("File");  
        edit=new JMenu("Edit"); 
        help=new JMenu("Help"); 
        view=new JMenu("View"); 

        f1=new JMenuItem("Save");  
        f2=new JMenuItem("Save As");  
        f3=new JMenuItem("Open");  
        f4=new JMenuItem("New Game");  
        f5=new JMenuItem("Exit");   

        //implementing menu button actions - facilitates database integration

        f1.addActionListener( new ActionListener(){

            public void actionPerformed(ActionEvent e) {

                String currentGame = game.parseGame();
                boolean currentTurn = game.whiteTurn;

                if(NameOfSave == null){
                    setName();

                    if(DataBaseConn.gameExists(NameOfSave)){ //if already exists

                        boolean cancel = false;

                        do{
                            //ask if you want to override this save?
                            if(getConfirm(f1, "This save already exitsts. Do you want to overide: "+NameOfSave)){
                                DataBaseConn.dbUpdate(NameOfSave, currentTurn, currentGame);
                                cancel = true;
                            }else{
                                //ask for a new name
                                setName();
                            }
                        }while(DataBaseConn.gameExists(NameOfSave) && !cancel);
                        
                        if(!cancel){ // means whiile loop ended and name chosen doesnt exist
                            DataBaseConn.dbInsert(currentTurn, NameOfSave, currentGame);
                        }

                    }else{ // if doesnt exist add it 
                        DataBaseConn.dbInsert(currentTurn, NameOfSave, currentGame);
                    }
                }else{
                    DataBaseConn.dbUpdate(NameOfSave, currentTurn, currentGame);

                }

            }
            
        });

        f2.addActionListener( new ActionListener(){

            public void actionPerformed(ActionEvent e) {

                String currentGame = game.parseGame();
                boolean currentTurn = game.whiteTurn;

                // ask for name -- via procedure
                setName();

                //System.out.println(NameOfSave);// Test

                if(DataBaseConn.gameExists(NameOfSave)){ //if already exists
                    System.out.println("Game Exists");
                    boolean cancel = false;

                    do{
                        //ask if you want to override this save?
                        if(getConfirm(f2, "This save already exitsts. Do you want to overide: "+NameOfSave)){
                            DataBaseConn.dbUpdate(NameOfSave, currentTurn, currentGame);
                            cancel = true;
                        }else{
                            //ask for a new name
                            setName();
                        }
                    }while(DataBaseConn.gameExists(NameOfSave) && !cancel);
                    
                    if(!cancel){ // means whiile loop ended and name chosen doesnt exist
                        DataBaseConn.dbInsert(currentTurn, NameOfSave, currentGame);
                    }

                }else{ // if doesnt exist add it 
                    System.out.println("Game dont Exists");
                    DataBaseConn.dbInsert(currentTurn, NameOfSave, currentGame);
                }

                

                

            }
            
        });

        f3.addActionListener( new ActionListener(){

            public void actionPerformed(ActionEvent e) {

                Vector<DbRecord> gameList = DataBaseConn.dbRead();
                String[] gameArray = new String[gameList.size()];

                int index =0;
                for(DbRecord a : gameList){
                    //array of ojects
                    gameArray[index] = a.SaveName;
                    //System.out.println(a.SaveName);
                    index++;
                } 
                //test that array has been created succesfully
                for(String a : gameArray){
                    System.out.println(a);
                }
               //String[] gameArray = {"test", "test2"}; //TEST
                
                String input = (String) JOptionPane.showInputDialog(
                    f3,                        
                    "Choose A Save.",           
                    "Saved Games",               
                    JOptionPane.QUESTION_MESSAGE, 
                    null,                         
                    gameArray,
                    null             
                    );
                
                System.out.println("you chose: "+input);
                
                //loading the game
                if(input !=null){ // didnt click cancel or escape
                    for(DbRecord a : gameList){
                        if(a.SaveName.equals(input)){
                            //System.out.println("about to construct game based on string");
                            game = new Game(a.GameString, a.WhiteTurn); // loads game stored under this name
                            NameOfSave = a.SaveName;
                            //System.out.println("parsed game");
                        }
                    }
                }// topic of discussion for efficiency ^^^

                //refresh GUI
                for(int i=0; i<LENGTH; i++){
                    for(int j=0; j<LENGTH; j++){
                        if(game.gameBoard[i][j] == null){
                            chessBoardSquares[i][j].setIcon(null);
                        }else{
                            Image initailIcon = getPieceImage(i, j);
                            if(initailIcon != null){
                                setPieceImage(initailIcon, i, j);
                            }
                            
                        }
                        chessBoardSquares[i][j].updateUI();
                        
                    }
                }
            }
        });

        f4.addActionListener( new ActionListener(){

            public void actionPerformed(ActionEvent e) {

                //do you want to save current game

                NameOfSave = null;
                
                game = new Game();

                for(int i=0; i<LENGTH; i++){
                    for(int j=0; j<LENGTH; j++){
                        if(i%2 == j%2){
                            chessBoardSquares[i][j].setBackground(colourW);
        
                        }else{
                            chessBoardSquares[i][j].setBackground(colourB);
                        }

                        if(game.gameBoard[i][j] == null){
                            chessBoardSquares[i][j].setIcon(null);
                        }else{
                            Image initailIcon = getPieceImage(i, j);
                            if(initailIcon != null){
                                setPieceImage(initailIcon, i, j);
                            }
                        }
                        
                    }
                }


            }
            
        });

        f5.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }       
        });

        file.add(f1); file.add(f2); file.add(f3); file.add(f4); file.add(f5);

        e1=new JMenuItem("Undo");
        e2=new JMenuItem("Redo");
        e3=new JMenuItem("Move Log");
        e4=new JMenuItem("Reset");
        e1.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(!game.moveStack.empty()){
                    game.undo();
                    System.out.println("undid");// test
                }
                

                for(int i=0; i<LENGTH; i++){
                    for(int j=0; j<LENGTH; j++){
                        if(game.gameBoard[i][j] == null){
                            chessBoardSquares[i][j].setIcon(null);
                        }else{
                            Image initailIcon = getPieceImage(i, j);
                            if(initailIcon != null){
                                setPieceImage(initailIcon, i, j);
                            }
                        }
                        
                    }
                }

                if(game.blackCheck){
                    //check red
                    chessBoardSquares[bkY][bkX].setBackground(Color.decode("#ff7c3b"));
                    chessBoardSquares[selY][selX].updateUI();
                    System.out.println("changed colour to red   "+bkY+bkX);

                }else{
                    if(bkY%2 == bkX%2){
                        chessBoardSquares[bkY][bkX].setBackground(colourW);
    
                    }else{
                        chessBoardSquares[bkY][bkX].setBackground(colourB);
                    }

                }

                if(game.whiteCheck){
                    chessBoardSquares[wkY][wkX].setBackground(Color.decode("#ff7c3b"));
                    chessBoardSquares[selY][selX].updateUI();
                    System.out.println("changed colour to red   "+wkY+wkX);

                }else{
                    if(wkY%2 == wkX%2){
                        chessBoardSquares[wkY][wkX].setBackground(colourW);
    
                    }else{
                        chessBoardSquares[wkY][wkX].setBackground(colourB);
                    }

                }
            }       
        });
        e2.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(!game.redoStack.empty()){
                    game.redo();
                    System.out.println("redid");// test
                }
                
                for(int i=0; i<LENGTH; i++){
                    for(int j=0; j<LENGTH; j++){
                        if(game.gameBoard[i][j] == null){
                            chessBoardSquares[i][j].setIcon(null);
                        }else{
                            Image initailIcon = getPieceImage(i, j);
                            if(initailIcon != null){
                                setPieceImage(initailIcon, i, j);
                            }
                        }
                        
                    }
                }

                if(game.blackCheck){
                    //check red
                    chessBoardSquares[bkY][bkX].setBackground(Color.decode("#ff7c3b"));
                    chessBoardSquares[selY][selX].updateUI();
                    System.out.println("changed colour to red   "+bkY+bkX);

                }else{
                    if(bkY%2 == bkX%2){
                        chessBoardSquares[bkY][bkX].setBackground(colourW);
    
                    }else{
                        chessBoardSquares[bkY][bkX].setBackground(colourB);
                    }

                }

                if(game.whiteCheck){
                    chessBoardSquares[wkY][wkX].setBackground(Color.decode("#ff7c3b"));
                    chessBoardSquares[selY][selX].updateUI();
                    System.out.println("changed colour to red   "+wkY+wkX);

                }else{
                    if(wkY%2 == wkX%2){
                        chessBoardSquares[wkY][wkX].setBackground(colourW);
    
                    }else{
                        chessBoardSquares[wkY][wkX].setBackground(colourB);
                    }

                }
            }       
        });
        e3.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //TODO
            }       
        });
        e4.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                game = new Game();

                for(int i=0; i<LENGTH; i++){
                    for(int j=0; j<LENGTH; j++){
                        if(i%2 == j%2){
                            chessBoardSquares[i][j].setBackground(colourW);
        
                        }else{
                            chessBoardSquares[i][j].setBackground(colourB);
                        }
                        
                        if(game.gameBoard[i][j] == null){
                            chessBoardSquares[i][j].setIcon(null);
                        }else{
                            Image initailIcon = getPieceImage(i, j);
                            if(initailIcon != null){
                                setPieceImage(initailIcon, i, j);
                            }
                        }
                        
                    }
                }
            }       
        });
        edit.add(e1); edit.add(e2); edit.add(e3); edit.add(e4);

        v1=new JMenuItem("Colour White");
        v2=new JMenuItem("Colour Black");
        view.add(v1); view.add(v2);

        help.addMenuListener(new MenuListener() {

            public void menuSelected(MenuEvent e) {
                // TODO Auto-generated method stub
                //System.out.println("webbed");
                getWebLink("https://en.wikipedia.org/wiki/Rules_of_chess");
                
            }

            public void menuDeselected(MenuEvent e) {
                // TODO Auto-generated method stub
                
            }

            public void menuCanceled(MenuEvent e) {
                // TODO Auto-generated method stub
                
            }

        });
        v1.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                Color initialColor = colourW;
                Color colour  =JColorChooser.showDialog(null,"Select a color for white",initialColor);
                colourW = colour;
                for(int i=0; i<LENGTH; i++){
                    for(int j=0; j<LENGTH; j++){
                        if(i%2 == j%2){
                            chessBoardSquares[i][j].setBackground(colourW);
        
                        }else{
                            chessBoardSquares[i][j].setBackground(colourB);
                        }
                    }
                }
            }
        });
        v2.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                Color initialColor = colourB;
                Color colour =JColorChooser.showDialog(null,"Select a color for black",initialColor);
                colourB = colour;
                for(int i=0; i<LENGTH; i++){
                    for(int j=0; j<LENGTH; j++){
                        if(i%2 == j%2){
                            chessBoardSquares[i][j].setBackground(colourW);
        
                        }else{
                            chessBoardSquares[i][j].setBackground(colourB);
                        }
                    }
                }
                
            }});
           
        menuBar.add(file);  
        menuBar.add(edit);
        menuBar.add(help);
        menuBar.add(view);

        frame.setJMenuBar(menuBar);  
        frame.setSize(size, size);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Image img;
        try {
            img = ImageIO.read(getClass().getResource("./chessPieces/qb.png"));
            frame.setIconImage(img);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        

        //instantilize game
        game = new Game();

        /* to show that loading game actually works *///game = new Game("rb/kb/bb/qb/xb/bb/kb/rb/pb/pb/pb/pb/pb/pb/pb/pb/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/pw/pw/pw/pw/pw/pw/pw/pw/rw/kw/bw/qw/xw/bw/kw/rw/", true);
        


        /* Rook rook1 = new Rook();
        rook1.moves(0, 0, true,game.gameBoard, true);
        Rook rook2 = new Rook();
        rook2.moves(1, 1, true,game.gameBoard, true); */

        /* System.out.println("rook1 moveset");
        for(String move : rook1.moveset){
            System.out.println(move);
            game.gameBoard[Character.getNumericValue(move.charAt(0))]
            [Character.getNumericValue(move.charAt(1))] = " 1  ";
        }
        System.out.println("rook2 moveset");
        for(String move : rook2.moveset){
            System.out.println(move);
            game.gameBoard[Character.getNumericValue(move.charAt(0))]
            [Character.getNumericValue(move.charAt(1))] = " 2 ";
        } */

        System.out.println("GameBoard: ");

        for(int i =0; i<8; i++){
            for(int j =0; j<8; j++){
                System.out.print(game.gameBoard[i][j]);
            }
            System.out.print("\n");
        } 
        //chessboard

        panelSize = (int) 0.8*size;
        squareSize = (int) 0.1*size;

        panel.setSize(panelSize,panelSize);
        panel.setLayout(new GridLayout(LENGTH, LENGTH));
        
        for(int i = 0; i<LENGTH; i++){
            for(int j = 0; j<LENGTH; j++){
                initiateChessSquare(i, j);
            }
        }

        frame.add(panel);
        panel.setVisible(true);
        frame.setVisible(true);
        
    }










    private void initiateChessSquare(int row, int col){
        chessBoardSquares[row][col] = new JButton();

                //chessboard pattern

                if(row%2 == col%2){
                    chessBoardSquares[row][col].setBackground(colourW);

                }else{
                    chessBoardSquares[row][col].setBackground(colourB);
                }

                chessBoardSquares[row][col].setOpaque(true);
                chessBoardSquares[row][col].setBorderPainted(false);
                chessBoardSquares[row][col].setSize(squareSize, squareSize); 


                //~~~chessBoardSquares[row][col].setText(game.pieceAt(row, col)); ~~~// for testing

                // iinitalise piece icons
                Image initailIcon = getPieceImage(row, col);
                if(initailIcon != null){
                    setPieceImage(initailIcon, row, col);
                }

                //button functions

                chessBoardSquares[row][col].addActionListener(new ActionListener(){  
                    public void actionPerformed(ActionEvent e){  

                        if(selected){

                            //checks if move is legal

                           // Image icon = getPieceImage(selY, selX);

                            if(game.movePiece(selY, selX, row, col, selectedPiece)){

                                System.out.println("moved"); //test

                                selected = false;
                            
                                /* if(icon != null){

                                    setPieceImage(icon, row, col);
                                    chessBoardSquares[selY][selX].setIcon(null);
                                } */

                                for(int i=0; i<LENGTH; i++){
                                    for(int j=0; j<LENGTH; j++){
                                        if(i%2 == j%2){
                                            chessBoardSquares[i][j].setBackground(colourW);
                        
                                        }else{
                                            chessBoardSquares[i][j].setBackground(colourB);
                                        }
                
                                        if(game.gameBoard[i][j] == null){
                                            chessBoardSquares[i][j].setIcon(null);
                                        }else{
                                            Image initailIcon = getPieceImage(i, j);
                                            if(initailIcon != null){
                                                setPieceImage(initailIcon, i, j);
                                            }
                                        }
                                        
                                    }
                                }

                                //get king coordinated

                                bkY = Character.getNumericValue(game.kingCoords(false, game.gameBoard).charAt(0)); 
                                bkX = Character.getNumericValue(game.kingCoords(false, game.gameBoard).charAt(1)); 

                                wkY = Character.getNumericValue(game.kingCoords(true, game.gameBoard).charAt(0)); 
                                wkX = Character.getNumericValue(game.kingCoords(true, game.gameBoard).charAt(1)); 

                                if(game.blackCheck){
                                    //check red
                                    chessBoardSquares[bkY][bkX].setBackground(Color.decode("#ff7c3b"));
                                    chessBoardSquares[selY][selX].updateUI();
                                    System.out.println("changed colour to red   "+bkY+bkX);

                                }else{
                                    if(bkY%2 == bkX%2){
                                        chessBoardSquares[bkY][bkX].setBackground(colourW);
                    
                                    }else{
                                        chessBoardSquares[bkY][bkX].setBackground(colourB);
                                    }

                                }

                                if(game.whiteCheck){
                                    chessBoardSquares[wkY][wkX].setBackground(Color.decode("#ff7c3b"));
                                    chessBoardSquares[selY][selX].updateUI();
                                    System.out.println("changed colour to red   "+wkY+wkX);

                                }else{
                                    if(wkY%2 == wkX%2){
                                        chessBoardSquares[wkY][wkX].setBackground(colourW);
                    
                                    }else{
                                        chessBoardSquares[wkY][wkX].setBackground(colourB);
                                    }

                                }

                                selectedPiece = null;

                                chessBoardSquares[selY][selX].updateUI();

                            }

                            //deselect piece 

                            else{
                                System.out.println("illegal move");
                            }

                            selected = false;

                            if(selY%2 == selX%2){
                                chessBoardSquares[selY][selX].setBackground(colourW);
            
                            }else{
                                chessBoardSquares[selY][selX].setBackground(colourB);
                            }

                            if(game.pieceAt(selY, selX)!= null){
                                if(game.pieceAt(selY, selX).charAt(0) == 'x'){
                                    if((game.pieceAt(selY, selX).charAt(1) == 'w')&&(game.whiteCheck)||(game.pieceAt(selY, selX).charAt(1) != 'w')&&(game.blackCheck)){
                                        chessBoardSquares[selY][selX].setBackground(Color.decode("#ff7c3b"));                                            

                                    }

                                }

                            }                              
                            
                            //~~~chessBoardSquares[selY][selX].setText(game.pieceAt(selY, selX)); ~~~// for testing

                        }else{
                            
                            selectedPiece = game.pieceAt(row, col);

                            if(selectedPiece != null){
                                selected = true;
                                selY = row;
                                selX = col;
                            
                            }

                            if(game.pieceExists(row, col)){
                                chessBoardSquares[row][col].setBackground(Color.decode("#c7d6d4"));// select blue
                            }

                        }

                        //for testing

                        String pieceX = game.pieceAt(row, col);
                        System.out.println(pieceX);
                        //chessBoardSquares[row][col].setText(pieceX);
                    }  
                });

                panel.add(chessBoardSquares[row][col]);

    }








    //some image methods

    private Image getPieceImage(int row, int col){
        if(game.gameBoard[row][col] != null){
            String pieceName = game.pieceAt(row, col);
            pieceName = pieceName.substring(0, 2);

            try {
                Image img = ImageIO.read(getClass().getResource("./chessPieces/"+ pieceName +".png"));
                return img;
            }catch (Exception ex) {
                System.out.println(ex);
                
            }

        }
        return null;
    }
    private void setPieceImage(Image img, int row, int col){
        chessBoardSquares[row][col].setIcon(new ImageIcon(img));
        chessBoardSquares[row][col].updateUI();

    }


    private void getWebLink(String url){
        System.out.println("webbed");
        try {         
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
          }
          catch (java.io.IOException ex) {
              System.out.println(ex.getMessage());
          }
    }




    //option pane prompt method

    public void setName(){
        NameOfSave = (String)JOptionPane.showInputDialog("Enter Save Name");
    }

    private boolean getConfirm(JComponent parent, String message){

        int a = JOptionPane.showConfirmDialog(parent, message);    
        if(a == JOptionPane.YES_OPTION){  
            return true; 
        }  
        return false;
    }
}

