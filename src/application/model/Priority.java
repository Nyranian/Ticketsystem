package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Priority {
    public String priorityName;
    public int priorityID;

    @Override
    public String toString() {
        return priorityName;
    }

    public Priority(int priorityID, String priorityName) {
        this.priorityName = priorityName;
        this.priorityID = priorityID;
    }

    public Priority() {
        this.priorityName = "";
        this.priorityID = 0;
    }

    public void delete() {
        try {
            Connection connection = AccessDB.getConnection();

            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM priorities WHERE priority_id = " + priorityID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Priority getById(int id) {
        if (id > 0) {
            Priority obj = null;
            try {
                Connection connection = AccessDB.getConnection();
                Statement statement = null;
                statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT  * FROM priorities WHERE priority_id = " + id);

                if (result.next()) {
                    obj = new Priority();
                    obj.priorityID = result.getInt("priority_id");
                    obj.priorityName = result.getString("name");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return obj;
        }
        return new Priority();
    }

    public void update() {
        try {
            Connection connection = AccessDB.getConnection();

            PreparedStatement statement = null;

            statement = connection.prepareStatement("UPDATE priorities SET name = ? WHERE priority_id = " + priorityID);

            statement.setString(1, priorityName);

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static ObservableList<Priority> loadList() {
        ObservableList<Priority> list = FXCollections.observableArrayList();

        try {
            Connection connection = AccessDB.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT  * FROM priorities");
            while (result.next()) {
                Priority p = new Priority();
                p.priorityName = result.getString("name");
                p.priorityID = result.getInt("priority_id");
                list.add(p);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }


}
