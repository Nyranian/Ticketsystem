package application.controller;

import application.model.Priority;
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
    Status currentStatus;
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
            currentStatus = statusListView.getSelectionModel().getSelectedItem();

            currentItemText = statusList.get(currentIndex).statusID + ";" + statusList.get(statusListView.getSelectionModel().getSelectedIndex()).statusName + ";\n";
        }
    }

    public void saveClicked() {
        if (this.currentStatus != null) {
            currentStatus.statusName = statusNameField.getText();

            statusListView.refresh();

        } else {
            Status status = new Status();
            status.statusID = (statusList.get(statusList.size() - 1).statusID) + 1;
            status.statusName = statusNameField.getText();

            statusList.add(status);
        }
        Status.printToFile(statusList);
        this.currentStatus = null;
        statusNameField.clear();
    }

    public void cancelClicked() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
}
