package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    public ObservableList<User> userList;

    public Ticket(int id, String name, String desc, int statusId, int priorityId, ObservableList<User> userList) {
        this.ticketID = id;
        this.ticketName = name;
        this.ticketBeschreibung = desc;
        this.status = Status.getById(statusId);
        this.priority = Priority.getById(priorityId);
        this.userList = userList;
    }

    public Ticket() {
        this.ticketID = 0;
        this.ticketName = null;
        this.ticketBeschreibung = null;
        this.status = null;
        this.priority = null;
        this.userList = null;
    }

    @Override
    public String toString() {
        return ticketName;
    }

    public static Ticket getById(int id) {
        Ticket obj = null;
        try {
            Connection connection = AccessDB.getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT  * FROM tickets WHERE ticket_id = " + id);

            if (result.next()) {
                obj = new Ticket(result.getInt("ticket_id"),
                        result.getString("name"), result.getString("desc"),
                        result.getInt("status_id"), result.getInt("priorityId"),
                        userToTickets(id));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return obj;
    }

    public static ObservableList<User> userToTickets(int id) {
        ObservableList<User> userList = FXCollections.observableArrayList();
        try {
            Connection connection = AccessDB.getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM users_to_tickets WHERE ticket_id = " + id);

            while (result.next()) {
                userList.add(User.getById(result.getInt("user_id")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return userList;
    }

    public static ObservableList<Ticket> loadList() {
        ObservableList<Ticket> list = FXCollections.observableArrayList();

        try {
            Connection connection = AccessDB.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT  * FROM tickets");
            while (result.next()) {
                Ticket t = new Ticket(result.getInt("ticket_id"),
                        result.getString("name"), result.getString("desc"),
                        result.getInt("status_id"), result.getInt("priority_id"),
                        userToTickets(result.getInt("ticket_id")));

                list.add(t);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
}

