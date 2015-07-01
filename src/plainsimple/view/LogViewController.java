package plainsimple.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import plainsimple.util.DateUtil;
import plainsimple.Session;
import plainsimple.util.SessionUtil;
import plainsimple.util.TimeUtil;

/* Dialog to display full practice log */
public class LogViewController {

    @FXML private Text sub_heading;

    @FXML private TextArea log_display;

    private Stage dialogStage;
    private ObservableList<Session> data;

    /* Sets text in "sub-heading" field and "log_display" using data
     * Called once LogView.fxml has been loaded */
    @FXML private void initialize() {
    }

    /* Sets stage of LogView dialog */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /* Sets data to be displayed in "log_display" */
    public void setData(ObservableList<Session> data) {
        this.data = data;
        setSubHeading();
        log_display.setText(getLogText());
    }

    /* Handles user pressing "Close" button */
    @FXML private void handleClose() {
        dialogStage.close();
    }

    /* Sets "sub_heading" text using data */
    private void setSubHeading() {
        if(data.size() == 0) /* Nothing to show */
            sub_heading.setText("");
        else if(data.size() == 1)
            sub_heading.setText(DateUtil.format(data.get(0).getDate()));
        else
            sub_heading.setText(DateUtil.format(SessionUtil.getOldestDate(data)) +
                    " - " + DateUtil.format(SessionUtil.getNewestDate(data)));
    }

    /* Returns a String with formatted log for display in textarea
     * The first "column" shows the Activity field of each Session.
     * This column is 14 characters long. The second column shows
     * time practiced and is 16 characters long, while the third
     * shows the date practiced and is 8 characters long.
     * There is a three-character space between each column.
     * Padding is calculated by taking the fixed column width,
     * subtracting the length of the field to be displayed in it,
     * and dividing by two */
    private String getLogText() { // todo: painful to look at
        String log = "   Activity       Time Practiced      Date\n";
        log += "--------------   ----------------   --------\n";

        /* If no Sessions to show display "No Data to Show" centered in 4th row */
        if(data.isEmpty())
            return log + "\n      Log is Empty: No Sessions to Show        ";
        else {
        /* Fill the table with information from data. Each column is centered */
            Session this_session;
            String activity;
            String date;
            String time;
            for (int i = 0; i < data.size(); i++) {
                this_session = data.get(i);
                activity = this_session.getActivity();
                date = this_session.getDate().toString();
                time = TimeUtil.format(this_session.getTimePracticed());
                /* Determine how many spaces before and after each field to leave blank */
                int firstColPadding = (14 - activity.length()) / 2;
                int secondColPadding = (16 - time.length()) / 2;
                int thirdColPadding = (8 - date.length()) / 2;

                log += buildSpace(firstColPadding) + activity + buildSpace(firstColPadding);
                log += "   ";
                log += buildSpace(secondColPadding) + time + buildSpace(secondColPadding);
                log += "   ";
                log += buildSpace(thirdColPadding) + date + buildSpace(thirdColPadding);
                log += "\n";
            }
            return log;
        }
        // todo: look into field.setPrefWidth(TextUtils.computeTextWidth(field.getFont(),field.getText(), 0.0D) + 10);
    }

    /* Returns a String of " " of specified length
     * @param length the length of the String to be created */
    private String buildSpace(int length) {
        String spaces = "";
        for(int i = 0; i < length; i++)
            spaces += " ";
        return spaces;
    }
}
