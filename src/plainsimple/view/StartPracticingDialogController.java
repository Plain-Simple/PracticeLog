package plainsimple.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import plainsimple.Session;

/* Dialog to display a countup/countdown clock for
 * user to time an practice session */
public class StartPracticingDialogController {

    @FXML private TextField sec_field;
    @FXML private TextField min_field;
    @FXML private TextField hr_field;

    @FXML private Button startStop_button;

    @FXML private ToggleGroup group_1;

    @FXML private RadioButton count_down;
    @FXML private RadioButton count_up;

    private Stage dialogStage;
    private Session session;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /* Sets stage of StartPracticing dialog */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /* sets Session to be created in dialog */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /* Handles user pressing startStop_button, which controls the clock */
    @FXML private void handleStartStop() {

    }

    /* Handles user pressing count_up radiobutton
     * This sets all fields to "00" and makes them non-editable */
    @FXML private void handleCountUp() {

    }

    /* Handles user pressing count_down radiobutton
     * This makes all fields editable and selects the hour field */
    @FXML private void handleCountDown() {

    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
