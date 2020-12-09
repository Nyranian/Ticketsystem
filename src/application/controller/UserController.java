package application.controller;

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
    public ListView userListView;
    public TextField userNameTextfield;
    public TextField userTitleTextfield;
    public TextField userStreetTextfield;
    public TextField userPlzTextfield;
    public TextField userPlaceTextfield;
    public ComboBox userDepartmentCombobox;
    public Button cancelButton;
    private User selectedUser = null;
    ObservableList<User> userList = FXCollections.observableArrayList();



    public void initialize() {
        userList =User.openFile();
        userListView.setItems(userList);

    }



    public void saveClicked(ActionEvent actionEvent) {
        if (this.selectedUser != null &&
                !(userNameTextfield.getText().equals("") || userNameTextfield == null)&&
                !(userTitleTextfield.getText().equals("") || userTitleTextfield == null)&&
                !(userPlzTextfield.getText().equals("") || userPlzTextfield == null)&&
                !(userPlaceTextfield.getText().equals("") || userPlaceTextfield == null)&&
                !userDepartmentCombobox.getItems().isEmpty()
        ) {
            selectedUser.userName = userNameTextfield.getText();
            selectedUser.userPlace = userPlaceTextfield.getText();
            selectedUser.userStreet = userStreetTextfield.getText();
            selectedUser.userTitle = userTitleTextfield.getText();
            selectedUser.userPlz = Short.parseShort(userPlzTextfield.getText());
            selectedUser.userDepartment = (String) userDepartmentCombobox.getValue();
            userListView.refresh();
            System.out.println("Daten aktualisieren");
        } else {
            // erzeuge neuen Priorität, füge ihn in die ListView ein
            // und speichere alles in die Datei
            User user = new User(); //neuer Speicherplatz für artikel wird reserviert
            user.userID = String.valueOf(Integer.parseInt(userList.get(userList.size() - 1).userID) + 1);
            user.userName = userNameTextfield.getText();

            userList.add(user);

            System.out.println("Neue Priorität");
        }
        User.printToFile(userList);
        this.selectedUser = null;
        userNameTextfield.clear();
        userPlaceTextfield.clear();
        userPlzTextfield.clear();
        userTitleTextfield.clear();
        userStreetTextfield.clear();
        userDepartmentCombobox.getItems().clear();



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
            userDepartmentCombobox.setItems(User.userDepartmentList);
        }
    }
}
