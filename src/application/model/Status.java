package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

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


    public static ObservableList<Status> openFile(){
        String s = "";
        ObservableList<Status> result = FXCollections.observableArrayList();

        try {
            try( BufferedReader br = new BufferedReader(new FileReader("stati.csv"))){
                while ((s = br.readLine()) != null) {
                    String[] words = s.split(";");

                    Status status = new Status();
                    status.statusID = Integer.parseInt(words[0]);
                    status.statusName = words[1];

                    result.add(status);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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
