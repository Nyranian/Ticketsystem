package application.controller;

import application.model.Department;
import application.model.Status;
import application.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UserController {
    public ListView<User> userListView;
    public TextField userNameTextfield;
    public TextField userTitleTextfield;
    public TextField userStreetTextfield;
    public TextField userPlzTextfield;
    public TextField userPlaceTextfield;
    public ComboBox<Department> userDepartmentCombobox;
    public Button cancelButton;
    private User selectedUser = null;
    ObservableList<User> userList = FXCollections.observableArrayList();



    public void initialize() {
        userList = User.loadUserList();
        userListView.setItems(userList);

    }



    public void saveClicked(ActionEvent actionEvent) {
        if (this.selectedUser != null) {
            selectedUser.userName = userNameTextfield.getText();

            userListView.refresh();

            selectedUser.update(); // Aktualisiere in Datenbank

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

        User.printToFile(userList);
        this.selectedUser = null;
        userNameTextfield.clear();
    }

    public void deleteClicked(ActionEvent actionEvent) {
        userNameTextfield.clear();
        userListView.getItems().remove(selectedUser);

        selectedUser.delete();
    }

    public void cancelClicked(ActionEvent actionEvent) {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }


    public void userListViewClicked(MouseEvent mouseEvent) {
        User selected = (User) userListView.getSelectionModel().getSelectedItem();

        if (selected != null) {
            this.selectedUser = selected;
            userTitleTextfield.setText(userList.get(userListView.getSelectionModel().getSelectedIndex()).userTitle);
            userNameTextfield.setText(userList.get(userListView.getSelectionModel().getSelectedIndex()).userName);
            userPlaceTextfield.setText(userList.get(userListView.getSelectionModel().getSelectedIndex()).userPlace);
            userPlzTextfield.setText(String.valueOf(userList.get(userListView.getSelectionModel().getSelectedIndex()).userPlz));
            userStreetTextfield.setText(userList.get(userListView.getSelectionModel().getSelectedIndex()).userStreet);
            userDepartmentCombobox.setItems(Department.loadList());

            for (Department d : userDepartmentCombobox.getItems()) {
                if (d.departmentID == (selectedUser.userDepartment.departmentID)) {
                    userDepartmentCombobox.getSelectionModel().select(d);
                    break;
                }
            }
        }
    }
}
