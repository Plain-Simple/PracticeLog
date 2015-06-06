import java.util.Calendar;

/* A Session holds all the information about a single practice session:
 * Activity, Minutes Practiced, and Date of session, which is stored in
 * milliseconds
 * A Session is constructed using a String taken from the program's datafile
 * This String holds all necessary information */
public class Session {
    private String activity = "";
    private long time = 0;
    private Calendar date = Calendar.getInstance();
    /* constructs Session using data from log, which is in format "[activity],[time],[date]" */
    public Session(String log) throws Exception {
        String data[] = log.split(","); // does comma need to be escaped?
        activity = data[0];
        time = Integer.parseInt(data[1]);
        date.setTimeInMillis(Long.parseLong(data[2]));
    }
    public void setActivity(String activity_name) { activity = activity_name; }
    public void setTime(int min) { time = min; }
    public void setDate(Calendar date) { this.date = date; }
    public String getActivity() { return activity; }
    public long getTime() { return time; }
    public Calendar getDate() { return date; }
    /* returns a String representation of session, for logging purposes */
    @Override public String toString() {
        return activity + "," + time + "," + date.getTimeInMillis();
    }
}
