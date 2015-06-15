package plainsimple;

import java.util.Calendar;

/* A Session holds all the information about a single practice session:
 * Activity, Time Practiced (milliseconds), and Date of Session (milliseconds)
 * A Session is constructed using a String taken from the program's datafile
 * This String holds all necessary information */
public class Session {

    private String activity;
    private long time_practiced;
    private long date;

    /* default constructor */
    public Session() {
        this("", 0, 0);
    }

    /* constructs Session using given parameters */
    public Session(String activity, long time_practiced, long date) {
        this.activity = activity;
        this.time_practiced = time_practiced;
        this.date = date;
    }

    /* constructs Session using data from log, which is in format "[activity],[time_practiced],[date]" */
    public Session(String log) throws Exception {
        String data[] = log.split(","); // does comma need to be escaped?
        activity = data[0];
        time_practiced = Integer.parseInt(data[1]);
        date = Long.parseLong(data[2]);
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity_name) {
        activity = activity_name;
    }

    public long getTimePracticed() {
        return time_practiced;
    }

    public void setTimePracticed(long time) {
        time_practiced = time;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setDate(Calendar date) {
        this.date = date.getTimeInMillis();
    }

    /* returns a String representation of session, for logging purposes */
    @Override public String toString() {
        return activity + "," + time_practiced + "," + date;
    }
}
