package AHprojectBC;
import java.sql.Date;

public class DbRecord {
    String SaveName;
    boolean WhiteTurn;
    String GameString;
    Date DateCreated;

    public DbRecord(String saveNameInput, boolean whiteTurnInput,  String GameStringInput, Date dateInput){
        this.SaveName = saveNameInput;
        this.WhiteTurn = whiteTurnInput;
        this.GameString = GameStringInput;
        this.DateCreated = dateInput;
    }
    
}
