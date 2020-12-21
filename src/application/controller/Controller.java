package application.controller;

import application.MyFXMLLoader;
import application.model.Priority;
import application.model.Status;
import application.model.Ticket;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class Controller {

    public ListView<Ticket> ticketListView;
    public AnchorPane contentPane;
    public TextField ticketNameSearchField;
    public ComboBox<Status> statusFilterBox;
    public ComboBox<Priority> priorityFilterBox;
    public ArrayList<Ticket> allTickets;

    public ObservableList<Ticket> ticketList = FXCollections.observableArrayList();
    public TicketController active;


    public void editStatiClicked(ActionEvent actionEvent) {
        MyFXMLLoader loader = new MyFXMLLoader();
        loader.loadFXML("view/status.fxml", "Stati bearbeiten");
    }

    public void editPrioritiesClicked(ActionEvent actionEvent) {
        MyFXMLLoader loader = new MyFXMLLoader();
        loader.loadFXML("view/priority.fxml", "Prioritäten bearbeiten");
    }

    public void closeClicked(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void editUserClicked() {
        MyFXMLLoader loader = new MyFXMLLoader();
        loader.loadFXML("view/user.fxml", "Benutzer bearbeiten");
    }

    public void editDepartmentClicked() {
        MyFXMLLoader loader = new MyFXMLLoader();
        loader.loadFXML("view/department.fxml", "Abteilungen bearbeiten");
    }

    public void ticketListViewClicked(MouseEvent mouseEvent) {
        MyFXMLLoader loader = new MyFXMLLoader();
        Parent root = loader.loadFXML("view/ticket.fxml");
        contentPane.getChildren().add(root);

        TicketController controller = (TicketController) loader.getController();

        Ticket selectedTicket = ticketListView.getSelectionModel().getSelectedItem();
        if (selectedTicket != null) {
            controller.ticketNameField.setText(selectedTicket.ticketName);
            controller.ticketDescField.setText(selectedTicket.ticketBeschreibung);
            active = (TicketController) loader.getController();
            active.setTicket(ticketListView.getSelectionModel().getSelectedItem());
        }
    }

    public void initialize() {
        ticketListView.setItems(Ticket.openFile());
        ticketList.setAll(ticketListView.getItems());

        ObservableList<Status> stati = Status.openFile();
        Status s = new Status();
        s.statusName = "Status wählen";
        s.statusID = -1;
        stati.add(0, s);
        statusFilterBox.setItems(stati);
        statusFilterBox.getSelectionModel().select(s);

        ObservableList<Priority> priority = Priority.openFile();
        Priority p = new Priority();
        p.priorityName = "Priorität wählen";
        p.priorityID = -1;
        priority.add(0, p);
        priorityFilterBox.setItems(priority);
        priorityFilterBox.getSelectionModel().select(p);

        allTickets = new ArrayList<>(ticketListView.getItems());
    }


    public void Filter() {
        ObservableList<Ticket> filter = FXCollections.observableArrayList(allTickets);
        Status s = statusFilterBox.getSelectionModel().getSelectedItem();
        Priority p = priorityFilterBox.getSelectionModel().getSelectedItem();
        String tmp = ticketNameSearchField.getText();

        if (s != null && s.statusName != null) {
            if(!(statusFilterBox.getSelectionModel().getSelectedItem().statusName.equals("Status wählen"))){
                filter.removeIf(t -> !t.Status.statusName.equals(s.statusName));
            }else {
                filter = FXCollections.observableArrayList(allTickets);
            }
        }
        if (p != null && p.priorityName != null) {
            if(!(statusFilterBox.getSelectionModel().getSelectedItem().statusName.equals("Priorität wählen"))){
                filter.removeIf(t -> !t.Priority.priorityName.equals(p.priorityName));
            }else{
                filter = FXCollections.observableArrayList(allTickets);
            }
        }
        if (ticketNameSearchField != null && !tmp.equals("") && tmp.length() > 0) {
            filter.removeIf(t -> !(t.ticketName.toLowerCase().equals(ticketNameSearchField.getText().toLowerCase())));
        }else{
            filter = FXCollections.observableArrayList(allTickets);
        }
        ticketListView.setItems(filter);
    }

    public void ComboBoxFilter(ActionEvent actionEvent) {
        Filter();
    }

    public void TextFieldFilter(KeyEvent keyEvent) {
        Filter();
    }

    public void deleteClicked(ActionEvent actionEvent) {
        // Laden des Tickets
        // Entfernen aus Listview
        // Datei aktualisieren
    }

    public void saveClicked(ActionEvent actionEvent) {
        // Wenn Ticket neu -> laden des Ticekts und hinzufügen zur Liste!
        // Datei aktualisieren
        MyFXMLLoader loader = new MyFXMLLoader();
        loader.loadFXML("view/ticket.fxml");

        TicketController controller = (TicketController) loader.getController();
        Ticket t = new Ticket();

        if (controller.selectedTicket == null) {
            t.ticketName = controller.ticketNameField.getText();
            t.ticketBeschreibung = controller.ticketDescField.getText();
            t.ticketID = 69;
            t.Status.statusName = "TestStatus";
            t.Status.statusID = 69;
            t.Priority.priorityName = "TestPriority";
            t.Priority.priorityID = 69;

            ticketList.add(t);
            Ticket.printToFile(ticketList);
        }


    }

    public void newClicked(ActionEvent actionEvent) {
        MyFXMLLoader loader = new MyFXMLLoader();
        Parent root = loader.loadFXML("view/ticket.fxml");
        AnchorPane.setBottomAnchor(root, 0.0);
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
        contentPane.getChildren().add(root);

        active = (TicketController) loader.getController();
        active.setTicket(null);
    }

}


