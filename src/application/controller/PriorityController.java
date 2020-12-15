package application.controller;

import application.model.Priority;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PriorityController {
    public ListView prioritylistView;
    public TextField priorityTextField;
    public Button cancelButton;
    private Priority selectedPriority = null;
    ObservableList<Priority> priorityList = FXCollections.observableArrayList();

    public void initialize() {
        priorityList = Priority.openFile();
        prioritylistView.setItems(priorityList);

    }

    public void priorityItemClicked() {
        Priority selected = (Priority) prioritylistView.getSelectionModel().getSelectedItem();

        if (selected != null) {
            this.selectedPriority = selected;
            priorityTextField.setText(prioritylistView.getSelectionModel().getSelectedItem().toString());
        }
    }


    public void cancelClicked() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    public void speichernCLicked(ActionEvent actionEvent) {
        if (this.selectedPriority != null) {
            selectedPriority.priorityName = priorityTextField.getText();

            prioritylistView.refresh();
            System.out.println("Daten aktualisieren");

        } else {
                // erzeuge neuen Priorität, füge ihn in die ListView ein
                // und speichere alles in die Datei
                Priority priority = new Priority(); //neuer Speicherplatz für artikel wird reserviert
                priority.priorityID = priorityList.get(priorityList.size() - 1).priorityID + 1;
                priority.priorityName = priorityTextField.getText();

                priorityList.add(priority);

                System.out.println("Neue Priorität");
        }
        Priority.printToFile(priorityList);
        this.selectedPriority = null;
        priorityTextField.clear();
    }


}
