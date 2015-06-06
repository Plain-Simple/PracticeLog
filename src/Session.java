import java.util.Calendar;

/* This class holds all the information about a single practice session:
Activity, Minutes Practiced, and Date of session */
public class Session {
    private String activity = "";
    private int minutes = 0;
    private Calendar date = Calendar.getInstance();
    /* constructs Session using data from log, which is in format "[activity],[minutes],[date]"
       where date is stored in milliseconds */
    public Session(String log) throws Exception {
        activity = log.substring(0, log.indexOf(","));
        minutes = Integer.parseInt(log.substring(log.indexOf(","), log.lastIndexOf(","))); // indexOf(",") + 1?
        date.setTimeInMillis(Long.parseLong(log.substring(log.lastIndexOf(","))));
    }
    public void setActivity(String activity_name) { activity = activity_name; }
    public void setMinutes(int min) { minutes = min; }
    public void setDate(Calendar date) { this.date = date; }
    public String getActivity() { return activity; }
    public int getMinutes() { return minutes; }
    public Calendar getDate() { return date; }
    /* returns a String representation of session, for logging purposes */
    @Override public String toString() {
        return activity + "," + minutes + "," + date.getTimeInMillis();
    }
}
