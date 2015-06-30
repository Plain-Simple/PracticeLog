package plainsimple.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import plainsimple.Goal;
import plainsimple.MainApp;
import plainsimple.Session;
import plainsimple.model.GoalListWrapper;
import plainsimple.model.SessionListWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.prefs.Preferences;

/* Methods for accessing program preferences and getting persistent XML data */
public class DataHandler {
    
    /* Accesses Program Preferences as saved in the OS's system registry and returns
     * the path to the file where Session data is stored. If the file
     * preference can't be found, returns null.
     * @return path to the file containing persisting Session data */
    public static File getSessionFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        /* Retrieve filePath from "filePath" field in Preferences file */
        String filePath = prefs.get("sessionFilePath", null);

        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /* Accesses Program Preferences as saved in the OS's system and sets the path
     * to the file where Session data is stored. The path is stored as persisting
     * data in the OS specific registry.
     * @param file file where Session data is stored, or null, to remove the path */
    public static void setSessionFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("sessionFilePath", file.getPath());
        } else {
            prefs.remove("sessionFilePath");
        }
    }

    /* Loads Session data from the specified file into an ObservableList.
     * // todo: If the file does not exist a new file will be created.
     * @param file XML file where persisting Session data is kept
     * @return an ObservableList of Session objects retrieved from the XML file */
    public static ObservableList<Session> loadSessionDataFromFile(File file) {
        ObservableList<Session> sessionData = FXCollections.observableArrayList();;
        try {
            JAXBContext context = JAXBContext
                    .newInstance(SessionListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            /* Read XML from the file and unmarshal the data */
            SessionListWrapper wrapper = (SessionListWrapper) um.unmarshal(file);

            sessionData.clear();
            sessionData.addAll(wrapper.getSessions());

            /* Save the file path to the registry */
            setSessionFilePath(file);

        } catch (Exception e) {
        }
        return sessionData;
    }

    /* Saves the current Session data to the specified file as XML
     * @param file XML file to save persisting Session data to */
    public static boolean saveSessionDataToFile(File file, ObservableList<Session> sessionData) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(SessionListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            /* Wrap Session data from sessionData */
            SessionListWrapper wrapper = new SessionListWrapper();
            wrapper.setSessions(sessionData);

            /* Marshal and save XML to the file */
            m.marshal(wrapper, file);

            /* Save the file path to the registry */
            setSessionFilePath(file);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /* Accesses Program Preferences as saved in the OS's system registry and returns
     * the path to the file where Goal data is stored. If the file
     * preference can't be found, returns null.
     * @return path to the file containing persisting Goal data */
    public static File getGoalFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        /* Retrieve filePath from "filePath" field in Preferences file */
        String filePath = prefs.get("goalFilePath", null);

        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /* Accesses Program Preferences as saved in the OS's system and sets the path
     * to the file where Goal data is stored. The path is stored as persisting
     * data in the OS specific registry.
     * @param file file where Goal data is stored, or null, to remove the path */
    public static void setGoalFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("goalFilePath", file.getPath());
        } else {
            prefs.remove("goalFilePath");
        }
    }

    /* Loads Goal data from the specified file into an ObservableList.
     * @param file XML file where persisting Session data is kept
     * @return an ObservableList of Goal objects retrieved from the XML file */
    public static ObservableList<Goal> loadGoalDataFromFile(File file) {
        ObservableList<Goal> goalData = FXCollections.observableArrayList();
        try {
            JAXBContext context = JAXBContext
                    .newInstance(SessionListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            /* Read XML from the file and unmarshal the data */
            GoalListWrapper wrapper = (GoalListWrapper) um.unmarshal(file);

            goalData.clear();
            goalData.addAll(wrapper.getGoals());

            /* Save the file path to the registry */
            setGoalFilePath(file);

        } catch (Exception e) {
        }
        return goalData;
    }

    /* Saves the current Goal data to the specified file as XML
     * @param file XML file to save persisting Goal data to */
    public static void saveGoalDataToFile(File file, ObservableList<Goal> goalData) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(SessionListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            /* Wrap Goal data from sessionData */
            GoalListWrapper wrapper = new GoalListWrapper();
            wrapper.setGoals(goalData);

            /* Marshal and save XML to the file */
            m.marshal(wrapper, file);

            /* Save the file path to the registry */
            setGoalFilePath(file);
        } catch (Exception e) {
        }
    }

}
