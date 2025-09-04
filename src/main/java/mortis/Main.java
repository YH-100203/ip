package mortis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {
    // Pick your default data file
    private final Mortis mortis = new Mortis("data/tasks.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            // inject the Mortis instance into controller
            fxmlLoader.<MainWindow>getController().setMortis(mortis);
            stage.setTitle("Mortis");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
