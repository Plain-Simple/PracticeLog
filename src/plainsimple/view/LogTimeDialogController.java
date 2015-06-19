package plainsimple.view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType; // todo: get Alert working
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import plainsimple.DataHandler;
import plainsimple.Session;

import java.time.LocalTime;
import java.util.Calendar;

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
    }

    /* sets stage of dialog  */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /* sets Session to be edited in dialog */
    public void setSession(Session session) {
        this.session = session;
        /*activity_field.setText(session.getActivity());
        hrs_field.setText(Integer.toString(session.getTimePracticed().getHour()));
        min_field.setText(Integer.toString(session.getTimePracticed().getMinute()));
        //date_picker.setChronology(); */// todo: set datepicker to correct date
    }

    /* returns whether user has clicked the "Ok" button */
    public boolean isOkClicked() {
        return okClicked;
    }

    /* handles user pressing "Ok" button */
    @FXML
    private void handleOk() {
        if (isInputValid()) {  /* isInputValid() will take care of any input errors */
            okClicked = true;
            if(session == null)
                session = new Session();
            session.setActivity(activity_field.getText());
            session.setTimePracticed(getPracticeTime());
            session.setDate(date_picker.getValue());
            System.out.println("LogTimeDialogController.java:\nActivity:" + activity_field.getText() +
                    "\nTime Practiced:" + getPracticeTime() + "\nDate:" + date_picker.getValue()
                    + "\nLog:" + session.toString());
            System.out.println("Time Practiced:" + session.activityProperty());
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
                Integer.parseInt(min_field.getText());
            } catch(NumberFormatException e) {
                error_message += "Min Practiced Must be an Integer\n";
            }
        } else if(!isEmpty(hrs_field)) { /* validate field if it has text */
            try {
                Integer.parseInt(hrs_field.getText());
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

    /* simple helper function, returns whether a TextField is empty or not */
    private boolean isEmpty(TextField field) {
        return field.getText() == null || field.getText().length() == 0;
    }

    /* constructs LocalTime object using entries from "hrs" and "min" fields of dialog
     * entries must me validated first with isValid() method */
    private LocalTime getPracticeTime() {
        int hrs = 0, min = 0;
        if(!isEmpty(hrs_field)) {
            hrs = Integer.parseInt(hrs_field.getText());
        }
        if(!isEmpty(min_field)) {
            min = Integer.parseInt(min_field.getText());
        }
        return LocalTime.of(hrs, min);
    }
 }
