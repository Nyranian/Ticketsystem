package application.controller;

import application.model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

public class StatusController {
    public ListView<Status> statusListView;
    public TextField statusNameField;
    public Button cancelButton;
    String currentItemText;
    int currentIndex;
    String statusText = "";

    ObservableList<Status> statusList = FXCollections.observableArrayList();

    public void initialize() {
        statusList = Status.openFile();
        statusListView.setItems(statusList);
    }

    public void statusItemClicked() {
        if (statusListView.getSelectionModel().getSelectedItem() != null) {
            statusNameField.setText(statusListView.getSelectionModel().getSelectedItem().toString());

            currentIndex = statusListView.getSelectionModel().getSelectedIndex();

            currentItemText = statusList.get(currentIndex).statusID + ";" + statusList.get(statusListView.getSelectionModel().getSelectedIndex()).statusName + ";\n";
        }
    }

    public void saveClicked() {
        String statusText = "";
        Status status = new Status();

        System.out.println(statusListView.getSelectionModel().getSelectedIndex());

        if (statusListView.getSelectionModel().getSelectedIndex() != -1) {

            statusText = statusList.get(currentIndex).statusID + ";" + statusNameField.getText() + ";\n";

            statusList.get(currentIndex).statusName = statusNameField.getText();
            statusListView.setItems(statusList);
            statusListView.refresh();

            this.statusText = this.statusText.replace(currentItemText, statusText);

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("stati.csv"));

                bw.write(this.statusText);
                bw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            statusNameField.setText("");

        }else{
            status.statusID = statusList.size() + 1;
            status.statusName = statusNameField.getText();

            statusText = status.statusID + ";" + status.statusName + ";\n";

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("stati.csv", true));

                bw.write(statusText);
                bw.close();

                statusList.add(status);
                statusListView.setItems(statusList);
                statusListView.refresh();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void cancelClicked() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
}
