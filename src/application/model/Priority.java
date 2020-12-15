package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Priority {
    public String priorityName;
    public  int priorityID;
    @Override
    public String toString() {
        return priorityName;
    }
    public  String newCSVLine(){
        return priorityID + ";" + priorityName+ "\n";
    }

    public static ObservableList<Priority> openFile(){
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
    public static void printToFile(ObservableList<Priority> priorityList){

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("priorities.csv"));
            for (Priority p : priorityList) {
                bw.write(p.newCSVLine());
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }}

}
