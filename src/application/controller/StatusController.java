package application.controller;

import application.model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

public class StatusController {
    public ListView<Status> statusListView;
    public TextField statusNameField;
    public Button cancelButton;
    String statusText = "";
    String currentItemText;
    int currentIndex;

    ObservableList<Status> statusList = FXCollections.observableArrayList();

    public void initialize() {
        String s = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader("stati.csv"));

            while ((s = br.readLine()) != null) {
                String[] words = s.split(";");

                Status status = new Status();
                status.statusName = words[1];
                status.statusID = Integer.parseInt(words[0]);

                statusText += status.statusID + ";" + status.statusName + ";\n";

                statusList.add(status);
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

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
        if (!statusNameField.getText().isEmpty()) {
            String replacementText = "";

            statusList.get(currentIndex).statusName = statusNameField.getText();
            statusListView.setItems(statusList);
            statusListView.refresh();

            replacementText = statusList.get(currentIndex).statusID + ";" + statusNameField.getText() + ";\n";

            statusText = statusText.replace(currentItemText, replacementText);

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("stati.csv"));

                bw.write(statusText);
                bw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            statusNameField.setText("");

        }
    }

    public void cancelClicked() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
}
