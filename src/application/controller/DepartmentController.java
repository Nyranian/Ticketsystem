package application.controller;

import application.model.Department;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DepartmentController {
    public ListView departmentListView;
    public TextField departmentNameField;
    public Button saveButton;
    public Button cancelButton;
    public ObservableList<Department> departmentList = FXCollections.observableArrayList();

    String currentItemText;
    int currentIndex;
    Department currentDepartment;

    public void initialize(){
        departmentList = Department.openFile();
        departmentListView.setItems(departmentList);
    }

    public void departmentClicked() {
        if (departmentListView.getSelectionModel().getSelectedItem() != null) {
            departmentNameField.setText(departmentListView.getSelectionModel().getSelectedItem().toString());

            currentIndex = departmentListView.getSelectionModel().getSelectedIndex();
            currentDepartment = (Department) departmentListView.getSelectionModel().getSelectedItem();

            currentItemText = departmentList.get(currentIndex).departmentID + ";" + departmentList.get(departmentListView.getSelectionModel().getSelectedIndex()).departmentName + ";\n";
        }
    }

    public void saveClicked() {
        if (this.currentDepartment != null) {
            currentDepartment.departmentName = departmentNameField.getText();

            departmentListView.refresh();

        } else {
            Department department = new Department();
            department.departmentID = (departmentList.get(departmentList.size() - 1).departmentID) + 1;
            department.departmentName = departmentNameField.getText();

            departmentList.add(department);
        }
        Department.printToFile(departmentList);
        this.currentDepartment = null;
        departmentNameField.clear();
    }

    public void cancelClicked() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }


}
