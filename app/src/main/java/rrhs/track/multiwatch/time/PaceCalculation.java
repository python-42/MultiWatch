package rrhs.track.multiwatch.time;

public class PaceCalculation {

    /**
     * Returns the estimated 800 meter pace. If either parameter is zero or less, returns pace of zero
     * @param meters distance travelled in meters
     * @param seconds time elapsed since start in seconds
     * @return 800 meter pace
     */
    public static Pace getHalfMilePace(int meters, double seconds) {
        if(meters < 1 || seconds < 1) return new Pace(0);
        return new Pace((800.0 / meters) * seconds);
    }

    /**
     * Returns the estimated 1600 meter pace. If either parameter is zero or less, returns pace of zero
     * @param meters distance travelled in meters
     * @param seconds time elapsed since start in seconds
     * @return 1600 meter pace
     */
    public static Pace getMilePace(int meters, double seconds) {
        if(meters < 1 || seconds < 1) return new Pace(0);
        return new Pace((1600.0 / meters) * seconds);
    }

    /**
     * Returns the estimated 3200 meter pace. If either parameter is zero or less, returns pace of zero
     * @param meters distance travelled in meters
     * @param seconds time elapsed since start in seconds
     * @return 3200 meter pace
     */
    public static Pace getTwoMilePace(int meters, double seconds) {
        if(meters < 1 || seconds < 1) return new Pace(0);
        return new Pace((3200.0 / meters) * seconds);
    }

    /**
     * Returns the estimated 5000 meter (5k) pace. If either parameter is zero or less, returns pace of zero
     * @param meters distance travelled in meters
     * @param seconds time elapsed since start in seconds
     * @return 5k pace
     */
    public static Pace get5kPace(int meters, double seconds) {
        if(meters < 1 || seconds < 1) return new Pace(0);
        return new Pace((5000.0 / meters) * seconds );
    }

}
