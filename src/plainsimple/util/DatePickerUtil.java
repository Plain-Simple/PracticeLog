package plainsimple.util;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.time.LocalDate;

/* Helper functions for handling DatePickers */
public class DatePickerUtil {

    /* Returns a Callback with all DateCells before specified LocalDate disabled
     * This is useful for setting the DayCellFactory of a DatePicker
     * @param when the LocalDate before which all DateCells should be disabled
     * @return */
    public static Callback<DatePicker, DateCell> getDayCellFactoryBefore(LocalDate when) {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isBefore(when)) {
                            setDisable(true);
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }

    /* Returns a Callback with all DateCells after specified LocalDate disabled
     * This is useful for setting the DayCellFactory of a DatePicker
     * @param when the LocalDate after which all DateCells should be disabled
     * @return */
    public static Callback<DatePicker, DateCell> getDayCellFactoryAfter(LocalDate when) {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isAfter(when)) {
                            setDisable(true);
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }
}
