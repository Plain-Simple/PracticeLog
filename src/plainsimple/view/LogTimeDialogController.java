package plainsimple.view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType; // todo: get Alert working
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import plainsimple.Session;
import plainsimple.util.DatePickerUtil;
import plainsimple.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;

/* Controller class for "Log a Time" dialog, which allows user to enter a
 * new Session to the log */
public class LogTimeDialogController {

    @FXML private TextField activity_field;
    @FXML private TextField min_field;
    @FXML private TextField hrs_field;

    @FXML private DatePicker date_picker;

    private Stage dialogStage;
    private Session session;
    private boolean okClicked = false;

    /* initializes class
     * called automatically once fxml file has been loaded */
    @FXML
    private void initialize() {
        /* disable all DateCells after today's date (see Oracle's "Working With JavaFX UI Components" #26) */
        date_picker.setDayCellFactory(DatePickerUtil.getDayCellFactoryAfter(LocalDate.now()));
    }

    /* sets stage of dialog  */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /* sets Session to be edited in dialog */
    public void setSession(Session session) {
        this.session = session;
        activity_field.setText(session.getActivity());
        if(session.getTimePracticed().getHour() != 0)
            hrs_field.setText(Integer.toString(session.getTimePracticed().getHour()));
        if(session.getTimePracticed().getMinute() != 0)
            min_field.setText(Integer.toString(session.getTimePracticed().getMinute()));
        date_picker.setValue(LocalDate.now());
    }

    /* returns whether user has clicked the "Ok" button */
    public boolean isOkClicked() {
        return okClicked;
    }

    /* Handles user pressing "Ok" button
     * Checks to make sure input is valid before updating Session with information
     * from fields and closing the dialog  */
    @FXML private void handleOk() {
        if (isInputValid()) {  /* isInputValid() will take care of any input errors */
            okClicked = true;

            /* Make sure Session is initialized */
            if(session == null)
                session = new Session();

            session.setActivity(activity_field.getText());
            session.setTimePracticed(TimeUtil.stringsToTime(hrs_field.getText(), min_field.getText()));
            session.setDate(date_picker.getValue());

            dialogStage.close();
        }
    }

    /* handles user pressing "Cancel" button */
    @FXML private void handleCancel() {
        dialogStage.close();
    }

    /* validates user input in text fields and generates an alert if errors are found */
    private boolean isInputValid() {
        String error_message = "";
        if(isEmpty(activity_field))
            error_message += "Missing Activity Name\n";
        if(isEmpty(min_field) && isEmpty(hrs_field)) { /* at least one must have text */
            error_message += "Missing Time Practiced\n";
        } else if(!isEmpty(min_field)) { /* validate field if it has text */
            try {
                int min = Integer.parseInt(min_field.getText());
                if(min < 0)
                    error_message += "Min Practiced Must be Positive\n";
            } catch(NumberFormatException e) {
                error_message += "Min Practiced Must be an Integer\n";
            }
        } else if(!isEmpty(hrs_field)) { /* validate field if it has text */
            try {
                int hrs = Integer.parseInt(hrs_field.getText());
                if(hrs < 0)
                    error_message += "Hrs Practiced Must be Positive\n";
            } catch (NumberFormatException e) {
                error_message += "Hrs Practiced Must be an Integer\n";
            }
        }
        if(error_message.length() == 0) {
            return true;
        } else {
            System.out.println(error_message);
            /* popup error message */ // todo: find workaround for javafx Alert
            //Alert alert = new Alert(AlertType.ERROR);
            //alert.initOwner(dialogStage);
            //alert.setTitle("Error Saving Log");
            //alert.setContentText(error_message);
            //alert.showAndWait();
            return false;
        }
    }

    /* Simple helper function, returns whether a TextField is empty or not */
    private boolean isEmpty(TextField field) {
        return field.getText() == null || field.getText().length() == 0;
    }
 }
