package plainsimple;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import plainsimple.model.GoalListWrapper;
import plainsimple.model.SessionListWrapper;
import plainsimple.view.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/* This class starts the JavaFX Application using MainScreen.fxml
   as the root stage */
public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /* Sessions stored as an observable list */
    private ObservableList<Session> sessionData =
            FXCollections.observableArrayList();

    /* Goals stored as an observable list */
    private ObservableList<Goal> goalData =
            FXCollections.observableArrayList();

    /* Constructor */
    public MainApp() {
    }

    /* Returns sessionData */
    public ObservableList<Session> getSessionData() { return sessionData; }

    /* Returns goalData */
    public ObservableList<Goal> getGoalData() { return goalData; }

    /* Starts program, setting up the root layout and displaying the main screen */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("PracticeLog");

        initRootLayout();

        showMainScreen();

        /* Access file containing persisting data using path found in Preferences */
        File file = getSessionFilePath();
        System.out.println("File location: " + file.getPath());
        if (file != null) {
            loadSessionDataFromFile(file);
            loadGoalDataFromFile(file); // todo: goalData not being saved correctly
        } else { /* File not found - Open directory chooser for user to choose where to save data */
            // todo: read datafile and add to sessionData and goalData
            // todo: pop-up asking to specify a directory
            File new_directory = showDirectoryChooser("Choose Save Location",
                    new File(System.getProperty("user.home")));
            /* Create a folder titled "PracticeLog" in chosen directory */
            new_directory = new File(new_directory.getPath() + File.separator + "PracticeLog");
            createFolder(new_directory); // todo: nullpointerexception if no file chosen

            /* Create xml datafiles for sessionData and goalData and update properties file with new paths */
            File sessionDataFile = new File(new_directory.getPath() + File.separator + "SessionData.xml");
            setSessionFilePath(sessionDataFile);
            File goalDataFile = new File(new_directory.getPath() + File.separator + "GoalData.xml");
            setGoalFilePath(goalDataFile);
        }

        sessionData.addListener(new ListChangeListener<Session>() {
            @Override
            public void onChanged(Change<? extends Session> c) {
                saveSessionDataToFile(getSessionFilePath());
            }
        });

        goalData.addListener(new ListChangeListener<Goal>() {
            @Override
            public void onChanged(Change<? extends Goal> c) {
                saveGoalDataToFile(getGoalFilePath());
            }
        });
    }

    /* Initializes root layout */
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            /* show the scene containing the root layout */
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Returns main stage */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /* Shows mainscreen inside root layout */
    public void showMainScreen() {
        try {
            /* load MainScreen.fxml */
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainScreen.fxml"));
            AnchorPane personOverview = loader.load();

            /* set MainScreen in center of root layout */
            rootLayout.setCenter(personOverview);

            /* give controller access to MainApp and stored data */
            MainScreenController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Pops up the "Log a Time" dialog to edit/create a new Session
     * If the user clicks OK, the changes are saved into the provided
     * Session object and true is returned.
     * @param person the Session object to be edited
     * @return true if the user clicked OK, false otherwise. */
    public boolean showLogTimeDialog(Session new_session) {
        try {
            /* load the fxml file and create a new stage for the popup dialog */
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LogTimeDialog.fxml"));
            AnchorPane page = loader.load();

            /* create the dialog stage */
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Submit an Entry");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            LogTimeDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSession(new_session);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* Pops up the "See Full Log" dialog to show the full practice log
     * @param data the ObservableList of Session data to be displayed
     * @return true if the dialog displayed correctly, false otherwise */
    public boolean showLogViewDialog(ObservableList<Session> data) {
        try {
            /* Load the fxml file and create a new stage for the popup dialog */
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LogView.fxml"));
            AnchorPane page = loader.load();

            /* Create the dialog Stage */
            Stage dialogStage = new Stage();
            dialogStage.setTitle("View Log");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            /* Sets controller's data */
            LogViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setData(data);

            /* Show the dialog and wait until the user closes it */
            dialogStage.showAndWait();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* Pops up the "Start Practicing!" dialog to allow the user to
     * time themselves with a countdown/countup clock
     * @param session the Session object to be created
     * @return true if the user clicked OK, false otherwise. */
    public boolean showStartPracticingDialog(Session session) {
        try {
            /* Load the fxml file and create a new stage for the popup dialog */
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/StartPracticingDialog.fxml"));
            AnchorPane page = loader.load();

            /* Create the dialog stage */
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Start Practicing!");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            /* Sets Session to be created */
            StartPracticingDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSession(session);
            StopWatch initial_stopwatch = new StopWatch(controller, LocalTime.of(0, 0, 0), 1, true);
            controller.setStopWatch(initial_stopwatch);

            /* Show the dialog and wait until the user closes it */
            dialogStage.showAndWait();

            dialogStage.close();

            /* Make sure stopwatch has been stopped */
            initial_stopwatch.stop();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* Pops up the "Set a Goal" dialog to allow the user to
     * create or edit a Goal
     * @param goal the Goal object to be created or edited
     * @return true if the user clicked OK, false otherwise. */
    public boolean showSetGoalDialog(Goal goal) {
        try {
            /* Load the fxml file and create a new stage for the popup dialog */
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SetGoalDialog.fxml"));
            AnchorPane page = loader.load();

            /* Create the dialog stage */
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Set a Goal");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            /* Sets Goal to be created/edited */
            SetGoalDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setGoal(goal);

            /* Show the dialog and wait until the user closes it */
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* Accesses Program Preferences as saved in the OS's system registry and returns
     * the path to the file where Session data is stored. If the file
     * preference can't be found, returns null.
     * @return path to the file containing persisting Session data */
    public File getSessionFilePath() {
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
    public void setSessionFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("sessionFilePath", file.getPath());
        } else {
            prefs.remove("sessionFilePath");
        }
    }

    /* Loads Session data from the specified file into sessionData. This overwrites
     * sessionData
     * @param file file where persisting Session data is kept */
    public void loadSessionDataFromFile(File file) {
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
    }

    /* Saves the current Session data to the specified file as XML
     * @param file file to save persisting Session data to */
    public void saveSessionDataToFile(File file) {
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
        } catch (Exception e) {
        }
    }

    /* Accesses Program Preferences as saved in the OS's system registry and returns
     * the path to the file where Goal data is stored. If the file
     * preference can't be found, returns null.
     * @return path to the file containing persisting Goal data */
    public File getGoalFilePath() {
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
    public void setGoalFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("goalFilePath", file.getPath());
        } else {
            prefs.remove("goalFilePath");
        }
    }

    /* Loads Goal data from the specified file into goalData. This overwrites
     * goalData
     * @param file file where persisting Session data is kept */
    public void loadGoalDataFromFile(File file) {
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
    }

    /* Saves the current Goal data to the specified file as XML
     * @param file file to save persisting Goal data to */
    public void saveGoalDataToFile(File file) {
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

    /* Opens a DirectoryChooser allowing user to choose a directory
     * @param title the title to be displayed on the chooser
     * @param defaultDirectory the directory the chooser opens to initially
     * @return the directory chosen by the user */
    private File showDirectoryChooser(String title, File defaultDirectory) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle(title);
        chooser.setInitialDirectory(defaultDirectory);
        return(chooser.showDialog(primaryStage));
    }

    /* Creates a folder with specified path
     * @param location the path of the new folder */
    public boolean createFolder(File location) {
        try{
            return location.mkdir();
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
