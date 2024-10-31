package src;

import java.sql.*;
import java.time.LocalDate;
import java.util.Vector;
public class DataBaseConn{
    private static String database = "jdbc:mariadb://192.168.1.3:3306/bcummings_chessgames";
    private static String user = "cummingsb";
    private static String pass = "100528339";
    private static Statement stmt = null;

    public static  Vector<DbRecord>  dbRead(){

        Vector<DbRecord> cache = new Vector<DbRecord>();
        
        String query = "SELECT SaveName, WhiteTurn, GameString, DateCreated FROM games ORDER by SaveName";
        try {
            Connection conn = DriverManager.getConnection(database, user, pass);
            stmt = conn.createStatement();
            System.out.println("I am connected");
            ResultSet rs = stmt.executeQuery(query);

            // for testing query results
            /* while(rs.next()){ 
                String save = rs.getString("SaveName");
                boolean turn = rs.getBoolean("WhiteTurn");
                String game = rs.getString("GameString");
                Date date =  rs.getDate("DateCreated");

                System.out.println(save + "\n" + turn +"\n"+ game +"\n"+ date +"\n\n");
            } */
            
            while(rs.next()){
                cache.add(new DbRecord(rs.getString("SaveName"), rs.getBoolean("WhiteTurn"), rs.getString("GameString"), rs.getDate("DateCreated")));
                //System.out.println(rs.getString("SaveName"));//test
            }

            // test print vecto

            /* for(DbRecord a : cache){
                System.out.println(a.SaveName +"\n"+a.DateCreated);
            } */
            System.out.println(">>>>>>> bubble sort");
            //bubble sort sort by date
            int n = cache.size();
            DbRecord temp;
            boolean swapped = true;
            while(swapped && n>=0){
                swapped = false;
                for(int i = 0; i<= n-2; i++){//****
                    if(cache.elementAt(i).DateCreated.before(cache.elementAt(i+1).DateCreated)){
                        temp = cache.elementAt(i);
                        cache.set(i, cache.elementAt(i+1));
                        cache.set(i+1, temp);
                        swapped = true;
                    }
                }
                n--;
            }
            //testing bubble sort
            /* for(DbRecord a : cache){
                System.out.println(a.SaveName +"\n"+a.DateCreated);
            } */
            
            if(stmt != null){
                stmt.close();
            }

        }
        catch ( SQLException err ) {
            System.out.println("ERROR" +  err.getMessage( ) );
        }

        return cache;
    }









    
    public static void dbInsert(Boolean WhiteTurn, String SaveName, String GameString){

        LocalDate date = LocalDate.now();

        String query = "INSERT INTO games (WhiteTurn,  SaveName, GameString, DateCreated) VALUES ("+WhiteTurn.toString()+", '"+SaveName+"', '"+GameString+"', '"+date+"') ";
        
        try {
            Connection conn = DriverManager.getConnection(database, user, pass);
            stmt = conn.createStatement();
            System.out.println("I am connected");
            stmt.executeQuery(query);
            
            if(stmt != null){
                stmt.close();
            }
        }
        catch ( SQLException err ) {
            System.out.println("ERROR" +  err.getMessage( ) );
        }

    }





    public static void dbUpdate(String SaveName, Boolean WhiteTurn, String GameString){

        //LocalDate date = LocalDate.now();

        String query = "UPDATE games SET WhiteTurn = "+WhiteTurn.toString()+", GameString = '"+GameString+"' WHERE SaveName = '"+SaveName+"'";
        
        try {
            Connection conn = DriverManager.getConnection(database, user, pass);
            stmt = conn.createStatement();
            System.out.println("I am connected");
            stmt.executeQuery(query);
            
            if(stmt != null){
                stmt.close();
            }
        }
        catch ( SQLException err ) {
            System.out.println("ERROR" +  err.getMessage( ) );
        }

    }





    public static boolean gameExists(String SaveName){

        String query = "SELECT EXISTS(SELECT SaveName FROM games WHERE SaveName = '"+SaveName+"') AS 'Ex'";
        try {
            
            Connection conn = DriverManager.getConnection(database, user, pass);
            stmt = conn.createStatement();
            System.out.println("I am connected");
            ResultSet rs = stmt.executeQuery(query);

            rs.first(   );

            if(rs.getBoolean("Ex")){
                return true;
            }
            
            if(stmt != null){
                stmt.close();
            }
        }
        catch ( SQLException err ) {
            System.out.println("ERROR" +  err.getMessage( ) );
        }

        return false;
    }
    
}