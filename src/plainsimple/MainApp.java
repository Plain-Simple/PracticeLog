package plainsimple;

import java.io.IOException;
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
import plainsimple.view.MainScreenController;

/* This class starts the JavaFX Application using MainScreen.fxml
   as the root stage */
public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /* Sessions stored as an observable list */
    private ObservableList<Session> sessionData =
            FXCollections.observableArrayList();

    /* constructor */
    public MainApp() {
        // todo: read datafile and add to sessionData
    }

    /* returns sessionData */
    public ObservableList<Session> getSessionData() {
        return sessionData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("PracticeLog");

        initRootLayout();

        showMainScreen();
    }

    /* initializes root layout */
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

    /* shows mainscreen inside root layout */
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

    /* returns main stage */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /* pops up the "Log a Time" dialog */
    public boolean showLogTimeDialog() {
        try {
            /* load the fxml file and create a new stage for the popup dialog */
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LogTimeDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            /* create the dialog stage */
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Submit an Entry");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            LogTimeDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* adds Session to sessionData, sorts sessionData, and recalculates stuff */
    public void addSession(Session add) {
        sessionData.add(add);
        sessionData = SessionUtil.sort(sessionData);
        // todo: add recalculation and access MainScreen somehow
    }

    public static void main(String[] args) {
        launch(args);
    }
}
