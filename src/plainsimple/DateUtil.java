package plainsimple;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/* Helper functions for handling LocalDates */
public class DateUtil {

    /* Date pattern used for conversion. Can be changed */
    private static final String DATE_PATTERN = "MM/dd/yyyy";

    /* The date formatter */
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    /* Returns the date as a formatted String in pattern DATE_PATTERN
     * @param date the date to be formatted */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        String formatted = DATE_FORMATTER.format(date);

        /* Remove annoying leading zeros in day and month fields */
        if(date.getMonthValue() < 10)
            formatted = formatted.substring(1);
        if(date.getDayOfMonth() < 10)
            formatted = formatted.substring(0, formatted.indexOf('/') + 1) +
                    formatted.substring(formatted.indexOf('/') + 2);
        return formatted;
    }

    /* Converts String into LocalDate using DATE_PATTERN
     * @param dateString the date as String
     * @return parsed LocalDate or null if String couldn't be converted
     */
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /* Checks to see if dateString can be converted
     * @param dateString String to be converted
     * @return true if the String is a valid date */
    public static boolean validDate(String dateString) {
        return DateUtil.parse(dateString) != null;
    }
}
