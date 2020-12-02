package application.controller;

import application.model.Department;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class DepartmentController {
    public ListView departmentListView;
    public TextField departmentNameField;
    public Button saveButton;
    public Button cancelButton;
    ObservableList<Department> departmentList = FXCollections.observableArrayList();
    String statusText = "";
    String currentItemText;
    int currentIndex;

    public void initialize(){
        String s = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader("departments.csv"));

            while ((s = br.readLine()) != null) {
                String[] words = s.split(";");

                Department department = new Department();
                department.departmentName = words[1];
                department.departmentID = Integer.parseInt(words[0]);

                statusText += department.departmentID + ";" + department.departmentName + ";\n";

                departmentList.add(department);
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        departmentListView.setItems(departmentList);
    }

    public void departmentClicked() {
        if (departmentListView.getSelectionModel().getSelectedItem() != null) {
            departmentNameField.setText(departmentListView.getSelectionModel().getSelectedItem().toString());

            currentIndex = departmentListView.getSelectionModel().getSelectedIndex();

            currentItemText = departmentList.get(currentIndex).departmentID + ";" + departmentList.get(departmentListView.getSelectionModel().getSelectedIndex()).departmentName + ";\n";
        }
    }

    public void saveClicked() {
    }

    public void cancelClicked() {
    }


}
