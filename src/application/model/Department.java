package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;

public class Department {
    public String departmentName;
    public int departmentID;


    @Override
    public String toString(){
        return departmentName;
    }

    public static ObservableList<Department> openFile(){
        String s = "";
        ObservableList<Department> result = FXCollections.observableArrayList();

        try {
            BufferedReader br = new BufferedReader(new FileReader("departments.csv"));

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

}
