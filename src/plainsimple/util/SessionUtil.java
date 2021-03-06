package plainsimple.util;

import javafx.collections.ObservableList;
import plainsimple.Session;

import java.time.LocalDate;

/* Helper functions for handling Sessions */
public class SessionUtil {

    /* Sorts data by date, from newest to oldest */
    public static ObservableList<Session> sort(ObservableList<Session> data) {
        //return data.sort(); // todo: testing
        int calculations = 0;
        int counter;
        boolean keep_going;
        Session this_session;
        for (int i = 1; i < data.size(); i++) {
            counter = 1; /* number of elements to look ahead from current position */
            this_session = data.get(i); /* current element in loop, which is being evaluated */
            do {
                if (this_session.getDate().isAfter(data.get(i - counter).getDate())) { /* more recent */
                    keep_going = true; /* keep looking ahead */
                    /* move other session back one */
                    data.set(i - counter + 1, data.get(i - counter));
                    /* replace its old position with this_session */
                    data.set(i - counter, this_session);
                    counter++;
                } else
                    keep_going = false;
            } while (keep_going && counter <= i);
        }
        return data;
    }

    /* Returns ObservableList of all Sessions that happened between specified times */
    public static ObservableList<Session> getSessionsWithinDates(ObservableList<Session> data, LocalDate date_1, LocalDate date_2) {
        boolean equal = false; /* date_1 == date_2 */
        if(date_1.isEqual(date_2)) {
            equal = true;
        } else {
            /* make sure date_1 is earlier than date_2 */
            if (date_1.isAfter(date_2)) {
                LocalDate swap = date_2;
                date_2 = date_1;
                date_1 = swap;
            }
        }
        for(int i = 0; i < data.size(); i++) {// todo: this can be made more efficient by using the fact sessions are already sorted by date
            if(equal) { // todo: iterator instead?
                /* if date_1 == data_2 then this just returns sessions with date equal to date_1 */
                if(!data.get(i).getDate().isEqual(date_1))
                    data.remove(i);
            } else {
                if (data.get(i).getDate().isBefore(date_1) ||
                        data.get(i).getDate().isAfter(date_2))
                    data.remove(i);
            }
        }
        return data;
    }

    /* Returns ObservableList of Sessions that happened in the last [days] days */
    public static ObservableList<Session> getRecentSessions(ObservableList<Session> data, int days) {
        /* calculate "cutoff" date
         * for this to work we need to take today's date minus days and add one
         * that way we can use the isBefore() method */
        LocalDate cut_off = LocalDate.now().minusDays(days - 1);
        for(int i = 0; i < data.size(); i++) {
            if(data.get(i).getDate().isBefore(cut_off))
                data.remove(i);
        }
        return data;
    }

    /* Returns total hours of practice time of all sessions in ObservableList */
    public static float getTotalHours(ObservableList<Session> data) {
        float total = 0.0f;
        for(int i = 0; i < data.size(); i++) {
            total += data.get(i).getTimePracticed().getHour();
            total += (float) data.get(i).getTimePracticed().getMinute() / 60;
        }
        return total;
    }

    /* Returns date of most recent Session in ObservableList */
    public static LocalDate getNewestDate(ObservableList<Session> data) {
        LocalDate most_recent = data.get(0).getDate();

        for(int i = 0; i < data.size(); i++) {
            /* Speed up - can't get more recent than today */
            if(data.get(i).getDate().isEqual(LocalDate.now()))
                return data.get(i).getDate();
            if(data.get(i).getDate().isAfter(most_recent))
                most_recent = data.get(i).getDate();
        }
        return most_recent;
    }

    /* Returns date of oldest Session in ObservableList */
    public static LocalDate getOldestDate(ObservableList<Session> data) {
        LocalDate oldest = data.get(0).getDate();

        for(int i = 0; i < data.size(); i++) {
            if(data.get(i).getDate().isBefore(oldest))
                oldest = data.get(i).getDate();
        }
        return oldest;
    }
}
