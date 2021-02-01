package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.*;

public class Priority {
    public String priorityName;
    public int priorityID;

    @Override
    public String toString() {
        return priorityName;
    }

    public String newCSVLine() {
        return priorityID + ";" + priorityName + "\n";
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

    public static ObservableList<Priority> openFile() {
        String s;
        ObservableList<Priority> result = FXCollections.observableArrayList();

        try {
            try (BufferedReader br = new BufferedReader(new FileReader("priorities.csv"))) {

                while ((s = br.readLine()) != null) {

                    Priority priority = new Priority();

                    String[] words = s.split(";");
                    priority.priorityID = Integer.parseInt(words[0]);
                    priority.priorityName = words[1];

                    result.add(priority); // füge Priorität zur Liste hinzu
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void printToFile(ObservableList<Priority> priorityList) {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("priorities.csv"));
            for (Priority p : priorityList) {
                bw.write(p.newCSVLine());
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
