package plainsimple;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import plainsimple.view.StartPracticingDialogController;

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

    /* Javafx Controller displaying currentTime */
    private StartPracticingDialogController controller;

    private Timer timer;
    private TimerTask task;

    /* Default constructor
     * @param start_time the time clock is set at initially
     * @param increment the length, in milliseconds, of each measured interval */
    public StopWatch(StartPracticingDialogController controller, LocalTime start_time, long increment) {
        this.controller = controller;
        currentTime = start_time;
        this.increment = increment;
        secondsIncrement = increment / 1000;

        /* Initialize task to update clock when it is called */
        task = new TimerTask() {
            @Override public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        updateClock();
                    }
                });
            }
        };
    }

    /* Sets whether stopwatch counts up (true) or down (false)
     * @param countUp */
    public void setCountUp(boolean countUp) {
        this.countUp = countUp;
    }

    /* Sets controller where clock is displayed */
    public void setController(StartPracticingDialogController controller) {
        this.controller = controller;
    }

    /* Starts the clock */
    public void start() {
        timer = new Timer();
        timer.schedule(task, 0, increment);
    }

    /* Stops the clock, cancels and purges timer */
    public void stop() {
        timer.cancel();
        timer.purge();
    }

    /* Updates currentTime and incrementsElapsed and sets TextFields */
    public void updateClock() {
        incrementsElapsed++;
        if(countUp)
            currentTime.plusSeconds(secondsIncrement);
        else {
            currentTime.minusSeconds(secondsIncrement);
            // todo: tell if finished
        }
        System.out.println("Current Time: " + currentTime.toString());
        controller.updateHrs(currentTime.getHour());
        controller.updateMin(currentTime.getMinute());
        controller.updateSec(currentTime.getSecond());
    }
}
