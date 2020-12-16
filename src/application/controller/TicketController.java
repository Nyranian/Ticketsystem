package application.controller;

import application.MyFXMLLoader;
import application.model.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.lang.reflect.InvocationTargetException;


public class TicketController {
    public TextField ticketNameField;
    public TextArea ticketDescField;
    public  Ticket selectedTicket = null;
    public ObservableList<Ticket> ticketList = FXCollections.observableArrayList();


    public void initialize(){
        ticketList = Ticket.openFile();
        ticketNameField.setText("");
        ticketDescField.setText("");
    }

    public void setTicket(Ticket ticket){
        /*selectedTicket = ticket;
        if(this.selectedTicket != null){
            ticketNameField.setText(ticket.ticketName);
            ticketDescField.setText(ticket.ticketBeschreibung);
        }
         */

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
       // this.ticketControllerTicket = null;
       // ticketNameField.clear();
       // ticketDescField.clear();
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
