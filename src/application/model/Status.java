package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Status {
    public String statusName;
    public int statusID;

    @Override
    public String toString(){
        return statusName;
    }

    public  String newCSVLine(){
        return statusID + ";" + statusName + "\n";
    }

    public static ObservableList<Status> loadStatusList(){
        ObservableList<Status> list = FXCollections.observableArrayList();

        try {
            Connection connection = AccessDB.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT  * FROM stati");
            while (result.next()){
                Status s = new Status();
                s.statusName= result.getString("name");
                s.statusID = result.getInt("status_id");
                list.add(s);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }


    public static void printToFile(ObservableList<Status> statusList){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("stati.csv"));
            for (Status s : statusList) {
                bw.write(s.newCSVLine());
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
