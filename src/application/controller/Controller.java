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
    private  Ticket selectedTicket = new Ticket();


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
        contentPane.getChildren().clear();
        contentPane.getChildren().add(root);


        active = (TicketController) loader.getController();

        selectedTicket = ticketListView.getSelectionModel().getSelectedItem();
        selectedTicket.status.statusID = ticketListView.getSelectionModel().getSelectedIndex() + 1;
        selectedTicket.priority.priorityID = ticketListView.getSelectionModel().getSelectedIndex() + 1;

        if (selectedTicket != null) {
            active.setTicket(selectedTicket);
        }

    }

    public void initialize() {
        ticketListView.setItems(Ticket.loadList());
        ticketList.setAll(ticketListView.getItems());

        ObservableList<Status> stati = Status.loadStatusList();
        Status s = new Status();
        s.statusName = "Status wählen";
        s.statusID = -1;
        stati.add(0, s);
        statusFilterBox.setItems(stati);
        statusFilterBox.getSelectionModel().select(s);

        ObservableList<Priority> priority = Priority.loadList();
        Priority p = new Priority(-1, "Priorität wählen");
        priority.add(0, p);
        priorityFilterBox.setItems(priority);
        priorityFilterBox.getSelectionModel().select(p);

        allTickets = new ArrayList<>(ticketListView.getItems());
    }

    public void Filter() {
        ObservableList<Ticket> filter = FXCollections.observableArrayList(allTickets);
        Status s = statusFilterBox.getSelectionModel().getSelectedItem();
        Priority p = priorityFilterBox.getSelectionModel().getSelectedItem();
        boolean alreadyFiltered = false;

        if (s != null && s.statusName != null) {
            if(!(statusFilterBox.getValue().statusName.equals("Status wählen"))){
                filter.removeIf(t -> !t.status.statusName.equals(s.statusName));
                alreadyFiltered = true;
            } else{
                filter = FXCollections.observableArrayList(allTickets);

            }
        }
        if (p != null && p.priorityName != null) {
            if(!(priorityFilterBox.getValue().priorityName.equals("Priorität wählen"))){
                filter.removeIf(t -> !t.priority.priorityName.equals(p.priorityName));
            } else if (!alreadyFiltered){
              filter = FXCollections.observableArrayList(allTickets);
            }
        }
        try{
            filter.removeIf(t -> !t.ticketName.toLowerCase().contains(ticketNameSearchField.getText().toLowerCase()));
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

        try{
            filter.removeIf(t -> !t.ticketName.toLowerCase().contains(ticketNameSearchField.getText().toLowerCase()));
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
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
        for (Ticket t1 : ticketList) {
            if (t1.ticketID == selectedTicket.ticketID) {
                ticketList.remove(selectedTicket);

                for (int i = 1; i <= ticketList.size();++i) {
                    ticketList.get(i-1).ticketID = i;
                }

                Ticket.printToFile(ticketList);

                active.setTicket(null);
                ticketListView.setItems(ticketList);
                ticketListView.refresh();

                break;
            }
        }
    }

    public void saveClicked(ActionEvent actionEvent) {
        MyFXMLLoader loader = new MyFXMLLoader();
        loader.loadFXML("view/ticket.fxml");

        Ticket ticket = new Ticket();
        ticket.status = new Status();
        ticket.priority = new Priority();

        ticket.ticketName = active.ticketNameField.getText();
        ticket.ticketBeschreibung = active.ticketDescField.getText();
        ticket.ticketID = ticketList.size() + 1;
        ticket.status.statusName = active.ticketStatusComboBox.getSelectionModel().getSelectedItem().toString();
        ticket.priority.priorityName = active.ticketPriorityComboBox.getSelectionModel().getSelectedItem().toString();

        if (ticketListView.getSelectionModel().getSelectedItem() == null) {
            ticketList.add(ticket);
        } else {
            for (Ticket t : ticketList) {
                if (t.ticketID == ticketListView.getSelectionModel().getSelectedIndex()) {
                    ticketList.set(t.ticketID, ticket);
                }
            }
        }

        ticketListView.setItems(ticketList);
        ticketListView.refresh();
        Ticket.printToFile(ticketList);
    }

    public void newClicked(ActionEvent actionEvent) {
        MyFXMLLoader loader = new MyFXMLLoader();
        Parent root = loader.loadFXML("view/ticket.fxml");
        AnchorPane.setBottomAnchor(root, 0.0);
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);

        contentPane.getChildren().clear();
        contentPane.getChildren().add(root);

        ticketListView.getSelectionModel().select(-1);

        active = (TicketController) loader.getController();
        active.setTicket(null);
    }
}


