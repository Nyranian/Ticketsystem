package application.controller;

import application.model.Ticket;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TicketController {
    public TextField ticketNameField;
    public TextArea ticketDescField;

    public ObservableList<Ticket>

    public void initialize(){
        ticketNameField.setText("");
        ticketDescField.setText("");
    }

    public void setTicket(ObservableList<Ticket>){
        ticketNameField.setText();
    }
}
