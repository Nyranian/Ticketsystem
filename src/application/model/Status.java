package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Status {
    public String statusName;
    public int statusID;

    public Status(int statusID, String statusName) {
        this.statusName = statusName;
        this.statusID = statusID;
    }

    public Status() {
        this.statusName = "";
        this.statusID = 0;
    }

    @Override
    public String toString() {
        return statusName;
    }

    public static Status getById(int id) {
        if (id > 0) {
            Status obj = null;
            try {
                Connection connection = AccessDB.getConnection();
                Statement statement = null;
                statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT  * FROM stati WHERE status_id = " + id);

                if (result.next()) {
                    obj = new Status();
                    obj.statusID = result.getInt("status_id");
                    obj.statusName = result.getString("name");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return obj;
        }
        return new Status();
    }


    public void delete() {
        try {
            Connection connection = AccessDB.getConnection();

            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM stati WHERE status_id = " + statusID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update() {
        try {
            Connection connection = AccessDB.getConnection();

            PreparedStatement statement = null;

            statement = connection.prepareStatement("UPDATE stati SET name = ? WHERE status_id = " + statusID);

            statement.setString(1, statusName);

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static ObservableList<Status> loadStatusList() {
        ObservableList<Status> list = FXCollections.observableArrayList();

        try {
            Connection connection = AccessDB.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT  * FROM stati");
            while (result.next()) {
                Status s = new Status();
                s.statusName = result.getString("name");
                s.statusID = result.getInt("status_id");
                list.add(s);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }


}
