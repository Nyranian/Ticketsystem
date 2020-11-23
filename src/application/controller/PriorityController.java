package application.controller;

import application.model.Priority;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

public class PriorityController {
    public ListView prioritylistView;
    public TextField priorityTextField;
    ObservableList<Priority> priorityList = FXCollections.observableArrayList();

    File priorityFileToOpen = new File(System.getProperty("user.dir") + "\\priorities.csv");//Aktueller ordner + standard Datei

    public  void initialize(){
        String s;

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(priorityFileToOpen))) {

                while ((s = br.readLine()) != null) {
                    // s enthält die gesamte Zeile
                    s = s.replace("\"", ""); // ersetze alle " in der Zeile

                    Priority priority = new Priority();

                     String[] words = s.split(";");
                     priority.priorityName = words[0];

                    priorityList.add(priority); // füge Artikel zur Liste hinzu
                }
            }

        } catch (IOException io) {
            io.printStackTrace();
        }
        priorityList.sort(coparatorPriority_byName);
        prioritylistView.setItems(priorityList);
    }

    public void priorityItemClicked() {
        if(prioritylistView.getSelectionModel().getSelectedItem() != null) {
            priorityTextField.setText(prioritylistView.getSelectionModel().getSelectedItem().toString());
        }
    }

    Comparator<? super Priority> coparatorPriority_byName = (Comparator<Priority>) (o1, o2) -> o1.priorityName.compareToIgnoreCase(o2.priorityName);

}
