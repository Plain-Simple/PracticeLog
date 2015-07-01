package plainsimple.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import plainsimple.MainApp;
import plainsimple.Session;
import plainsimple.StopWatch;

import java.io.IOException;
import java.time.LocalDate;
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

    /* Returns true if the user saved the Session correctly */
    public boolean isOkClicked() {
        return okClicked;
    }

    /* Handles user pressing startStop_button, which toggles the clock's on/off state */
    @FXML private void handleStartStop() { // todo: toggle function and start/stop functions
        hr_field.setEditable(false);
        min_field.setEditable(false);
        sec_field.setEditable(false);

        if(clockRunning) {
            /* Clock was running - stop it */
            stopClock();
        } else {
            /* Clock was not running - start it */
            startClock();
        }
    }

    /* Stops stopWatch and changes GUI to reflect this */
    private void stopClock() {
        startStop_button.setText("Go");
        clockRunning = false;
        stopWatch.stop();
        /* Re-enable radio buttons */
        disableRadioButtons(false);
    }

    /* Starts stopWatch and changes GUI to reflect this */
    private void startClock() {
        startStop_button.setText("Stop");
        clockRunning = true;
        stopWatch.setStartTime(getTime());
        stopWatch.start();
        /* Disable radio buttons  */
        disableRadioButtons(true);
    }

    /* Handles user pressing "Log Time" button, which opens up a LogTimeDialog */
    @FXML private void handleLogTime() {
        stopClock();
        session.setTimePracticed(stopWatch.getTimeElapsed());
        session.setDate(LocalDate.now());
        try {
            /* Load the fxml file and create a new stage for the popup dialog */
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LogTimeDialog.fxml"));
            AnchorPane page = loader.load();

            /* Create the dialog stage */
            Stage logDialogStage = new Stage();
            logDialogStage.setTitle("Submit an Entry");
            logDialogStage.initModality(Modality.WINDOW_MODAL);
            logDialogStage.initOwner(dialogStage);
            Scene scene = new Scene(page);
            logDialogStage.setScene(scene);

            LogTimeDialogController controller = loader.getController();
            controller.setDialogStage(logDialogStage);
            controller.setSession(session);

            /* Show LogTime dialog and waits until the user closes it */
            logDialogStage.showAndWait();

            okClicked = controller.isOkClicked();

            /* Close clock if user has logged a time successfully */
            if(okClicked)
                dialogStage.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /* Handles user pressing "Reset" button, which resets clock and clock display */
    @FXML private void handleReset() {
        stopClock();
        stopWatch.reset();
    }

    /* Extracts data from TextFields and returns time as a LocalTime object
     * @return set_time  */
    private LocalTime getTime() { // todo: error-handling and use TimeUtil
        int hours = Integer.parseInt(hr_field.getText());
        int min = Integer.parseInt(min_field.getText());
        int seconds = Integer.parseInt(sec_field.getText());
        return LocalTime.of(hours, min, seconds);
    }

     /* Handles user pressing count_up radiobutton
     * This resets all fields to "00" and makes them non-editable */
    @FXML private void handleCountUp() {
        stopWatch.setCountUp(true);
        disableTextFields(true);
        hr_field.setText("00");
        min_field.setText("00");
        sec_field.setText("00");
    }

    /* Handles user pressing count_down radiobutton
     * This makes all fields editable and selects the hour field */
    @FXML private void handleCountDown() {
        stopWatch.setCountUp(false);
        disableTextFields(false);
        hr_field.requestFocus();
    }

    /* Can enable/disable count_up and count_down RadioButtons
     * @param disable true disables fields, false enables fields */
    private void disableRadioButtons(boolean disable) {
        count_up.setDisable(disable);
        count_down.setDisable(disable);
    }

    /* Can enable/disable sec_field, min_field, and hr_field TextFields
     * @param disable true sets fields non-editable, false sets editable */
    private void disableTextFields(boolean disable) {
        hr_field.setEditable(!disable);
        min_field.setEditable(!disable);
        sec_field.setEditable(!disable);
    }
}
