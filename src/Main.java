import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * @ToDo edit run configuration
     * !! FIRST TRY TO RUN YOU APPLICATION FROM THE LAUNCHER CLASS !!
     *
     *
     *
     * If this does not work try the following:
     * Open "Run" -> "Edit Configurations..."
     * Add this to your VM options (just copy & paste the following line):
     * --module-path "C:\Program Files\Java\javafx-sdk-11.0.2\lib" --add-modules=javafx.controls,javafx.fxml
     */

    /**
     * main method
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene.fxml"));

        /**
         * Lade die FXML Datei
         * --> Hier wird automatisch eine Controller - Instanz erzeugt.
         */
        Parent root = loader.load();

        /**
         * Hole die automatisch erzeugte Controller - Instanz.
         */
        Controller c = loader.getController();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
