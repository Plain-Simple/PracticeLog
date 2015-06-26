package plainsimple.util;

import java.time.DateTimeException;
import java.time.LocalTime;

/* Helper functions for handling LocalTimes */
public class TimeUtil {

    /* Constructs LocalTime object using "hour" and "minute" Strings
     * Returns null if a valid LocalTime cannot be constructed
     * @param hr_field
     * @param min_field
     * @return LocalTime constructed from provided fields */
    public static LocalTime stringsToTime(String hr_field, String min_field) { // todo: make sure this catches errors
        int hrs, min;
        /* Initialized = true if at least one field has an integer */
        boolean initialized = false;
        try {
            hrs = Integer.parseInt(hr_field);
            initialized = true;
        } catch(NumberFormatException e) {
            if(hr_field.isEmpty())
                hrs = 0;
            else
                return null;
        }

        try {
            min = Integer.parseInt(min_field);
            initialized = true;
        } catch(NumberFormatException e) {
            if(min_field.isEmpty())
                min = 0;
            else
                return null;
        }

        if(initialized) {
            if (min > 59) {
                hrs += min / 60;
                min = min % 60;
            }

            try {
                return LocalTime.of(hrs, min);
            } catch (DateTimeException e) {
                return null;
            }

        } else
            return null;
    }

    /* Returns true if Strings could be used to construct a LocalTime object,
     * false if they are invalid
     * @param hr_field
     * @param min_field
     * @return */
    public static boolean validTime(String hr_field, String min_field) {
        return stringsToTime(hr_field, min_field) != null;
    }
}
