package plainsimple;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import plainsimple.util.DateUtil;
import plainsimple.util.LocalDateAdapter;
import plainsimple.util.LocalTimeAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.LocalTime;

/* Model class for a Session
 * Holds all the information about a single practice session:
 * Activity, Time Practiced (milliseconds), and Date of Session (milliseconds)
 * A Session is constructed using a String taken from the program's datafile
 * This String holds all necessary information */
public class Session {

    private final StringProperty activity;
    private LocalTime time_practiced;
    private LocalDate date;

    /* default constructor */
    public Session() {
        this("", LocalTime.of(0, 0), LocalDate.now());
    }

    /* constructs Session using given parameters */
    public Session(String activity, LocalTime time_practiced, LocalDate date) {
        this.activity = new SimpleStringProperty(activity);
        this.time_practiced = time_practiced;
        this.date = date;
    }

    /* constructs Session using data from log, which is in format "[activity],[time_practiced],[date]" */
    public Session(String log) throws Exception {
        String data[] = log.split(","); // does comma need to be escaped?
        this.activity = new SimpleStringProperty(data[0]);
        this.time_practiced = LocalTime.parse(data[1]);
        this.date = LocalDate.parse(data[2]);
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

    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    public LocalTime getTimePracticed() {
        return time_practiced;
    }

    public void setTimePracticed(LocalTime time_practiced) {
        this.time_practiced = time_practiced;
    }

    /* returns time_practiced as a String in custom format */
    public String timePracticedString() {
        String time = "";
        if(time_practiced.getHour() != 0)
            time += time_practiced.getHour() + "h";
        if(time_practiced.getMinute() != 0)
            time += time_practiced.getMinute() + "m";
        return time;
    }

    /* returns time_practiced as a StringProperty in custom format */
    public StringProperty timePracticedProperty() {
        String time = "";
        if(time_practiced.getHour() != 0)
            time += time_practiced.getHour() + "h";
        if(time_practiced.getMinute() != 0)
            time += time_practiced.getMinute() + "m";
        return new SimpleStringProperty(time);
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String dateString() { return DateUtil.format(date); }

    public StringProperty dateProperty() { return new SimpleStringProperty(DateUtil.format(date)); }

    /* returns a String representation of session, for logging purposes */
    @Override public String toString() {
        return activity + "," + time_practiced + "," + date;
    }
}
