package plainsimple;

import javafx.scene.control.TextField;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

/* A class for managing a countup/countdown clock */
public class StopWatch {
    /* The "current time" of the clock, which is displayed */
    private LocalTime currentTime;

    /* Increment, in milleseconds, to count. Should be a multiple of 1000 */
    private long increment;

    /* Increment, in seconds, to count. Used for calculation of currentTime */
    private long secondsIncrement;

    /* Number of increments that have passed since clock started */
    private int incrementsElapsed;

    /* True if clock is a countup stopwatch; false if countdown timer */
    private boolean countUp;

    /* Javafx Textfields displaying currentTime */
    private TextField hourField;
    private TextField minuteField;
    private TextField secondField;

    private Timer timer;
    private TimerTask task;

    /* Default constructor
     * @param start_time the time clock is set at initially
     * @param count_up whether the clock counts up (true) or down (false)
     * @param increment the length, in milliseconds, of each measured interval */
    public StopWatch(LocalTime start_time, boolean count_up, long increment) {
        currentTime = start_time;
        countUp = count_up;
        this.increment = increment;
        secondsIncrement = increment / 1000;

        /* Initialize timer and task */
        timer = new Timer();
        task = new TimerTask() {
            @Override public void run() { updateClock(); }
        };
    }

    /* Starts the clock */
    public void start() {
        timer.schedule(task, 0, increment);
    }

    /* Stops the clock, cancels and purges timer */
    public void stop() {
        timer.cancel();
        timer.purge();
    }

    /* Updates currentTime and incrementsElapsed
     * Sets TextFields */
    public void updateClock() {
        incrementsElapsed++;
        if(countUp)
            currentTime.plusSeconds(secondsIncrement);
        else
            currentTime.minusSeconds(secondsIncrement);
    }
}
