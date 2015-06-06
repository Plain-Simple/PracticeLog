import java.util.ArrayList;
import java.util.Calendar;

/* A Goal has a fair amount of variability
 * The user can set the goal to be a number of times to
 * practice or an amount of goal_time to practice
 * In addition the user can set a one-goal_time goal_time limit
 * or a recurring goal_time limit (e.g., 2hrs/week)
 * Goals are constructed using a String taken from the
 * program's datafile, which holds all necessary information */
public class Goal {
    private String activity = "";
    private int goal_sessions = 0;
    private long goal_time = 0;
    private Calendar start_time = Calendar.getInstance();
    private long time_limit = 0; /* timelimit stored in milliseconds */
    private boolean recurring = false;
    /* initializes Goal using stored data */
    public Goal(String constructor) {
        /* parse constructor into individual values */
        String[] data = constructor.split(","); // comma escaped?
        activity = data[0];
        goal_sessions = Integer.parseInt(data[1]);
        goal_time = Long.parseLong(data[2]);
        start_time.setTimeInMillis(Long.parseLong(data[3]));
        time_limit = Long.parseLong(data[4]);
        recurring = Boolean.parseBoolean(data[5]);
    }
    /* returns a String representation of Goal, for data handling */
    @Override public String toString() {
        return activity + "," + goal_sessions + "," + goal_time + "," + start_time.getTimeInMillis() +
                "," + time_limit + "," + recurring;
    }
    /* accesses datahandler and returns an ArrayList of Sessions
    that are relevant towards Goal progress */
    public ArrayList<Session> getRelevantSessions(String file_name) {
        DataHandler data_handler = new DataHandler(file_name);
        ArrayList<Session> data = new ArrayList<Session>();
        if (data_handler.isValid()) { // todo: what if it isn't?
            data = data_handler.getSessions();
             /* we need to remove any Sessions that aren't relevant to the goal */
            if (time_limit != 0) { /* a goal_time limit was specified */
                long end_time = start_time.getTimeInMillis() + time_limit;
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getDate().before(start_time)
                            || data.get(i).getDate().after(end_time))
                        data.remove(i); /* remove goal_sessions that happened before/after goal_time limit */
                }
            }
            if (!activity.equals("")) { /* a specific activity was specified */
                for (int i = 0; i < data.size(); i++) {
                    if (!data.get(i).getActivity().equals(activity))
                        data.remove(i); /* remove goal_sessions that have a different activity */
                }
            }
        }
        return data;
    }
    /* returns an int[] where first element is goal_time progress in
     * milliseconds and second is percent progress
     * file_name is the name of the local file where all data is
     * stored */
     public int[] getProgress(String file_name) {
         int[] progress = new int[] {0, 0};
         ArrayList<Session> data = getRelevantSessions(file_name);
         /* now calculate relevant progress */
         if(goal_time != 0) { /* goal time specified */
             long time_spent = 0;
             for (int i = 0; i < data.size(); i++)
                 time_spent += data.get(i).getTime();
            /* calculated as total time spent during relevant sessions divided by target time */
             progress[0] = (int) (time_spent / goal_time);
         } else if(goal_sessions != 0) { /* goal sessions specified */
             /* calculated as number of relevant sessions recorded divided
             by target sessions  */
             progress[1] = data.size() / goal_sessions;
         }
         return progress;
     }
}
