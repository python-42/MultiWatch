package rrhs.track.multiwatch.time;

import androidx.annotation.NonNull;

/**
 * A class to represent an amount of time in a more natural, human-readable way.
 * Each method in this class returns the component of the total time which the given unit makes up, not the complete time represented in the given unit.
 * Please note that the initial component calculation may suffer from rounding errors present in all floating point number calculation.
 * Some steps have been taken to mitigate this.
 */
public class Pace {
    private final int minutes;
    private final int seconds;
    private final int milliseconds;// 1/100th of a second

    public Pace(double seconds) {
        int secondsInt = (int) seconds;
        this.milliseconds = (int) ((seconds - secondsInt) * 1000 + 0.000000001);//should hopefully fix floating point errors (10^-9)
        this.seconds = secondsInt % 60;
        this.minutes = (int) (secondsInt / 60.0);
    }

    public Pace(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = 0;
    }

    /**
     * Returns the minute component of the pace
     *
     * @return integer minute component
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Returns the second component of the pace
     *
     * @return integer second component
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Returns the millisecond (1/100th of 1 second) component of the pace
     *
     * @return integer millisecond component
     */
    public int getMilliseconds() {
        return milliseconds;
    }

    /**
     * Gets the total time represented by this object in seconds
     * @return total time in seconds
     */
    public double getTotalTimeSeconds() {
        return minutes * 60 + seconds + milliseconds * 0.001;
    }

    @NonNull
    public String toString() {return zeroPad(minutes, 2) + ":" + zeroPad(seconds, 2) + "." + zeroPad(milliseconds, 3);}

    private String zeroPad(int num, int desiredLen) {
        String rtn = String.valueOf(num);
        while(rtn.length() < desiredLen) {
            //not best practice as a new object is created each time, making this inefficient memory wise. However in this application memory usage is fairly negligible
            rtn = "0" + rtn;
        }
        return rtn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pace pace = (Pace) o;
        return minutes == pace.minutes && seconds == pace.seconds && milliseconds == pace.milliseconds;
    }
}
