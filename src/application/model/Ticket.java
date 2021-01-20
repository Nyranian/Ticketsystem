package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ticket {
    public int ticketID;
    public String ticketName;
    public String ticketBeschreibung;
    public Status Status;
    public Priority Priority;

    @Override
    public String toString(){
        return ticketName;
    }

    public  String newCSVLine(){
        return ticketID + ";" + ticketName + ";" + ticketBeschreibung + ";" + Status.statusName + ";" + Priority.priorityName +"\n";
    }

    public static ObservableList<Ticket> openFile() {
        ObservableList<Ticket> list = FXCollections.observableArrayList();

        try {
            Connection connection = AccessDB.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT  * FROM tickets");
            while (result.next()){
                Ticket t = new Ticket();
                t.ticketID = result.getInt("ticket_id");
                t.ticketName= result.getString("name");
                t.ticketBeschreibung = result.getString("desc");

                t.Status = new Status();
                t.Status.statusID = result.getInt("status_id");

                t.Priority = new Priority();
                t.Priority.priorityID = result.getInt("priority_id");


                list.add(t);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
    public static void printToFile(ObservableList<Ticket> ticketList){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("tickets.csv"));
            for (Ticket t: ticketList) {
                bw.write(t.newCSVLine());
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }}

}

