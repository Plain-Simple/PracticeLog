package plainsimple;

import java.io.IOException;
import java.time.LocalTime;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import plainsimple.view.LogTimeDialogController;
import plainsimple.view.LogViewController;
import plainsimple.view.MainScreenController;
import plainsimple.view.StartPracticingDialogController;

/* This class starts the JavaFX Application using MainScreen.fxml
   as the root stage */
public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /* Sessions stored as an observable list */
    private ObservableList<Session> sessionData =
            FXCollections.observableArrayList();

    /* Constructor */
    public MainApp() {
        // todo: read datafile and add to sessionData
    }

    /* Returns sessionData */
    public ObservableList<Session> getSessionData() { return sessionData; }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("PracticeLog");

        initRootLayout();

        showMainScreen();
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
     * time themselves with a countdown/countup clock // todo: figure out logic
     *
     * @param session the Session object to be created
     * @return true if the user clicked OK, false otherwise.
     */
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
            controller.setSession(new Session());
            controller.setStopWatch(new StopWatch(controller, LocalTime.of(0, 0, 0), 1));

            /* Show the dialog and wait until the user closes it */
            dialogStage.showAndWait();

            dialogStage.close(); // todo: how to stop process?

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
