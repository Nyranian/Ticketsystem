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

        /*try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("shutdown -s -t 0");
            System.exit(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        Platform.exit();
    }
}
