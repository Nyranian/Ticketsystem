package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
    public String userID = "";
    public String userTitle = "";
    public String userName = "";
    public String userStreet = "";
    public short userPlz = 0;
    public String userDepartment = "";
    public String userPlace = "";
    public static ObservableList<String> userDepartmentList = FXCollections.observableArrayList();

    @Override
    public String toString() {
        return userTitle + " " + userName + " " + userDepartment;
    }

    public String newCSVLine() {
        return userID + ";" + userTitle + ";" + userName + ";" + userStreet + ";" + userPlz + ";" + userPlace + ";" + userDepartment + "\n";
    }

    public static ObservableList<User> loadUserList(){
        ObservableList<User> list = FXCollections.observableArrayList();

        try {
            Connection connection = AccessDB.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT  * FROM users");
            while (result.next()){
                User user = new User();
                user.userID = result.getString("user_id");
                user.userName = result.getString("name");
                user.userPlace = result.getString("city");
                if(result.getString("title")!= null) {
                    user.userTitle = result.getString("title");
                }
                user.userPlz = result.getShort("zip");
                user.userDepartment = result.getString("department_id");
                user.userStreet = result.getString("street");
                list.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public static ObservableList<User> openFile() {
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

                    userDepartmentList.add(words[6]);

                    result.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void printToFile(ObservableList<User> userList) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("users.csv"));
            for (User u : userList) {
                bw.write(u.newCSVLine());
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}