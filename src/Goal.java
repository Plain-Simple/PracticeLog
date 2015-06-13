import java.util.ArrayList;

/* A Goal has a fair amount of variability
 * The user can set the goal to be a number of times to
 * practice or an amount of goal_time to practice
 * In addition the user can set a one-goal_time goal_time limit
 * or a recurring goal_time limit (e.g., 2hrs/week)
 * Goals are constructed using a String taken from the
 * program's datafile, which holds all necessary information */
public class Goal {
    private String file_name = ""; /* name of local file where program data can be retrieved */
    private String activity = "";
    private int goal_sessions = 0;
    private long goal_time = 0;
    private long start_time = 0;
    private long time_limit = 0; /* timelimit stored in milliseconds */
    private boolean recurring = false;
    /* initializes Goal using stored data */
    public Goal(String constructor) throws Exception {
        /* parse constructor into individual values */
        String[] data = constructor.split(","); // comma escaped?
        activity = data[0];
        goal_sessions = Integer.parseInt(data[1]);
        goal_time = Long.parseLong(data[2]);
        start_time = Long.parseLong(data[3]);
        time_limit = Long.parseLong(data[4]);
        recurring = Boolean.parseBoolean(data[5]);
    }
    /* returns whether Goal has a time limit, in which case long time_limit != 0 */
    public boolean hasTimeLimit() { return time_limit != 0; }
    /* returns whether Goal has been completed by checking if goal progress equals
     * 1, or 100 percent */
    public boolean isComplete() { return getProgress()[1] == 1; }
    /* returns whether Goal is relevant at specified time
     * if a time limit is specified it will see if System time falls within time limit
     * if no time limit is specified it is relevant */
    public boolean isRelevant(long at_time) {
        if(hasTimeLimit()) {
            return (at_time >= start_time && at_time <= start_time + time_limit);
        } else {
            return true;
        }
    }
     /* returns a String representation of Goal, for data handling */
    @Override public String toString() {
        return activity + "," + goal_sessions + "," + goal_time + "," + start_time +
                "," + time_limit + "," + recurring;
    }
    /* accesses datahandler and returns an ArrayList of Sessions
    that are relevant towards Goal progress */
    public ArrayList<Session> getRelevantSessions() {
        DataHandler data_handler = new DataHandler(file_name);
        ArrayList<Session> data = new ArrayList<Session>();
        if (data_handler.isValid()) { // todo: what if it isn't?
            data = data_handler.getSessions();
             /* we need to remove any sessions that aren't relevant to the goal */
            if (time_limit != 0) { /* a time limit was specified */
                long end_time = start_time + time_limit;
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getDate() < start_time
                            || data.get(i).getDate() > end_time)
                        data.remove(i); /* remove sessions that happened before/after goal_time limit */
                }
            }
            if (!activity.equals("")) { /* a specific activity was specified */
                for (int i = 0; i < data.size(); i++) {
                    if (!data.get(i).getActivity().equals(activity))
                        data.remove(i); /* remove sessions that have a different activity */
                }
            }
        }
        return data;
    }
    /* returns a long[] where first element is "raw" progress (i.e. milliseconds
     * practiced or sessions practiced) and second is percent progress */
     public long[] getProgress() {
         long[] progress = new long[] {0, 0};
         ArrayList<Session> data = getRelevantSessions();
         /* now calculate relevant progress */
         if(goal_time != 0) { /* goal time specified */
             long time_spent = 0;
             for (int i = 0; i < data.size(); i++)
                 time_spent += data.get(i).getTimePracticed();
             /* total time spent, in milliseconds */
             progress[0] = time_spent;
            /* calculated as total time spent during relevant sessions divided by target time */
             progress[1] = time_spent / goal_time;
         } else if(goal_sessions != 0) { /* goal sessions specified */
             /* total practice sessions */
             progress[0] = data.size();
             /* calculated as number of relevant sessions recorded divided
             by target sessions  */
             progress[1] = data.size() / goal_sessions;
         }
         return progress;
     }
}
