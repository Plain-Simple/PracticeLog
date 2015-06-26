package plainsimple.util;

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
        try {
            hrs = Integer.parseInt(hr_field);
            min = Integer.parseInt(min_field);

            if(min > 59) {
                hrs += min / 60;
                min = min % 60;
            }
            return LocalTime.of(hrs, min);
        } catch(NullPointerException|NumberFormatException e) {
            return null;
        }
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
