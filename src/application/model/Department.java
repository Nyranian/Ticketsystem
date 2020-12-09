package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Department {
    public String departmentName;
    public int departmentID;


    @Override
    public String toString(){
        return departmentName;
    }

    public  String newCSVLine(){
        return departmentID + ";" + departmentName + "\n";
    }

    public static ObservableList<Department> openFile(){
        String s = "";
        ObservableList<Department> result = FXCollections.observableArrayList();

        try {
            BufferedReader br = new BufferedReader(new FileReader("department.csv"));

            while ((s = br.readLine()) != null) {
                String[] words = s.split(";");

                Department department = new Department();
                department.departmentName = words[1];
                department.departmentID = Integer.parseInt(words[0]);


                result.add(department);
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void printToFile(ObservableList<Department> departmentList){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("department.csv"));
            for (Department d : departmentList) {
                bw.write(d.newCSVLine());
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
