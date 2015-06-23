package plainsimple.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import plainsimple.Session;
import plainsimple.StopWatch;

import java.time.LocalTime;

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
    private StopWatch stopWatch;
    private boolean clockRunning = false;
    private boolean okClicked = false;

    /* Initialize radiobuttons in ToggleGroup */
    @FXML private void initialize() {
        group_1 = new ToggleGroup();
        count_up.setToggleGroup(group_1);
        count_down.setToggleGroup(group_1);
    }

    /* Sets stage of StartPracticing dialog */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /* Sets Session to be created in dialog */
    public void setSession(Session session) {
        this.session = session;
    }

    /* Sets StopWatch to control clock */
    public void setStopWatch(StopWatch stopWatch) {
        this.stopWatch = stopWatch;
    }

    /* Updates hr_field to display hours on clock */
    public void updateHrs(int hours) {
        if(hours < 10)
            hr_field.setText("0" + hours);
        else
            hr_field.setText(Integer.toString(hours));
    }

    /* Updates min_field to display minutes on clock */
    public void updateMin(int minutes) {
        if(minutes < 10)
            min_field.setText("0" + minutes);
        else
            min_field.setText(Integer.toString(minutes));
    }

    /* Updates sec_field to display seconds on clock */
    public void updateSec(int seconds) {
        if(seconds < 10)
            sec_field.setText("0" + seconds);
        else
            sec_field.setText(Integer.toString(seconds));
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
            stopWatch.stop();
            /* Re-enable radio buttons */
            toggleRadioButtons();

        } else {
            /* Clock was not running - start it */
            startStop_button.setText("Stop");
            clockRunning = true;
            stopWatch.setStartTime(getTime());
            stopWatch.start();
            /* Disable radio buttons  */
            toggleRadioButtons();
        }
    }

    /* Handles user pressing "Log Time" button, which opens up a LogTimeDialog */
    @FXML private void handleLogTime() {

    }

    /* Handles user pressing "Reset" button, which resets clock to last set time */
    @FXML private void handleReset() {
        setTime(stopWatch.getStartTime());
    }

    /* Extracts data from TextFields and returns time as a LocalTime object
     * @return set_time  */
    private LocalTime getTime() { // todo: error-handling
        int hours = Integer.parseInt(hr_field.getText());
        int min = Integer.parseInt(min_field.getText());
        int seconds = Integer.parseInt(sec_field.getText());
        return LocalTime.of(hours, min, seconds);
    }

    /* Sets TextFields to time fields
     * @param time */
    private void setTime(LocalTime time) {
        updateHrs(time.getHour());
        updateMin(time.getMinute());
        updateSec(time.getSecond());
    }

     /* Handles user pressing count_up radiobutton
     * This resets all fields to "00" and makes them non-editable */
    @FXML private void handleCountUp() {
        stopWatch.setCountUp(true);
        hr_field.setText("00");
        hr_field.setEditable(false);
        min_field.setText("00");
        min_field.setEditable(false);
        sec_field.setText("00");
        sec_field.setEditable(false);
    }

    /* Handles user pressing count_down radiobutton
     * This makes all fields editable and selects the hour field */
    @FXML private void handleCountDown() {
        stopWatch.setCountUp(false);
        hr_field.setEditable(true);
        min_field.setEditable(true);
        sec_field.setEditable(true);
        hr_field.requestFocus();
    }

    /* Toggles enabled state of count_up and count_down RadioButtons */
    private void toggleRadioButtons() {
        count_up.setDisable(!count_up.isDisable());
        count_down.setDisable(!count_down.isDisable());
    }
}
