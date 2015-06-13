package plainsimple;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

/* This class handles reading and writing data to the program's data file
 * A DataHandler is initialized with the name of the local file that stores data
 * It is STRONGLY recommended that the "isValid" method be used to make sure
 * specified file exists and is accessible before any other methods are used */
public class DataHandler {

    private String file_name = "";

    /* initializes a new DataHandler with name of file to read/write */
    public DataHandler(String file_name) {
        this.file_name = file_name;
    }

    /* returns all text in file as an ArrayList of lines */
    public ArrayList<String> getData() {
        ArrayList<String> file_text = new ArrayList<String>();
        try {
            FileReader file = new FileReader(file_name);
            BufferedReader read_objects = new BufferedReader(file);
            String line = "";
            while ((line = read_objects.readLine()) != null) {
                file_text.add(line);
            }
            return file_text;
        } catch (IOException e) {
            return file_text;
        }
    }
    /* writes data to file as an ArrayList of lines*/
    public boolean writeData(ArrayList<String> data) {
        try {
            FileWriter file = new FileWriter(file_name);
            BufferedWriter writer = new BufferedWriter(file);
            for (int i = 0; i < data.size(); i++) {
                writer.write(data.get(i));
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    /* returns whether the file associated with this handler exists and
    is readable */
    public boolean isValid() {
        try {
            FileReader file = new FileReader(file_name);
            BufferedReader read_objects = new BufferedReader(file);
            return true; /* file read successfully */
        } catch (IOException e) {
            return false;
        }
    }

    /* creates a new file with default data */
    public void initialize() throws IOException {
        FileWriter file = new FileWriter(file_name);
        BufferedWriter writer = new BufferedWriter(file);
        writer.write("Activities:");
        writer.newLine();
        writer.write("Goals:");
        writer.newLine();
        writer.write("Log:");
        writer.newLine();
        writer.close();
    }
    /* reads log and returns ArrayList of saved Sessions, in order of newest to oldest */
    public ArrayList<Session> getSessions() {
        ArrayList<Session> stored_sessions = new ArrayList<Session>();
        ArrayList<String> data = getData();
        for (int i = data.indexOf("Log:") + 1; i < data.size(); i++) {
            if (data.get(i).equals("Log:")) { /* start of logs */
                try {
                    stored_sessions.add(new Session(data.get(i)));
                } catch (Exception e) {} /* just ignore corrupt data */
            }
        }
        for (int i = 1; i < stored_sessions.size(); i++) {
            int counter = 1; /* number of elements to look ahead from current position */
            boolean keep_going = false; /* whether to keep looking ahead */
            Session this_session = stored_sessions.get(i); /* current element in loop, which is being evaluated */
            do {
                if (this_session.getDate() > stored_sessions.get(i - counter).getDate()) { /* more recent */
                    keep_going = true;
                    /* move other session back one */
                    stored_sessions.set(i - counter + 1, stored_sessions.get(i - counter));
                    /* replace its old position with this_session */
                    stored_sessions.set(i - counter, this_session);
                    counter++;
                }
            } while (keep_going && counter <= i);
        }
        return stored_sessions;
    }
    /* returns ArrayList of all Sessions that happened between specified times */
    public ArrayList<Session> getSessionsWithinDates(long date_1, long date_2) {
        ArrayList<Session> stored_sessions = getSessions(); // be careful of arrayindexoutofbounds when arraylist gets smaller
        for(int i = 0; i < stored_sessions.size(); i++) {// todo: this can be made more efficient by using the fact sessions are already sorted by date
            if(stored_sessions.get(i).getDate() < date_1 || // todo: iterator instead?
                    stored_sessions.get(i).getDate() > date_2)
                stored_sessions.remove(i);
        }
        return stored_sessions;
    }
    /* reads datafile and returns ArrayList of Goals */
    public ArrayList<Goal> getGoals() {
        ArrayList<Goal> stored_goals = new ArrayList<Goal>();
        ArrayList<String> data = getData();
        for(int i = data.indexOf("Goals:") + 1; !data.get(i).equals("Log:") ; i++) {
            try {
                stored_goals.add(new Goal(data.get(i)));
            } catch(Exception e) {} /* ignore corrupted data */
        }
        return stored_goals;
    }
    /* reads datafile and returns ArrayList of names of activities */
    public ArrayList<String> getActivities() {
        ArrayList<String> stored_activities = new ArrayList<String>();
        ArrayList<String> data = getData();
        for (int i = data.indexOf("Activities:"); !data.get(i).equals("Goals:"); i++) {
            stored_activities.add(data.get(i));
        }
        return stored_activities;
    }
    /* reads log and returns ArrayList of the num_sessions most recent sessions */
    public ArrayList<Session> getMostRecentSessions(int num_sessions) {
        /* get stored sessions from log, which are stored from newest to oldest */
        ArrayList<Session> stored_sessions = getSessions();
        ArrayList<Session> recent_sessions = new ArrayList<Session>();
        for (int i = 0; i < num_sessions; i++)
            recent_sessions.add(stored_sessions.get(i));
        return recent_sessions;
    }
    /* reads datafile and returns ArrayList of goals that are currently relevant
     * THIS DOES NOT FILTER OUT GOALS THAT HAVE BEEN COMPLETED */
    public ArrayList<Goal> getCurrentGoals() {
        ArrayList<Goal> stored_goals = getGoals();
        /* get current time in milliseconds */
        long current_time = Calendar.getInstance().get(Calendar.MILLISECOND);
        for(int i = 0; i < stored_goals.size(); i++) {
            /* remove any goals that aren't relevant at current System time */
            if(!stored_goals.get(i).isRelevant(current_time))
                stored_goals.remove(i);
        }
        return stored_goals;
    }
    /* adds session to log, by default as first session after "Logs:" */
    public boolean addSession(Session add) {
        ArrayList<String> data = getData();
        data.add(data.indexOf("Logs:") + 1, add.toString());
        return writeData(data);
    }
    /* adds activity to datafile under "Activities:" header */
    public boolean addActivity(String activity_name) {
        ArrayList<String> data = getData();
        data.add(data.indexOf("Activities") + 1, activity_name);
        return writeData(data);
    }
    /* removes activity from datafile */
    public boolean removeActivity(String activity_name) {
        ArrayList<String> data = getData();
        for(int i = data.indexOf("Activities:"); !data.get(i).equals("Goals:"); i++) {
            if(data.get(i).equals(activity_name))
                data.remove(i);
        }
        return writeData(data);
    }
    /* adds goal to datafile under "Goals:" header */
    public boolean addGoal(Goal add) {
        ArrayList<String> data = getData();
        data.add(data.indexOf("Goals:") + 1, add.toString());
        return writeData(data);
    }
    /* removes goal from datafile */
    public boolean removeGoal(Goal remove) {
        ArrayList<String> data = getData();
        for(int i = data.indexOf("Goals:"); !data.get(i).equals("Log:"); i++) {
            if(data.get(i).equals(remove.toString()))
                data.remove(i);
        }
        return writeData(data);
    }
}
