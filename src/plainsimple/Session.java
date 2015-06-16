package plainsimple;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Calendar;

/* Model class for a Session
 * Holds all the information about a single practice session:
 * Activity, Time Practiced (milliseconds), and Date of Session (milliseconds)
 * A Session is constructed using a String taken from the program's datafile
 * This String holds all necessary information */
public class Session {

    private final StringProperty activity;
    private final ObjectProperty<Long> time_practiced;
    private final ObjectProperty<Long> date;

    /* default constructor */
    public Session() {
        this("", 0, 0);
    }

    /* constructs Session using given parameters */
    public Session(String activity, long time_practiced, long date) {
        this.activity = new SimpleStringProperty(activity);
        this.time_practiced = new SimpleObjectProperty<Long>(time_practiced);
        this.date = new SimpleObjectProperty<Long>(date);
    }

    /* constructs Session using data from log, which is in format "[activity],[time_practiced],[date]" */
    public Session(String log) throws Exception {
        String data[] = log.split(","); // does comma need to be escaped?
        this.activity = new SimpleStringProperty(data[0]);
        this.time_practiced = new SimpleObjectProperty<Long>(Long.parseLong(data[1]));
        this.date = new SimpleObjectProperty<Long>(Long.parseLong(data[2]));
    }

    public String getActivity() {
        return activity.get();
    }

    public void setActivity(String activity) {
        this.activity.set(activity);
    }

    public StringProperty activityProperty() {
        return activity;
    }

    public long getTimePracticed() {
        return time_practiced.get();
    }

    public void setTimePracticed(long time_practiced) {
        this.time_practiced.set(time_practiced);
    }

    public ObjectProperty<Long> timePracticedProperty() {
        return time_practiced;
    }

    public long getDate() {
        return date.get();
    }

    public void setDate(long date) {
        this.date.set(date);
    }

    public void setDate(Calendar date) {
        this.date.set(date.getTimeInMillis());
    }

    public ObjectProperty<Long> dateProperty() {
        return date;
    }

    /* returns a String representation of session, for logging purposes */
    @Override public String toString() {
        return activity + "," + time_practiced + "," + date;
    }
}
