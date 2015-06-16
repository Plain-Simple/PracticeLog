package plainsimple;

import javafx.collections.ObservableList;

/* Helper functions for handling Sessions */
public class SessionUtil {

    /* sorts data by date, from newest to oldest */
    public static ObservableList<Session> sort(ObservableList<Session> data) {
        for (int i = 1; i < data.size(); i++) {
            int counter = 1; /* number of elements to look ahead from current position */
            boolean keep_going = false; /* whether to keep looking ahead */
            Session this_session = data.get(i); /* current element in loop, which is being evaluated */
            do {
                if (this_session.getDate().isAfter(data.get(i - counter).getDate())) { /* more recent */
                    keep_going = true;
                    /* move other session back one */
                    data.set(i - counter + 1, data.get(i - counter));
                    /* replace its old position with this_session */
                    data.set(i - counter, this_session);
                    counter++;
                }
            } while (keep_going && counter <= i);
        }
        return data;
    }
}
