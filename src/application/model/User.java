package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class User {
    public int userID = 0;
    public String userTitle = "";
    public String userName = "";
    public String userStreet = "";
    public short userPlz = 0;
    public Department userDepartment = null;
    public String userCity = "";

    public User(int id, String title, String name, String street, short zip, String city, int departmentId) {
        this.userID = id;
        this.userTitle = title;
        this.userName = name;
        this.userStreet = street;
        this.userPlz = zip;
        this.userCity = city;

        this.userDepartment = Department.getById(departmentId);
    }

    public User() {
        this.userID = 0;
        this.userTitle = null;
        this.userName = null;
        this.userStreet = null;
        this.userPlz = 0;
        this.userCity = null;
        this.userDepartment = null;
    }


    @Override
    public String toString() {
        return userTitle + " " + userName;
    }

    public String newCSVLine() {
        return userID + ";" + userTitle + ";" + userName + ";" + userStreet + ";" + userPlz + ";" + userCity + ";" + userDepartment + "\n";
    }


    public static ObservableList<User> loadUserList() {
        ObservableList<User> list = FXCollections.observableArrayList();

        try {
            Connection connection = AccessDB.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT  * FROM users");
            while (result.next()) {
                User user = new User();
                if (result.getString("title") != null) {
                    user = new User(result.getInt("user_id"), result.getString("title"),
                            result.getString("name"), result.getString("street"),
                            result.getShort("zip"), result.getString("city"),
                            result.getInt("department_id"));
                } else {
                    user = new User(result.getInt("user_id"), "",
                            result.getString("name"), result.getString("street"),
                            result.getShort("zip"), result.getString("city"),
                            result.getInt("department_id"));
                }
                list.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    /**
     * public static ObservableList<User> openFile() {
     * String s;
     * ObservableList<User> result = FXCollections.observableArrayList();
     * try {
     * try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
     * while ((s = br.readLine()) != null) {
     * User user = new User();
     * String[] words = s.split(";");
     * user.userID = Integer.parseInt(words[0]);
     * user.userTitle = words[1];
     * user.userName = words[2];
     * user.userStreet = words[3];
     * user.userPlz = Short.parseShort(words[4]);
     * user.userCity = words[5];
     * user.userDepartment.departmentName = words[6];
     * <p>
     * userDepartmentList.add(words[6]);
     * <p>
     * result.add(user);
     * }
     * }
     * } catch (Exception e) {
     * e.printStackTrace();
     * }
     * return result;
     * }
     * <p>
     * public static void printToFile(ObservableList<User> userList) {
     * try {
     * BufferedWriter bw = new BufferedWriter(new FileWriter("users.csv"));
     * for (User u : userList) {
     * bw.write(u.newCSVLine());
     * }
     * bw.flush();
     * bw.close();
     * } catch (Exception e) {
     * e.printStackTrace();
     * }
     * }
     */
    public void delete() {
        try {
            Connection connection = AccessDB.getConnection();

            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM users WHERE user_id = " + userID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update() {
        try {
            Connection connection = AccessDB.getConnection();

            PreparedStatement statement = null;

            statement = connection.prepareStatement("UPDATE users SET name = ?, title = ?, street = ?, zip = ?, city = ? WHERE user_id = " + userID);

            statement.setString(1, userName);
            statement.setString(2, userTitle);
            statement.setString(3, userStreet);
            statement.setShort(4, userPlz);
            statement.setString(5, userCity);

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static User getById(int id){
        User obj = null;
        try {
            Connection connection = AccessDB.getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM users WHERE user_id = " +id);

            if(result.next()){
                obj = new User(result.getInt("user_id"), result.getString("name"), result.getString("title"),
                        result.getString("street"), result.getShort("zip"), result.getString("city"), result.getInt("department_id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return obj;
    }
}