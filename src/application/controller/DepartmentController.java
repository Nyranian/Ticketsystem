package application.controller;

import application.model.Department;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

public class DepartmentController {
    public ListView departmentListView;
    public TextField departmentNameField;
    public Button saveButton;
    public Button cancelButton;
    public ObservableList<Department> departmentList = FXCollections.observableArrayList();
    String departmentText = "";
    String currentItemText;
    int currentIndex;

    public void initialize(){
        departmentList = Department.openFile();

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
        String departmentText = "";
        Department department = new Department();

        if (departmentListView.getSelectionModel().getSelectedIndex() != -1) {

            departmentText = departmentList.get(currentIndex).departmentID + ";" + departmentNameField.getText() + ";\n";

            departmentList.get(currentIndex).departmentName = departmentNameField.getText();
            departmentListView.setItems(departmentList);
            departmentListView.refresh();

            this.departmentText = this.departmentText.replace(currentItemText, departmentText);

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("department.csv"));

                bw.write(this.departmentText);
                bw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            departmentNameField.setText("");

        }else{
            department.departmentID = departmentList.size() + 1;
            department.departmentName = departmentNameField.getText();

            departmentText = department.departmentID + ";" + department.departmentName + ";\n";

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("department.csv", true));

                bw.write(departmentText);
                bw.close();

                departmentList.add(department);
                departmentListView.setItems(departmentList);
                departmentListView.refresh();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void cancelClicked() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }


}
