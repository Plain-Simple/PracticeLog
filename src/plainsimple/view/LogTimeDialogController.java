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

/* Controller class for "Log a Time" Dialog */
public class LogTimeDialogController {

    @FXML private ChoiceBox<String> month_field;
    @FXML private TextField year_field;
    @FXML private TextField activity_field;
    @FXML private TextField day_field;
    @FXML private TextField min_field;
    @FXML private TextField hrs_field;

    private String file_name; /* name of file to save new log to */
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
    public void setFileName(String file_name) {
        this.file_name = file_name;
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
            DataHandler data_handler = new DataHandler(file_name);
            if(data_handler.isValid()) { // todo: what if it isn't?
                try {data_handler.addSession(getSession()); } catch (Exception e) {}
            }
            dialogStage.close();
        }
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
        if(activity_field.getText() == null || activity_field.getText().length() == 0
                || year_field.getText() == null || year_field.getText().length() == 0
                || day_field.getText() == null || day_field.getText().length() == 0
                || min_field.getText() == null || min_field.getText().length() == 0
                || hrs_field.getText() == null || hrs_field.getText().length() == 0) {
            error_message += "Missing Text Fields!\n";
        }
        try {
            getSession(); /* attempt parsing info */
        } catch(IndexOutOfBoundsException e) {
            error_message += "Invalid Date Entered!\n";
        } catch(Exception e) {
            error_message += "Invalid Input\n";
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
    /* constructs a Session using information entered by the user
     * input should be validated first */
    private Session getSession() throws Exception {
        String activity = activity_field.getText();
        long practice_time = Integer.parseInt(hrs_field.getText()) * 60 * 60 * 1000;
        practice_time += Integer.parseInt(min_field.getText()) * 1000;
        Calendar date = Calendar.getInstance();
        int year = Integer.parseInt(year_field.getText());
        int month = monthToInt(month_field.getValue());
        int day = Integer.parseInt(day_field.getText());
        date.set(year, month, day);
        return new Session(activity, practice_time, date.getTimeInMillis());
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
