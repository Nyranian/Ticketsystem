package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.*;

public class Department {
    public String departmentName;
    public int departmentID;

    public Department(int id, String name) {
        this.departmentName = name;
        this.departmentID = id;
    }

    public Department() {
        this.departmentName = "";
        this.departmentID = 0;
    }

    @Override
    public String toString(){
        return departmentName;
    }

    public  String newCSVLine(){
        return departmentID + ";" + departmentName + "\n";
    }

    public static Department getById(int id){
        Department obj = null;
        try {
            Connection connection = AccessDB.getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM departments WHERE department_id = " +id);

            if(result.next()){
                obj = new Department(result.getInt("department_id"), result.getString("name"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return obj;
    }

    public static ObservableList<Department> loadList(){
        ObservableList<Department> list = FXCollections.observableArrayList();

        try {
            Connection connection = AccessDB.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT  * FROM departments");
            while (result.next()){
                Department d = new Department();
                d.departmentName = result.getString("name");
                d.departmentID = result.getInt("department_id");
                list.add(d);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    /*public static ObservableList<Department> openFile(){
        ObservableList<Department> list = FXCollections.observableArrayList();

        try {
            Connection connection = AccessDB.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT  * FROM departments");
            while (result.next()){
                Department d = new Department();
                d.departmentName= result.getString("name");
                d.departmentID = result.getInt("department_id");
                list.add(d);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

     */

    public static void printToFile(ObservableList<Department> departmentList){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("department.csv"));
            for (Department d : departmentList) {
                bw.write(d.newCSVLine());
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void delete(){
        try{
            Connection connection = AccessDB.getConnection();

            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM departments WHERE  department_id = " + departmentID);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public void update(){
        try {
            Connection connection = AccessDB.getConnection();

            PreparedStatement statement = null;

            statement = connection.prepareStatement("UPDATE departments SET name = ? WHERE department_id = " + departmentID);

            statement.setString(1, departmentName);

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
