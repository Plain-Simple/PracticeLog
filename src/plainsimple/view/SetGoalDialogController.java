package plainsimple.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import plainsimple.Goal;

/* Controller class for "Set a Goal" dialog, which allows user to define
 * a new Goal */
public class SetGoalDialogController {

    @FXML private RadioButton targetSessions_button;
    @FXML private RadioButton specificActivity_button;
    @FXML private RadioButton noTimeRange_button;
    @FXML private RadioButton targetTime_button;
    @FXML private RadioButton timeRange_button;
    @FXML private RadioButton repeat_button;
    @FXML private RadioButton anyActivity_button;

    @FXML private ToggleGroup group_1;
    @FXML private ToggleGroup group_3;
    @FXML private ToggleGroup group_2;

    @FXML private TextField activity_name;
    @FXML private TextField goalTime_hrs;
    @FXML private TextField goalTime_min;
    @FXML private TextField goalSessions;

    @FXML private DatePicker endDate_picker;
    @FXML private DatePicker startDate_picker;

    @FXML private ChoiceBox<String> timeRange_choice;

    private Stage dialogStage;
    private Goal goal;
    private boolean okClicked = false;

    /* Initializes ChoiceBox, adds listeners to RadioButtons, and customizes Datepickers
     * Called once SetGoalDialog.fxml has been loaded */
    @FXML private void initialize() {
        // todo: initialization code
    }

    /* Sets dialog stage
     * @param dialogStage */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /* Sets Goal to be created/edited in the dialog and sets fields to reflect
     * attributes of this Goal
     * @param goal */
    public void setGoal(Goal goal) {
        this.goal = goal;

        if(goal.specifiesActivity()) {
            specificActivity_button.setSelected(true);
            activity_name.setText(goal.getActivity());
        } else {
            anyActivity_button.setSelected(true);
        }

        if(goal.specifiesTimeLimit()) {
            timeRange_button.setSelected(true);
            startDate_picker.setValue(goal.getStartDate());
            endDate_picker.setValue(goal.getEndDate());
            // todo: set choicebox
        } else {
            noTimeRange_button.setSelected(true);
        }

        if(goal.specifiesTime()) {
            targetTime_button.setSelected(true);
            if(goal.getTime().getHour() != 0)
                goalTime_hrs.setText(Integer.toString(goal.getTime().getHour()));
            if(goal.getTime().getMinute() != 0)
                goalTime_min.setText(Integer.toString(goal.getTime().getMinute()));
        } else {
            targetSessions_button.setSelected(true);
            goalSessions.setText(Integer.toString(goal.getSessions()));
        }

        if(goal.isRecurring())
            repeat_button.setSelected(true);
    }

    /* Returns true if the user clicked OK and goal was created, false otherwise
     * @return */
    public boolean isOkClicked() {
        return okClicked;
    }

    /* Handles the user pressing the "Ok" button
     * Checks to make sure input is valid before updating goal with information
     * from fields and closing the dialog  */
    @FXML private void handleOk() {
        if (isInputValid()) {
            

            okClicked = true;
            dialogStage.close();
        }
    }

    /* Handles the user clicking the "Cancel" button
     * Closes the dialog */
    @FXML private void handleCancel() {
        dialogStage.close();
    }

    @FXML private void handleSpecificActivity() {

    }

    @FXML private void handleAnyActivity() {

    }

    @FXML private void handleTimeRange() {

    }

    @FXML private void handleNoTimeRange() {

    }

    @FXML private void handlePracticeTime() {

    }

    @FXML private void handlePracticeSessions() {

    }

    @FXML private void handleRepeat() {

    }

    /* Validates the user input in the fields
     * @return true if the input is valid */
    private boolean isInputValid() {
        String errorMessage = "";


        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
         /*   Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();*/

            return false;
        }
    }
}
