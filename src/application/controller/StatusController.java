package application.controller;

import application.model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.util.Comparator;

public class StatusController {
    public ListView statusListView;
    public File statusFileToOpen = new File("stati.csv");
    public TextField statusNameField;

    ObservableList<Status> statusList = FXCollections.observableArrayList();

    Comparator<? super Status> coparatorStatus_byName = (Comparator<Status>) (o1, o2) -> o1.statusName.compareToIgnoreCase(o2.statusName);

public void initialize(){
    String s = "";

    try {
        BufferedReader br =  new BufferedReader(new FileReader(statusFileToOpen));

        while ((s = br.readLine()) != null) {
            String[] words = s.split(";");

            Status status = new Status();
            status.statusName = words[0];

            statusList.add(status);
        }
        br.close();

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

    statusList.sort(coparatorStatus_byName);
    statusListView.setItems(statusList);

}

    public void statusItemClicked() {
    if(statusListView.getSelectionModel().getSelectedItem() != null){
        statusNameField.setText(statusListView.getSelectionModel().getSelectedItem().toString());
    }
    }
}
