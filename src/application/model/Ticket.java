package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Ticket {
    public int ticketID;
    public String ticketName;
    public String ticketBeschreibung;
    public Status ticketStatus;
    public Priority ticketPriority;

    @Override
    public String toString() {
        return ticketName;
    }

    public String newCSVLine() {
        return ticketID + ";" + ticketName + ";" + ticketBeschreibung + ";" + ticketBeschreibung + ";" + ticketPriority+ "\n";
    }

    public static ObservableList<Ticket> openFile() {
        String s = "";
        ObservableList<Ticket> result = FXCollections.observableArrayList();

        try {
            BufferedReader br = new BufferedReader(new FileReader("tickets.csv"));

            while ((s = br.readLine()) != null) {
                String[] words = s.split(";");

                Ticket ticket = new Ticket();
                ticket.ticketID = Integer.parseInt(words[0]);
                ticket.ticketName = words[1];
                ticket.ticketBeschreibung = words[2];

                result.add(ticket);
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void printToFile(ObservableList<Ticket> ticketList) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("tickets.csv"));
            for (Ticket t : ticketList) {
                bw.write(t.newCSVLine());
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

