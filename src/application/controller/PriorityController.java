package application.controller;

import application.model.Priority;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.io.*;

public class PriorityController {
    public ListView prioritylistView;
    public TextField priorityTextField;
    private Priority selectedPriority = null;
    ObservableList<Priority> priorityList = FXCollections.observableArrayList();

    File priorityFileToOpen = new File(System.getProperty("user.dir") + "\\priorities.csv");//Aktueller ordner + standard Datei

    public  void initialize(){
        String s;

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(priorityFileToOpen))) {

                while ((s = br.readLine()) != null) {

                    Priority priority = new Priority();

                    String[] words = s.split(";");
                    priority.priorityID = words[0];
                    priority.priorityName = words[1];

                    priorityList.add(priority); // füge Priorität zur Liste hinzu
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        prioritylistView.setItems(priorityList);

    }

    public void priorityItemClicked() {
        Priority selected = (Priority) prioritylistView.getSelectionModel().getSelectedItem();

        if (selected != null) {
            this.selectedPriority = selected;
            priorityTextField.setText(prioritylistView.getSelectionModel().getSelectedItem().toString());
        }
    }


    public void abbrechenClicked(ActionEvent actionEvent) {
        Platform.exit();
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
                priority.priorityID = String.valueOf(Integer.parseInt(priorityList.get(priorityList.size()-1).priorityID) + 1);
                priority.priorityName = priorityTextField.getText();

                priorityList.add(priority);
                
                System.out.println("Neue Priorität");

        }
        printToFile();
        this.selectedPriority = null;
        priorityTextField.clear();

    }

    private void printToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(priorityFileToOpen));
            for (Priority p : priorityList) {
                bw.write(p.newCSVLine());
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
