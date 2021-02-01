package application.controller;

import application.MyFXMLLoader;
import application.model.Priority;
import application.model.Status;
import application.model.Ticket;
import application.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.*;


public class TicketController {
    public TextField ticketNameField;
    public TextArea ticketDescField;
    public Ticket selectedTicket = null;
    public ObservableList<Ticket> ticketList = FXCollections.observableArrayList();
    public ComboBox<Ticket> ticketOrderComboBox;
    public ComboBox<Status> ticketStatusComboBox;
    public ComboBox<Priority> ticketPriorityComboBox;
    public ListView<User> ticketUserListView;
    public Button addUserButton;
    public ComboBox<User> remainingUsersComboBox;
    public Button removeUserButton;


    public void initialize(){
        ticketList = Ticket.openFile();
        ticketNameField.setText("");
        ticketDescField.setText("");
    }

    public void setTicket(Ticket ticket){
        selectedTicket = ticket;

        if(ticket != null) {
            ticketNameField.setText(ticket.ticketName);
            ticketDescField.setText(ticket.ticketBeschreibung);
            ticketStatusComboBox.setItems(Status.loadStatusList());
            ticketPriorityComboBox.setItems(Priority.loadList());

            for (Status s : ticketStatusComboBox.getItems()) {
                if (s.statusID == (ticket.status.statusID)) {
                    ticketStatusComboBox.getSelectionModel().select(s);
                    break;
                }
            }

            for (Priority p : ticketPriorityComboBox.getItems()) {
                if (p.priorityID == (ticket.priority.priorityID)) {
                    ticketPriorityComboBox.getSelectionModel().select(p);
                    break;
                }
            }
        }else{
            ticketNameField.setText("");
            ticketDescField.setText("");
            ticketStatusComboBox.setItems(Status.loadStatusList());
            ticketPriorityComboBox.setItems(Priority.loadList());
        }

    }

    public Ticket getTicket(){
        selectedTicket.ticketName = ticketNameField.getText();

        return selectedTicket;
    }


    public void saveClicked(ActionEvent actionEvent) {
        if (this.selectedTicket != null) {
            selectedTicket.ticketName = ticketNameField.getText();
            selectedTicket.ticketBeschreibung = ticketDescField.getText();

            try {
                MyFXMLLoader loader = new MyFXMLLoader();
                Controller c = (Controller) loader.getController();
                c.ticketListView.refresh();
            }catch (Exception e){
                e.printStackTrace();
            }


        } else {
            Ticket ticket = new Ticket();
            ticket.ticketID = (ticketList.get(ticketList.size() - 1).ticketID) + 1;
            ticket.ticketName = ticketNameField.getText();

            ticketList.add(ticket);
        }
        Ticket.printToFile(ticketList);
    }



    public void closeClicked(ActionEvent actionEvent) {
        MyFXMLLoader loader = new MyFXMLLoader();
        Parent root = loader.loadFXML("view/scene.fxml");
        Controller c = (Controller) loader.getController();
        c.contentPane.getChildren().clear();
        c.ticketListView.refresh();
        System.out.println("HHHHHHHHHHHHHHHHH");
    }
}
