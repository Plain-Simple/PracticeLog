package plainsimple.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/* Methods for generating constructed Alert objects */
public class AlertUtil {

    /* Returns a new Alert object of type INFORMATION with specified
     * title, header, and content text.
     * @param title the title to be displayed at the top of the alert
     * @param headerText text to be displayed in the alert's header (null for no header)
     * @param contentText text to be displayed in the alert's main body
     * @return Alert constructed using specified parameters */
    public static Alert getInfoAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert;
    }

    /* Returns a new Alert object of type ERROR with specified
     * title, header, and content text.
     * @param title the title to be displayed at the top of the alert
     * @param headerText text to be displayed in the alert's header (null for no header)
     * @param contentText text to be displayed in the alert's main body
     * @return Alert constructed using specified parameters */
    public static Alert getErrorAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert;
    }
}
