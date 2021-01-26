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

    public  Ticket(int id, String name, String desc, int statusId, int departmentId){
        this.ticketID = id;
        this.ticketName = name;
        this.ticketBeschreibung = desc;
        this.status = Status.getById(statusId);
        this.priority = Priority.getById(departmentId);
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
                obj = new Ticket(result.getInt("dpartment_id"),
                        result.getString("name"), result.getString("desc"),
                        result.getInt("status_id"), result.getInt("department_id"));
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
                Ticket t = new Ticket();
                t.ticketID = result.getInt("ticket_id");
                t.ticketName= result.getString("name");
                t.ticketBeschreibung = result.getString("desc");

                t.status = new Status();
                t.status = Status.getById(result.getInt("department_id"));


                t.priority = new Priority();
                t.priority.priorityID = result.getInt("priority_id");


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

