package application.controller;

import application.MyFXMLLoader;
import application.model.Priority;
import application.model.Status;
import application.model.Ticket;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Controller {

    public ListView<Ticket> ticketListView;
    public AnchorPane contentPane;
    public TextField ticketNameSearchField;
    public ComboBox statusFilterBox;
    public ComboBox priorityFilterBox;

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
        statusFilterBox.setItems(Status.openFile());
        priorityFilterBox.setItems(Priority.openFile());
    }


    public void Filter() {
        ObservableList<Ticket> filter = ticketList;
        Status s = (Status) statusFilterBox.getSelectionModel().getSelectedItem();
        Priority p = (Priority) priorityFilterBox.getSelectionModel().getSelectedItem();

        if (s != null && s.statusName != null) {
            filter.removeIf(ticket -> !ticket.Status.statusName.equals(s.statusName));
        }
        if (p != null && p.priorityName != null) {
            filter.removeIf(ticket -> !ticket.Priority.priorityName.equals(p.priorityName));
        }

        if (ticketNameSearchField != null && ticketNameSearchField.getText().length() > 0) {
            filter.removeIf(ticket -> !ticket.ticketName.toLowerCase().contains(ticketNameSearchField.getText().toLowerCase()));
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


