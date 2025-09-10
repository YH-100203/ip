package mortis;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Mortis mortis;

    private final Image mortisImage = new Image(this.getClass().getResourceAsStream("/images/Mortis.png"));
    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/Traveller.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Called by Main to inject the bot instance.
     * */
    public void setMortis(Mortis mortis) {
        this.mortis = mortis;
        String welcome = "Greetings, mortal. I am Mortis, your eternal assistant.\n"
                + "What dark secret may I help you uncover today?";
        dialogContainer.getChildren().add(
                DialogBox.getMortisDialog(welcome, mortisImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = mortis.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMortisDialog(response, mortisImage)
        );
        userInput.clear();

        if (mortis.isExit(input)) {
            Platform.exit();
        }
    }
}

