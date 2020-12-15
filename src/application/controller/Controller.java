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
        if(selectedTicket != null){
            controller.ticketNameField.setText(selectedTicket.ticketName);
            controller.ticketDescField.setText(selectedTicket.ticketBeschreibung);
        }
    }

    public void initialize(){
        ticketListView.setItems(Ticket.openFile());
        ticketList = Ticket.openFile();
        statusFilterBox.setItems(Status.openFile());
        priorityFilterBox.setItems(Priority.openFile());
    }


    public void FilterClicked(KeyEvent actionEvent) {
        String searchFieldContent = ticketNameSearchField.getText();
        ObservableList<Ticket> filter = ticketList;

        Status s = (Status) statusFilterBox.getSelectionModel().getSelectedItem();
        Priority p = (Priority) priorityFilterBox.getSelectionModel().getSelectedItem();

        if(s != null && s.statusID > 0) {
            filter.removeIf(ticket ->  ticket.Status.statusID != s.statusID);
        }
        if(s != null && s.statusID > 0) {
            filter.removeIf(ticket ->  ticket.Priority.priorityID != p.priorityID);
        }
        if(searchFieldContent != null && searchFieldContent.length() > 0){
            filter.removeIf(ticket ->  !ticket.ticketName.toLowerCase().contains(searchFieldContent.toLowerCase()));
        }
        ticketListView.setItems(filter);
        ticketListView.refresh();
    }

}
