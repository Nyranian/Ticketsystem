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
    public Status status;
    public Priority priority;
    public ObservableList<User> userList = FXCollections.observableArrayList();

    public Ticket(int id, String name, String desc, int statusId, int priorityId){
        this.ticketID = id;
        this.ticketName = name;
        this.ticketBeschreibung = desc;
        this.status = Status.getById(statusId);
        this.priority = Priority.getById(priorityId);
    }

    public  Ticket(){
        this.ticketID = 0;
        this.ticketName = null;
        this.ticketBeschreibung = null;
        this.status = null;
        this.priority = null;
    }
    @Override
    public String toString(){
        return ticketName;
    }

    public  String newCSVLine(){
        return ticketID + ";" + ticketName + ";" + ticketBeschreibung + ";" + status.statusName + ";" + priority.priorityName +"\n";
    }

    public static Ticket getById(int id){
        Ticket obj = null;
        try {
            Connection connection = AccessDB.getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT  * FROM tickets WHERE ticket_id = " +id);

            if(result.next()){
                obj = new Ticket(result.getInt("ticket_id"),
                        result.getString("name"), result.getString("desc"),
                        result.getInt("status_id"), result.getInt("priorityId"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return obj;
    }

    public static ObservableList<Ticket> openFile() {
        ObservableList<Ticket> list = FXCollections.observableArrayList();

        try {
            Connection connection = AccessDB.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT  * FROM tickets");
            while (result.next()){
                Ticket t = new Ticket(result.getInt("ticket_id"),
                        result.getString("name"), result.getString("desc"),
                        result.getInt("status_id"), result.getInt("priority_id"));

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

