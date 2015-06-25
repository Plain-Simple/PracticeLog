package plainsimple;

import sun.util.resources.cldr.lag.LocaleNames_lag;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/* Allows users to set goals
 * Goals can specify a certain activity
 * Goals can specify a number of practice Sessions
 * or an amount of time to practice
 * Goals can be set to have time limits
 * They can also be recurring */
public class Goal {

    /* Name of activity - if empty, no specific activity specified */
    private String activity;

    /* Whether an activity was specified */
    private boolean hasActivity = true;

    /* Number of sessions to practice - if zero, a time was specified */
    private int goalSessions;

    /* Amount of time to practice - if zero, a number of sessions
     * was specified */
    private LocalTime goalTime;

    /* Whether a time limit was set or not */
    private boolean timeLimit = true;

    /* Date time limit starts */
    private LocalDate startDate;

    /* Date time limit ends */
    private LocalDate endDate;

    /* Whether or not Goal is recurring, i.e. is reset once completed */
    private boolean recurring = false;

    /* Default constructor */
    public Goal() {
        activity = "";
        goalTime = LocalTime.of(0, 0);
    }

    public String getActivity() { return activity; }

    public void setActivity(String activity) { this.activity = activity;  hasActivity = true; }

    public boolean specifiesActivity() { return hasActivity; }

    public void setHasActivity(boolean hasActivity) { this.hasActivity = hasActivity; }

    public boolean specifiesTimeLimit() { return timeLimit; }

    public void setSpecifiesTimeLimit(boolean timeLimit) { this.timeLimit = timeLimit; }

    public LocalDate getStartDate() { return startDate; }

    public void setStartDate(LocalDate startDate) { this.startDate = startDate; timeLimit = true; }

    public LocalDate getEndDate() { return endDate; }

    public void setEndDate(LocalDate endDate) { this.endDate = endDate; timeLimit = true; }

    public boolean specifiesSessions() { return goalSessions != 0; }

    public int getTargetSessions() { return goalSessions; }

    public void setTargetSession(int goalSessions) { this.goalSessions = goalSessions; }

    public boolean specifiesTime() { return goalSessions == 0; }

    public LocalTime getTargetTime() { return goalTime; }

    public void setTargetTime(LocalTime goalTime) { this.goalTime = goalTime; }

    public boolean isRecurring() { return recurring; }

    public void setRecurring(boolean recurring) { this.recurring = recurring; }
}
