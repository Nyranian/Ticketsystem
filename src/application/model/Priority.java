package application.model;

public class Priority {
    public String priorityName;
    public  String priorityID;
    @Override
    public String toString() {
        return priorityName;
    }
    public  String newCSVLine(){
        return priorityID + ";" + priorityName+ "\n";
    }
}
