package fxmls.comment;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class commentController {
    public Stage backToStartStage;
    public Button backToStartButton;

    public void confirmClicked(ActionEvent actionEvent) {
    }

    public void backToStartClicked(ActionEvent actionEvent) {
        backToStartStage = (Stage) backToStartButton.getScene().getWindow();
        backToStartStage.close();
    }

    public void deleteClicked(ActionEvent actionEvent) {
    }
}
