package application.controller;

import application.model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

            selectedStatus.update(); // Aktualisiere in Datenbank
        }
    }

    public void deleteClicked(ActionEvent actionEvent) {
        statusNameField.clear();
        statusListView.getItems().remove(selectedStatus);

        selectedStatus.delete();
    }
}
