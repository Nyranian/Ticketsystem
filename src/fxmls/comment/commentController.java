package fxmls.comment;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class commentController {
    public Stage backToStartStage;
    public Button backToStartButton;
    public TextField titleField;
    public TextArea textField;
    public DatePicker dateField;

    public void confirmClicked(ActionEvent actionEvent) {
        titleField.requestFocus();
    }

    public void deleteClicked(ActionEvent actionEvent) {

    }


}
