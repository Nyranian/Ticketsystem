package application.model;

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
}
