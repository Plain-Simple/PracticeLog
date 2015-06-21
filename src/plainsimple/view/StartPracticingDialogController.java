package plainsimple.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import plainsimple.Session;

import java.util.Timer;
import java.util.TimerTask;

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
    private int seconds = 0;
    private Timer second_timer;
    private TimerTask task;
    private boolean clockRunning = false;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        group_1 = new ToggleGroup();
        count_up.setToggleGroup(group_1);
        count_down.setToggleGroup(group_1);
        second_timer = new Timer();
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
        hr_field.setEditable(false);
        min_field.setEditable(false);
        sec_field.setEditable(false);

        if(clockRunning) {
            /* Clock was running - stop it */
            startStop_button.setText("Go");
            clockRunning = false;

            /* Stop timer */
            second_timer.cancel();

        } else {
            /* Initialize timer and task */
            task = new TimerTask() {
                @Override public void run() {
                    /* Increments seconds field and updates clock */
                    seconds++;
                    updateClock(seconds);
                }
            };

            startStop_button.setText("Stop");
            clockRunning = true;

            /* Start timer */
            second_timer.schedule(task, 0, 1000);
        }
    }

    private void updateClock(int seconds_elapsed) {
        
    }

    /* Handles user pressing count_up radiobutton
     * This sets all fields to "00" and makes them non-editable */
    @FXML private void handleCountUp() {
        hr_field.setText("  00");
        hr_field.setEditable(false);
        min_field.setText("  00");
        min_field.setEditable(false);
        sec_field.setText("  00");
        sec_field.setEditable(false);
    }

    /* Handles user pressing count_down radiobutton
     * This makes all fields editable and selects the hour field */
    @FXML private void handleCountDown() {
        hr_field.setEditable(true);
        min_field.setEditable(true);
        sec_field.setEditable(true);
        hr_field.requestFocus();
    }
}
