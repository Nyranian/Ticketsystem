package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;

public class Status {
    public String statusName;
    public int statusID;


    @Override
    public String toString(){
        return statusName;
    }


    public static ObservableList<Status> openFile(){
        String s = "";
        ObservableList<Status> result = FXCollections.observableArrayList();

        try {
            BufferedReader br = new BufferedReader(new FileReader("stati.csv"));

            while ((s = br.readLine()) != null) {
                String[] words = s.split(";");

                Status status = new Status();
                status.statusName = words[1];
                status.statusID = Integer.parseInt(words[0]);

                result.add(status);
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
