package application.controller;

import application.model.Department;
import application.model.Priority;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DepartmentController {
    public ListView<Department> departmentListView;
    public TextField departmentNameField;
    public Button saveButton;
    public Button cancelButton;
    public ObservableList<Department> departmentList = FXCollections.observableArrayList();

    String currentItemText;
    int currentIndex;
    Department selectedDepartment;

    public void initialize(){
        departmentList = Department.loadList();
        departmentListView.setItems(departmentList);
    }

    public void departmentClicked() {
        if (departmentListView.getSelectionModel().getSelectedItem() != null) {
            departmentNameField.setText(departmentListView.getSelectionModel().getSelectedItem().toString());

            currentIndex = departmentListView.getSelectionModel().getSelectedIndex();
            selectedDepartment = (Department) departmentListView.getSelectionModel().getSelectedItem();

            currentItemText = departmentList.get(currentIndex).departmentID + ";" + departmentList.get(departmentListView.getSelectionModel().getSelectedIndex()).departmentName + ";\n";
        }
    }

    public void saveClicked(ActionEvent actionEvent) {
        if (this.selectedDepartment != null) {
            selectedDepartment.departmentName = departmentNameField.getText();

            departmentListView.refresh();

            selectedDepartment.update(); // Aktualisiere in Datenbank

        } /*else{
            if(!priorityTextField.getText().isEmpty()){
                Priority priority = new Priority(); //neuer Speicherplatz für artikel wird reserviert
                priority.priorityID = priorityList.get(priorityList.size() - 1).priorityID + 1;
                priority.priorityName = priorityTextField.getText();

                priorityList.add(priority);
                prioritylistView.getItems().add(priority);
            }
        }
        */

        Department.printToFile(departmentList);
        this.selectedDepartment = null;
        departmentNameField.clear();
    }

    public void deletClicked(ActionEvent actionEvent) {
        departmentNameField.clear();
        departmentListView.getItems().remove(selectedDepartment);

        selectedDepartment.delete();
    }

    public void cancelClicked() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }


}
