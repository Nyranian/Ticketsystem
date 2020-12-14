package application.controller;

import application.model.Department;
import application.model.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class TicketController {
    public TextField ticketNameField;
    public TextArea ticketDescField;
    public  Ticket ticketControllerTicket = null;
    public ObservableList<Ticket> ticketList = FXCollections.observableArrayList();


    public void initialize(){
        ticketList = Ticket.openFile();
        ticketNameField.setText("");
        ticketDescField.setText("");
    }

    public void setTicket(Ticket ticket){
        ticketNameField.setText(ticket.ticketName);
        ticketDescField.setText(ticket.ticketBeschreibung);
        ticketControllerTicket = ticket;
    }


    public void saveClicked(ActionEvent actionEvent) {
        if (this.ticketControllerTicket != null) {
            ticketControllerTicket.ticketName = ticketNameField.getText();
            ticketControllerTicket.ticketBeschreibung = ticketDescField.getText();

            //refresh von Listview

        } else {
            Ticket ticket = new Ticket();
            ticket.ticketID = (ticketList.get(ticketList.size() - 1).ticketID) + 1;
            ticket.ticketName = ticketNameField.getText();

            ticketList.add(ticket);
        }
        Ticket.printToFile(ticketList);
       // this.ticketControllerTicket = null;
       // ticketNameField.clear();
       // ticketDescField.clear();
    }



    public void closeClicked(ActionEvent actionEvent) {
    }
}
