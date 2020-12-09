package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;

public class User {
    public String userID="";
    public String userTitle="";
    public String userName="";
    public String userStreet="";
    public short userPlz = 0;
    public String userDepartment="";
    public  String userPlace="";

    @Override
    public String toString() {
        return userTitle +" "+ userName +" "+ userDepartment;
    }
    public  String newCSVLine(){
        return userID + ";" + userTitle+ ";"+userName+ ";"+userStreet+ ";"+userPlz+ ";"+userPlace+ ";"+userDepartment + "\n";
    }
    public static ObservableList<User> openFile(){
        String s;
        ObservableList<User> result = FXCollections.observableArrayList();

        try {
            try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {

                while ((s = br.readLine()) != null) {

                    User user = new User();

                    String[] words = s.split(";");
                    user.userID = words[0];
                    user.userTitle = words[1];
                    user.userName = words[2];
                    user.userStreet = words[3];
                    user.userPlz = Short.parseShort(words[4]);
                    user.userPlace = words[5];
                    user.userDepartment = words[6];

                    result.add(user); // füge Priorität zur Liste hinzu
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
