package plainsimple;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import plainsimple.view.StartPracticingDialogController;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

/* A class for managing a countup/countdown clock */
public class StopWatch {

    /* Starting time of the clock, which is time before it was started */
    private LocalTime startTime;

    /* The "current time" of the clock, which is displayed */
    private LocalTime currentTime;

    /* Increment, in seconds, to count */
    private int increment;

    /* True if clock is a countup stopwatch; false if countdown timer */
    private boolean countUp;

    /* Javafx Controller displaying currentTime */
    private StartPracticingDialogController controller;

    private Timer timer;

    /* Default constructor
     * @param controller the class that contains the clock GUI display
     * @param start_time the time clock is set at initially
     * @param increment the length, in milliseconds, of each measured interval */
    public StopWatch(StartPracticingDialogController controller, LocalTime startTime, int increment, boolean countUp) {
        this.controller = controller;
        this.startTime = startTime;
        currentTime = startTime;
        this.increment = increment;
        this.countUp = countUp;
    }

    /* Initialize task to update clock when it is called */
    private TimerTask getTask() {
        return new TimerTask() {
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

    /* Sets startTime and resets currentTime to same value
     * @param time */
    public void setStartTime(LocalTime time) { startTime = time; currentTime = time; }

    /* Returns startTime */
    public LocalTime getStartTime() { return startTime; }

    /* Starts the clock */
    public void start() {
        /* Reset timer (cannot restart a canceled Timer) */
        timer = new Timer();
        /* Schedule timer to execute update */
        timer.schedule(getTask(), 0, increment * 1000);
    }

    /* Stops the clock, cancels and purges timer */
    public void stop() {
        timer.cancel();
        timer.purge();
    }

    /* Updates currentTime and incrementsElapsed and sets TextFields */
    public void updateClock() {
        if(countUp)
            currentTime = currentTime.plusSeconds(increment);
        else {
            currentTime = currentTime.minusSeconds(increment);
            // todo: tell if finished
        }
        System.out.println("Current Time: " + currentTime.toString());
        controller.updateHrs(currentTime.getHour());
        controller.updateMin(currentTime.getMinute());
        controller.updateSec(currentTime.getSecond());
    }
}
