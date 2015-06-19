package plainsimple.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import plainsimple.DataHandler;
import plainsimple.MainApp;
import plainsimple.Session;
import plainsimple.SessionUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

/* Controller Class for the MainScreen */
public class MainScreenController {

    @FXML private TableView<Session> session_table;
    @FXML private TableColumn<Session, String> activity_column;
    @FXML private TableColumn<Session, String> timePracticed_column;
    @FXML private TableColumn<Session, String> date_column;

    @FXML private Text hrs_365days;
    @FXML private Text hrs_today;
    @FXML private Text hrs_7days;
    @FXML private Text hrs_30days;

    @FXML private FlowPane goals_pane;

    /* reference to main application */
    private MainApp mainApp;

    /* constructor
     * called before initialize() method */
    public MainScreenController() {
    }

    /* initializes class
     * called automatically once fxml file has been loaded */
    @FXML private void initialize() {
        activity_column.setCellValueFactory(cellData -> cellData.getValue().activityProperty());
        timePracticed_column.setCellValueFactory(cellData -> cellData.getValue().timePracticedProperty());
        date_column.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
    }

    /* set main app */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        /* add observable list data to session_table */
        session_table.setItems(mainApp.getSessionData());
    }

    /* handles user pressing the "Start Practicing!" button */
    @FXML private void handleStartPracticing() {

    }

    /* handles user pressing the "Log a Time" button */
    @FXML private void handleLogTime() {
        Session temp_session = new Session();
        boolean okClicked = mainApp.showLogTimeDialog(temp_session);
            if (okClicked) {
                /* add Session to sessionData */
                mainApp.getSessionData().add(temp_session);

                /* sort sessionData */
                SessionUtil.sort(mainApp.getSessionData());

                /* recalculate and redisplays analytics */
                updateRecentStats(temp_session);
            }
    }

    /* updates values for time practiced recently */
    private void updateRecentStats(Session added_session) {
        /* calculate number of days between newest-added session and today */
        Period p = Period.between(added_session.getDate(), LocalDate.now());
        int days_since = p.getDays();


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
    public void setHoursToday(double hours) { hrs_today.setText(hours + " Hours"); }

    /* sets value of hours practiced last 7 days */
    public void setHours7Days(double hours) {
        hrs_7days.setText(hours + " Hours");
    }

    /* sets value of hours practiced last 30 days */
    public void setHours30Days(double hours) {
        hrs_30days.setText(hours + " Hours");
    }

    /* sets value of hours practiced last 365 days */
    public void setHours365Days(double hours) {
        hrs_365days.setText(hours + " Hours");
    }
}
