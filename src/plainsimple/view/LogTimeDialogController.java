package plainsimple.view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType; // todo: get Alert working
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import plainsimple.DataHandler;
import plainsimple.Session;

import java.util.Calendar;
import java.util.InputMismatchException;

/* Controller class for "Log a Time" Dialog */
public class LogTimeDialogController {

    @FXML private ChoiceBox<String> month_field = new ChoiceBox<String>();
    @FXML private TextField year_field;
    @FXML private TextField activity_field;
    @FXML private TextField day_field;
    @FXML private TextField min_field;
    @FXML private TextField hrs_field;

    private Session session;
    private Stage dialogStage;
    private boolean okClicked = false;

    /* initializes class
     * called automatically once fxml file has been loaded */
    @FXML private void initialize() {
        month_field.setItems(FXCollections.observableArrayList( /* populate choicebox with names of months */
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        ));
    }
    /* sets stage of dialog  */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    /* returns whether user has clicked the "Ok" button */
    public boolean isOkClicked() {
        return okClicked;
    }
    /* handles user pressing "Ok" button */
    @FXML private void handleOk() {
        /* input validation */
        if (isInputValid()) {  /* isInputValid() will take care of any errors */
            okClicked = true;
            session.setActivity(activity_field.getText());
            session.setTimePracticed(getPracticeTime());
            try {
                session.setDate(getDate());
            } catch(Exception e) {}
                okClicked = true;
                dialogStage.close();
            }
            dialogStage.close();
    }
    /* handles user pressing "Cancel" button */
    @FXML private void handleCancel() {
        dialogStage.close();
    }
    /* handles user pressing "Set to Today" button */
    @FXML private void handleSetToToday() {
        Calendar current_date = Calendar.getInstance();
        day_field.setText(Integer.toString(current_date.get(Calendar.DATE))); // todo: set choicebox to correct month
        month_field.setValue(intToMonth(current_date.get(Calendar.MONTH)));
        year_field.setText(Integer.toString(current_date.get(Calendar.YEAR)));
    }
    /* validates user input in text fields and generates an alert if errors are found */
    private boolean isInputValid() {
        String error_message = "";
        if(isEmpty(activity_field))
            error_message += "Missing Activity Name";
        if(isEmpty(min_field) && isEmpty(hrs_field)) { /* at least one must have text */
            error_message += "Missing Time Practiced";
        } else if(!isEmpty(min_field)) { /* validate field if it has text */
            try {
                Integer.parseInt(min_field.getText());
            } catch(NumberFormatException e) {
                error_message += "Min Practiced Must be an Integer";
            }
        } else if(!isEmpty(hrs_field)) { /* validate field if it has text */
            try {
                Integer.parseInt(hrs_field.getText());
            } catch (NumberFormatException e) {
                error_message += "Hrs Practiced Must be an Integer";
            }
        }
        if(isEmpty(day_field)) {
            error_message += "Missing Day of Practice Session";
        }
        if(isEmpty(year_field)) {
            error_message += "Missing Year of Practice Session";
        }
        try { /* validate date entered */
            getDate();
        } catch(Exception e) {
            error_message += "Invalid Date Entered";
        }
        if(error_message.length() == 0) {
            return true;
        } else {
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
    /* constructs a Calendar object using information from text fields
     * input should first be validated with isValid() before using this method */
    private Calendar getDate() throws Exception { // todo: how to validate date?
        Calendar date = Calendar.getInstance();
        int year = Integer.parseInt(year_field.getText());
        int month = monthToInt(month_field.getValue());
        int day = Integer.parseInt(day_field.getText());
        date.set(year, month, day);
        return date;
    }
    /* calculates milliseconds practiced using "hrs" and "min" fields
     * input should first be validated with isValid() before using this method */
    private long getPracticeTime() {
        long time = 0;
        if(!isEmpty(hrs_field))
            time += Integer.parseInt(hrs_field.getText()) * 60 * 60 * 1000;
        if(!isEmpty(min_field))
            time += Integer.parseInt(min_field.getText()) * 60 * 1000;
        return time;
    }
    /* takes a month name and returns its associated integer */
    private int monthToInt(String month) throws IndexOutOfBoundsException {
        if(month.equals("January"))
            return 0;
        else if(month.equals("February"))
            return 1;
        else if(month.equals("March"))
            return 2;
        else if(month.equals("April"))
            return 3;
        else if(month.equals("May"))
            return 4;
        else if(month.equals("June"))
            return 5;
        else if(month.equals("July"))
            return 6;
        else if(month.equals("August"))
            return 7;
        else if(month.equals("September"))
            return 8;
        else if(month.equals("October"))
            return 9;
        else if(month.equals("November"))
            return 10;
        else if(month.equals("December"))
            return 11;
        else
            throw new IndexOutOfBoundsException("Error: Invalid Month Entered");
    }
    /* takes a month value and returns its associated String */
    private String intToMonth(int month) {
        switch(month) {
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November";
            case 11:
                return "December";
            default:
                return "Error"; // todo: throw error?
        }
    }
 }
