package application.controller;

import application.MyFXMLLoader;
import javafx.application.Platform;
import javafx.event.ActionEvent;

public class Controller {

    public void editStatiClicked(ActionEvent actionEvent) {
        MyFXMLLoader loader = new MyFXMLLoader();
        loader.loadFXML("view/status.fxml", "Stati bearbeiten");
    }

    public void editPrioritiesClicked(ActionEvent actionEvent) {
        MyFXMLLoader loader = new MyFXMLLoader();
        loader.loadFXML("view/priority.fxml", "Prioritäten bearbeiten");
    }

    public void closeClicked(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void editUserClicked() {
        MyFXMLLoader loader = new MyFXMLLoader();
        loader.loadFXML("view/user.fxml", "Benutzer bearbeiten");
    }

    public void editDepartmentClicked() {
        MyFXMLLoader loader = new MyFXMLLoader();
        loader.loadFXML("view/department.fxml", "Abteilungen bearbeiten");
    }
}
