package application.controller;

import application.model.Priority;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

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
        String s;

        try {
            try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {

                while ((s = br.readLine()) != null) {

                   User user = new User();

                    String[] words = s.split(";");
                    user.userID = words[0];
                    user.userTitle = words[1];
                    user.userName = words[2];
                    user.userStreet = words[3];
                    user.userPlz = Short.parseShort(words[4]);
                    user.userPlace = words[5];
                    user.userDepartment = words[6];


                    userList.add(user); // füge Priorität zur Liste hinzu
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        userListView.setItems(userList);

    }


    public void saveClicked(ActionEvent actionEvent) {
        if (this.selectedUser != null) {
            selectedUser.userName = userNameTextfield.getText();
            selectedUser.userPlace = userPlaceTextfield.getText();
            selectedUser.userStreet = userStreetTextfield.getText();
            selectedUser.userTitle = userTitleTextfield.getText();
            selectedUser.userPlz = Short.parseShort(userPlzTextfield.getText());

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
        printToFile();
        this.selectedUser = null;
        userNameTextfield.clear();

    }
    private void printToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("users.csv"));
            for (User u : userList) {
                bw.write(u.newCSVLine());
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            //userDepartmentCombobox.setItems(userList.get(userListView.getSelectionModel().getSelectedIndex()));
        }
    }
}
