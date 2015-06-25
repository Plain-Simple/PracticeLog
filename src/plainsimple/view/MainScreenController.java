package plainsimple.view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import plainsimple.Goal;
import plainsimple.MainApp;
import plainsimple.Session;
import plainsimple.util.SessionUtil;

import java.time.LocalDate;
import java.time.Period;

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
        Session temp_session = new Session();
        boolean okClicked = mainApp.showStartPracticingDialog(temp_session);
        if (okClicked) {
            /* add Session to sessionData */
            mainApp.getSessionData().add(temp_session);

            /* sort sessionData */
            SessionUtil.sort(mainApp.getSessionData());

            /* recalculate and redisplay recent analytics */
            updateRecentStats(temp_session);
        }
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

                /* recalculate and redisplay recent analytics */
                updateRecentStats(temp_session);
            }
    }

    /* updates values for time practiced recently */
    private void updateRecentStats(Session added_session) {
        /* calculate number of days between newest-added session and today */
        Period p = Period.between(added_session.getDate(), LocalDate.now());
        int days_since = p.getDays();
        /* see what needs to be updated */
        if(days_since == 0) {  //todo: this needs to be checked. A little confusing
            setHoursToday(SessionUtil.getTotalHours(SessionUtil.getRecentSessions(mainApp.getSessionData(), 1)));
        }
        if(days_since < 7) {
            setHours7Days(SessionUtil.getTotalHours(SessionUtil.getRecentSessions(mainApp.getSessionData(), 7)));
        }
        if(days_since < 30) {
            setHours30Days(SessionUtil.getTotalHours(SessionUtil.getRecentSessions(mainApp.getSessionData(), 30)));
        }
        if(days_since < 365) {
            setHours365Days(SessionUtil.getTotalHours(SessionUtil.getRecentSessions(mainApp.getSessionData(), 365)));
        }
    }

    /* handles user pressing the "Set a Goal" button */
    @FXML private void handleSetGoal() {
        Goal temp_goal = new Goal();
        boolean okClicked = mainApp.showSetGoalDialog(temp_goal);
        if (okClicked) {
            mainApp.getGoalData().add(temp_goal);
        }
    }

    /* handles user pressing the "See Full Log" button */
    @FXML private void handleShowLog() {
        boolean okClicked = mainApp.showLogViewDialog(mainApp.getSessionData());
        if (false) {
            System.out.println("Error occurred");
        }
    }

    /* handles user pressing the "More Detailed Analytics" button */
    @FXML private void handleShowAnalytics() {

    }

    /* sets value of hours practiced today */
    public void setHoursToday(float hours) {
        hrs_today.setText(String.format("%.1f", hours) + " Hours");
    }

    /* sets value of hours practiced last 7 days */
    public void setHours7Days(float hours) {
        hrs_7days.setText(String.format("%.1f", hours) + " Hours");
    }

    /* sets value of hours practiced last 30 days */
    public void setHours30Days(float hours) {
        hrs_30days.setText(String.format("%.1f", hours) + " Hours");
    }

    /* sets value of hours practiced last 365 days */
    public void setHours365Days(float hours) {
        hrs_365days.setText(String.format("%.1f", hours) + " Hours");
    }
}
