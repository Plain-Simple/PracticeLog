package plainsimple.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import plainsimple.MainApp;

/* Controller Class for the MainScreen */
public class MainScreenController {

    @FXML private FlowPane recentActivity_pane;
    @FXML private Button logTime_button; // todo: get rid of fxml buttons that aren't used. A handler class is sufficient
    @FXML private Button showAnalytics_button;
    @FXML private Text hrs_365days;
    @FXML private Button startPracticing_button;
    @FXML private Text hrs_today;
    @FXML private Text hrs_7days;
    @FXML private Button setGoal_button;
    @FXML private Text hrs_30days;
    @FXML private Button showLog_button;
    @FXML private FlowPane goals_pane;

    private MainApp mainApp; /* reference to main application */

    /* constructor */
    public MainScreenController() {}

    /* initializes class
     * called automatically once fxml file has been loaded */
    @FXML private void initialize() {

    }
    /* set main app */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    /* handles user pressing the "Start Practicing!" button */
    @FXML private void handleStartPracticing() {

    }
    /* handles user pressing the "Log a Time" button */
    @FXML private void handleLogTime() {

    }
    /* handles user pressing the "Set a Goal" button */
    @FXML private void handleSetGoal() {

    }
    /* handles user pressing the "See Full Log" button */
    @FXML private void handleShowLog() {

    }
    /* handles user pressing the "More Detailed Analytics" button */
    @FXML private void handleShowAnalytics() {

    }
    /* sets value of hours practiced today */
    public void setHoursToday(double hours) { hrs_today.setText(Double.toString(hours)); }
    /* sets value of hours practiced last 7 days */
    public void setHours7Days(double hours) { hrs_7days.setText(Double.toString(hours));}
    /* sets value of hours practiced last 30 days */
    public void setHours30Days(double hours) { hrs_30days.setText(Double.toString(hours));}
    /* sets value of hours practiced last 365 days */
    public void setHours365Days(double hours) { hrs_365days.setText(Double.toString(hours));}
}
