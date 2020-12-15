package application.controller;

import application.MyFXMLLoader;
import application.model.Priority;
import application.model.Status;
import application.model.Ticket;
import javafx.application.Platform;
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

        controller.ticketNameField.setText(selectedTicket.Name);
        controller.ticketDescField.setText(selectedTicket.Beschreibung);
    }

    public void initialize(){
        ticketListView.setItems(Ticket.loadTicketFile());
        ticketList = Ticket.loadTicketFile();
        statusFilterBox.setItems(Status.openFile());
        priorityFilterBox.setItems(Priority.openFile());
    }


    public void searchFieldEntered(KeyEvent actionEvent) {
        String searchFieldContent = ticketNameSearchField.getText();
        ObservableList<Ticket> searchList = FXCollections.observableArrayList();

        ticketListView.getItems().clear();

        if(searchFieldContent != null){
            for (Ticket t : ticketList) {
                if(t.Name.toLowerCase().contains(searchFieldContent.toLowerCase())){
                    searchList.add(t);
                }
            }
            ticketListView.setItems(searchList);
            ticketList = searchList;
        }
    }

    public void statusFilterClicked(ActionEvent mouseEvent) {
        String statusFilterContent = statusFilterBox.getSelectionModel().getSelectedItem().toString();
        ObservableList<Ticket> statusSearchList = FXCollections.observableArrayList();

        ticketListView.getItems().clear();

        if(statusFilterContent != null){
            for (Ticket t : ticketList) {
                if(t.Status.statusName.toLowerCase().contains(statusFilterContent.toLowerCase())){
                    statusSearchList.add(t);
                }
            }
            ticketListView.setItems(statusSearchList);
            ticketList = statusSearchList;
        }
    }

    public void priorityFilterClicked(ActionEvent contextMenuEvent) {
        String priorityFilterContent = priorityFilterBox.getSelectionModel().getSelectedItem().toString();
        ObservableList<Ticket> prioritySearchList = FXCollections.observableArrayList();

        ticketListView.getItems().clear();

        if(priorityFilterContent != null){
            for (Ticket t : ticketList) {
                if(t.Priority.priorityName.toLowerCase().contains(priorityFilterContent.toLowerCase())){
                    prioritySearchList.add(t);
                }
            }
            ticketListView.setItems(prioritySearchList);
            ticketList = prioritySearchList;
        }
    }
}
