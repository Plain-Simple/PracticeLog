package plainsimple.util;

import javafx.collections.ObservableList;
import plainsimple.Goal;
import plainsimple.Session;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

/* Helper functions for handling LocalDates */
public class GoalUtil {

    /* Calculates and returns the number of days remaining in goal's time limit
     * 0  - Time Limit ends today
     * -1 - Time Limit has passed
     * -2 - No time Limit specified */
    public static int getDaysRemaining(Goal goal) {
        if(goal.specifiesTimeLimit()) {
            if(goal.getEndDate().isAfter(LocalDate.now())) {
                return -1;
            } else if (goal.getEndDate().isEqual(LocalDate.now())) {
                return 0;
            } else {
                return Period.between(LocalDate.now(), goal.getEndDate()).getDays();
            }
        } else
            return -2;
    }

    /* Given an ObservableList of Sessions, returns an ObservableList of
     * Sessions that happened in the specified Goal's timelimit */
    public ObservableList<Session> getRelevantSessions(ObservableList<Session> session_data, Goal goal) {
        /* Remove Sessions that happened before/after time limit (if one is specified) */
        if (goal.specifiesTimeLimit()) {
            for (int i = 0; i < session_data.size(); i++) {
                if (session_data.get(i).getDate().isBefore(goal.getStartDate())
                    || session_data.get(i).getDate().isAfter(goal.getEndDate()))
                session_data.remove(i);
            }
        }

        /* Remove Sessions of another activity (if one is specified) */
        if (goal.specifiesActivity()) { /* a specific activity was specified */
            for (int i = 0; i < session_data.size(); i++) {
                if (!session_data.get(i).getActivity().equals(goal.getActivity()))
                    session_data.remove(i);
            }
        }

        return session_data;
    }
}
