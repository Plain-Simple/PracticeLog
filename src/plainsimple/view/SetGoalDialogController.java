package plainsimple.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import plainsimple.Goal;
import plainsimple.util.DatePickerUtil;
import plainsimple.util.TimeUtil;

import java.time.LocalDate;

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
    @FXML private ToggleGroup group_2;
    @FXML private ToggleGroup group_3;

    @FXML private TextField activity_name;
    @FXML private TextField goalTime_hrs;
    @FXML private TextField goalTime_min;
    @FXML private TextField goalSessions;

    @FXML private DatePicker endDate_picker;
    @FXML private DatePicker startDate_picker;

    @FXML private ComboBox<String> timeRange_choice;

    private Stage dialogStage;
    private Goal goal;
    private boolean okClicked = false;

    /* Initializes ChoiceBox, adds RadioButtons to togglegroups, and customizes Datepickers
     * Called once SetGoalDialog.fxml has been loaded */
    @FXML private void initialize() {
        /* Add choices to timeRange_choice ComboBox */
        timeRange_choice.setItems(FXCollections.observableArrayList("Custom Selection",
                "Today", "Tomorrow", "1 Week", "30 Days", "This Month", "365 Days", "This Year"));

        /* Add a Listener to detect selection changes and call a handler method */
        timeRange_choice.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        handleComboBox(new_value.intValue());
                    }
                });

        /* Disable all days before today in startDate_picker */
        startDate_picker.setDayCellFactory(DatePickerUtil.getDayCellFactoryBefore(LocalDate.now()));
        startDate_picker.setValue(LocalDate.now());

        /* Disable all days before value of startDate_picker so Goal can not end before it starts */
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isBefore(startDate_picker.getValue())) {
                            setDisable(true);
                        }
                    }
                };
            }
        };
        endDate_picker.setDayCellFactory(dayCellFactory);

        /* Set toggle groups */
        specificActivity_button.setToggleGroup(group_1);
        anyActivity_button.setToggleGroup(group_1);

        timeRange_button.setToggleGroup(group_2);
        noTimeRange_button.setToggleGroup(group_2);

        targetTime_button.setToggleGroup(group_3);
        targetSessions_button.setToggleGroup(group_3);
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
            handleAnyActivity();
        }

        if(goal.specifiesTimeLimit()) {
            timeRange_button.setSelected(true);
            startDate_picker.setValue(goal.getStartDate());
            endDate_picker.setValue(goal.getEndDate());
            // todo: set choicebox
        } else {
            noTimeRange_button.setSelected(true);
            handleNoTimeRange();
        }

        if(goal.specifiesTime()) {
            targetTime_button.setSelected(true);
            handlePracticeTime();
            if(goal.getTargetTime().getHour() != 0)
                goalTime_hrs.setText(Integer.toString(goal.getTargetTime().getHour()));
            if(goal.getTargetTime().getMinute() != 0)
                goalTime_min.setText(Integer.toString(goal.getTargetTime().getMinute()));
        } else {
            targetSessions_button.setSelected(true);
            handlePracticeSessions();
            goalSessions.setText(Integer.toString(goal.getTargetSessions()));
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

            /* Make sure Goal is initialized */
            if(goal == null)
                goal = new Goal();

            if(specificActivity_button.isSelected()) {
                goal.setHasActivity(true);
                goal.setActivity(activity_name.getText());
            } else {
                goal.setHasActivity(false);
            }

            if(timeRange_button.isSelected()) {
                goal.setStartDate(startDate_picker.getValue());
                goal.setEndDate(endDate_picker.getValue());
            }

            if(targetTime_button.isSelected()) {
               goal.setTargetTime(TimeUtil.stringsToTime(goalTime_hrs.getText(), goalTime_min.getText()));
            } else {
                goal.setTargetSession(Integer.parseInt(goalSessions.getText()));
            }

            if(repeat_button.isSelected()) {
                goal.setRecurring(true);
            }

            dialogStage.close();
        }
    }

    /* Handles the user pressing the "Cancel" button
     * Closes the dialog */
    @FXML private void handleCancel() {
        dialogStage.close();
    }

    /* Handles the user pressing the "Any Activity" RadioButton */
    @FXML private void handleAnyActivity() {
        activity_name.setDisable(true);
    }

    /* Handles the user pressing the "Specific Activity" RadioButton */
    @FXML private void handleSpecificActivity() {
        activity_name.setDisable(false);
    }

    /* Handles the user pressing the "No Time Range" RadioButton */
    @FXML private void handleNoTimeRange() {
        timeRange_choice.setDisable(true);
        startDate_picker.setDisable(true);
        endDate_picker.setDisable(true);
    }

    /* Handles the user pressing the "Time Range" RadioButton */
    @FXML private void handleTimeRange() {
        timeRange_choice.setDisable(false);
    }

    /* Handles the user pressing the "Target Total Practice Time" RadioButton */
    @FXML private void handlePracticeTime() {
        goalTime_hrs.setDisable(false);
        goalTime_min.setDisable(false);
        goalSessions.setDisable(true);
    }

    /* Handles the user pressing the "Target Number of Sessions" RadioButton */
    @FXML private void handlePracticeSessions() {
        goalSessions.setDisable(false);
        goalTime_hrs.setDisable(true);
        goalTime_min.setDisable(true);
    }

    /* Handles the user choosing an option in timeRange_choice ComboBox
     * If "Custom Range" is chosen, DatePickers are enabled
     * If it is not chosen, DatePickers are disabled, but still display start and end dates
     * @param combo_index value of selected choice in combo_box as given by listener */
    private void handleComboBox(int combo_index) {
        if(combo_index == 0) { /* "Custom Range..." - Enable DatePickers */
            disableDatePickers(false);
        } else {
            disableDatePickers(true);
            /* By default the start of the time limit must be today */
            startDate_picker.setValue(LocalDate.now());
        }

        if(combo_index == 1) { /* "Today" */
            endDate_picker.setValue(LocalDate.now());
        } else if(combo_index == 2) { /* "Tomorrow" */
            endDate_picker.setValue(LocalDate.now().plusDays(1));
        } else if(combo_index == 3) { /* "1 Week" */
            endDate_picker.setValue(LocalDate.now().plusDays(7));
        } else if(combo_index == 4) { /* "30 Days" */
            endDate_picker.setValue(LocalDate.now().plusDays(30));
        } else if(combo_index == 5) { /* "This Month" - end date is first day of next month */
            endDate_picker.setValue(LocalDate.now().plusMonths(1).withDayOfMonth(1));
        } else if(combo_index == 6) { /* "365 Days" */
            endDate_picker.setValue(LocalDate.now().plusDays(365));
        } else if(combo_index == 7) { /* "This Year" */
            endDate_picker.setValue(LocalDate.now().plusYears(1).withDayOfYear(1));
        }
    }

    /* Easily set disable state of both DatePickers
     * @param disable disables if true, enables if false */
    private void disableDatePickers(boolean disable) {
        startDate_picker.setDisable(disable);
        endDate_picker.setDisable(disable);
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
