package plainsimple.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import plainsimple.util.DateUtil;
import plainsimple.Session;
import plainsimple.util.SessionUtil;

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
        setLogDisplay();
    }

    /* Handles user pressing "Close" button */
    @FXML private void handleClose() {
        dialogStage.close();
    }

    /* Sets "sub_heading" text using data */
    private void setSubHeading() {
        if(data.size() == 0) /* Nothing to show */
            sub_heading.setText("");
        else
            sub_heading.setText(DateUtil.format(SessionUtil.getOldestDate(data)) +
                    " - " + DateUtil.format(SessionUtil.getNewestDate(data)));
    }

    /* Sets "log_display" textarea using data */
    private void setLogDisplay() { // todo: painful to look at
        String log = "";
        Session this_session;
        int firstColumn_width = 0;
        int secondColumn_width = 0;
        int thirdColumn_width = 0;
        /* space between columns */
        int columnSeparation = 5;
        /* total width of table */
        int total_width = 0;

        /* Speed up - If no Sessions to show display "No Data to Show" */
        if(data.isEmpty())
            log = "Log is Empty: No Sessions to Show";
        else {
        /* Establish length of longest String in each column */
            for (int i = 0; i < data.size(); i++) {
                this_session = data.get(i);
                if (this_session.getActivity().length() > firstColumn_width)
                    firstColumn_width = this_session.getActivity().length();
                if (this_session.timePracticedString().length() > secondColumn_width)
                    secondColumn_width = this_session.timePracticedProperty().get().length();
                if (this_session.dateString().length() > thirdColumn_width)
                    thirdColumn_width = this_session.dateString().length();
            }

        /* Format first two rows */
            log = "Activity";
            firstColumn_width += columnSeparation;
            for (int i = 0; i < firstColumn_width; i++)
                log += " ";
            log += "Time Practiced";
            secondColumn_width += columnSeparation;
            for (int i = 0; i < secondColumn_width; i++)
                log += " ";
            log += "Date";
            thirdColumn_width += columnSeparation;
            for (int i = 0; i < thirdColumn_width; i++)
                log += " ";
            log += "\n";
            for (int i = 0; i < total_width; i++)
                log += "-";

        /* Fill the table with information from data */
            for (int i = 0; i < data.size(); i++) {
                log += data.get(i).getActivity();
                for (int j = data.get(i).getActivity().length(); j < firstColumn_width; j++)
                    log += " ";
                log += data.get(i).timePracticedString();
                for (int j = data.get(i).timePracticedString().length(); j < secondColumn_width; j++)
                    log += " ";
                log += data.get(i).dateString() + "\n";
            }
        }
        
        /* Set textarea */
        log_display.setText(log);
    }
}
