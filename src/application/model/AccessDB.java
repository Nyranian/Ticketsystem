package application.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccessDB {
    static {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // In jeder Instanz glecih
    private static  Connection connection = null;


    /**
     *
     * Vereinfachtes Singleton - Pattern
     */
    public static Connection getConnection(){
        //Wenn connection noch nciht befüllt wurde (connection == null)
        // da es sich um eine statische Variable handelt, ist diese
        // in allen Objektinstanzen gleich!
        if(connection == null){
            try {
                //erzeuge neue Verbindung zur Datenbank
                connection = DriverManager.getConnection(("jdbc:ucanaccess://db/Ticket_Verwaltung.accdb"));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return connection;
    }

}
