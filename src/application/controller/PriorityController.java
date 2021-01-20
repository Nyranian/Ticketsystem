package application.controller;

import application.model.Priority;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;

public class PriorityController {
    public ListView<Priority> prioritylistView;
    public TextField priorityTextField;
    public Button cancelButton;
    private Priority selectedPriority = null;
    ObservableList<Priority> priorityList = FXCollections.observableArrayList();

    public void initialize() {
        priorityList = Priority.loadList();
        prioritylistView.setItems(priorityList);

    }

    public void priorityItemClicked() {
        selectedPriority = prioritylistView.getSelectionModel().getSelectedItem();

        if (selectedPriority != null) {
            this.selectedPriority = selectedPriority;
            priorityTextField.setText(prioritylistView.getSelectionModel().getSelectedItem().toString());
        }
    }

    public void cancelClicked() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    public void saveClicked(ActionEvent actionEvent) {
        if (this.selectedPriority != null) {
            selectedPriority.priorityName = priorityTextField.getText();

            prioritylistView.refresh();

            selectedPriority.update(); // Aktualisiere in Datenbank

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

        Priority.printToFile(priorityList);
        this.selectedPriority = null;
        priorityTextField.clear();
    }

    public void deletClicked(ActionEvent actionEvent) {
        priorityTextField.clear();
        prioritylistView.getItems().remove(selectedPriority);

        selectedPriority.delete();
    }
}
