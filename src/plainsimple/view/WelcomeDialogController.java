package plainsimple.view;

import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import plainsimple.MainApp;

import java.io.File;

/* Controller class for the welcome dialog, which is shown the first time
 * the program runs. It gives a brief description of the program and prompts
 * the user to select a directory in which to save program data */
public class WelcomeDialogController extends Stage {

    private Stage dialogStage;
    private File saveDirectory;

    /* Sets dialog stage
     * @param dialogStage */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /* Returns directory user has chosen (null if not chosen) */
    public File getSaveDirectory() { return saveDirectory; }

    /* Handles the user pressing the "Choose Directory" Button, which
     * opens a directory chooser to the user's home directory */
    @FXML private void handleChooseDirectory() {
        saveDirectory = showDirectoryChooser("Choose Save Location",
                new File(System.getProperty("user.home")));

        /* Valid file chosen - Close chooser */
        if(saveDirectory != null && saveDirectory.exists()) {
            dialogStage.close();
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
        return(chooser.showDialog(dialogStage));
    }
}
