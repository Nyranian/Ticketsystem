package application.controller;

import application.model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StatusController {
    public ListView<Status> statusListView;
    public TextField statusNameField;
    public Button cancelButton;
    private  Status selectedStatus = null;
    ObservableList<Status> statusList = FXCollections.observableArrayList();

    public void initialize() {
        statusList = Status.loadStatusList();
        statusListView.setItems(statusList);
    }

    public void statusItemClicked() {
        Status selected = statusListView.getSelectionModel().getSelectedItem();

        if (selected != null) {
            this.selectedStatus = selected;
            statusNameField.setText(statusListView.getSelectionModel().getSelectedItem().toString());
        }
    }
    public void cancelClicked() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    public void saveClicked() {
        if (this.selectedStatus != null) {
            selectedStatus.statusName = statusNameField.getText();
            statusListView.refresh();
            System.out.println("Daten aktualisiert");
        } else {
            Status status = new Status();
            status.statusID = (statusList.get(statusList.size() - 1).statusID) + 1;
            status.statusName = statusNameField.getText();

            statusList.add(status);
            System.out.println("Neuer Status");
        }
        Status.printToFile(statusList);
        this.selectedStatus = null;
        statusNameField.clear();
    }

}
