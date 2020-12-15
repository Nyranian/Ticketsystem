package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;

public class Ticket {
    public int ID;
    public String Name;
    public String Beschreibung;
    public Status Status;
    public Priority Priority;

   // public static String ticketText;

    @Override
    public String toString(){
        return Name;
    }

    public static ObservableList<Ticket> loadTicketFile() {
        String s = "";
       // ticketText = "";
        ObservableList<Ticket> result = FXCollections.observableArrayList();

        try {
            BufferedReader br = new BufferedReader(new FileReader("tickets.csv"));

            while ((s = br.readLine()) != null) {
                String[] words = s.split(";");

                Ticket ticket = new Ticket();
                ticket.ID = Integer.parseInt(words[0]);
                ticket.Name = words[1];
                ticket.Beschreibung = words[2];

                ticket.Status = new Status();
                ticket.Status.statusName = words[3];
                ticket.Priority = new Priority();
                ticket.Priority.priorityName = words[4];

               // ticketText += ticket.ID + ";" + ticket.Name + ";" + ticket.Beschreibung + ";\n";;

                result.add(ticket);
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}

