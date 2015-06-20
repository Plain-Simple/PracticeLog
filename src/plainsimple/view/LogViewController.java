package plainsimple.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import plainsimple.DateUtil;
import plainsimple.Session;
import plainsimple.SessionUtil;

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
    private void setLogDisplay() {

    }
}
