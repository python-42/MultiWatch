package rrhs.track.multiwatch.time;

import java.util.ArrayList;

public class Timer {
    private final int lapLengthMeters;
    private double elapsedTimeSeconds = 0;
    private Long startTime;
    private boolean timerRunning = false;

    private final ArrayList<Pace> lapTimes = new ArrayList<>();

    public Timer(int lapLengthMeters) {
        this.lapLengthMeters = lapLengthMeters;
    }

    public void startTimer() {
        startTime = System.currentTimeMillis();
        timerRunning = true;
    }

    public void stopTimer() {
        long stopped = System.currentTimeMillis();//assign right away to minimize inaccuracy caused by code runtime
        if(startTime != null) {
            elapsedTimeSeconds += (stopped - startTime) / 1000.0;
        }
        timerRunning = false;
    }

    public void reset() {
        stopTimer();
        startTime = null;
        elapsedTimeSeconds = 0;
        lapTimes.clear();
    }

    public void lap() {
        lapTimes.add(getElapsedTime());
    }

    public Pace getElapsedTime() {
        if(timerRunning) {
            return new Pace(getTotalElapsedTimeSeconds());
        }
        return new Pace(elapsedTimeSeconds);

    }

    public Pace halfMilePace() {
        return PaceCalculation.getHalfMilePace(lapLengthMeters * lapTimes.size(), getTotalElapsedTimeSeconds());
    }

    public Pace milePace() {
        return PaceCalculation.getMilePace(lapLengthMeters * lapTimes.size(), getTotalElapsedTimeSeconds());
    }

    public Pace twoMilePace() {
        return PaceCalculation.getTwoMilePace(lapLengthMeters * lapTimes.size(), getTotalElapsedTimeSeconds());
    }
    public Pace FiveKilometerPace() {
        return PaceCalculation.get5kPace(lapLengthMeters * lapTimes.size(), getTotalElapsedTimeSeconds());
    }


    public Pace[] getLapTimes() {
        Pace[] rtn = lapTimes.toArray(new Pace[0]);
        double prev = rtn[0].getTotalTimeSeconds();
        for (int i = 1; i < lapTimes.size(); i++) {
            rtn[i] = new Pace(rtn[i].getTotalTimeSeconds() - prev);
            prev += rtn[i].getTotalTimeSeconds();
        }
        return rtn;
    }

    private int getTotalElapsedTimeSeconds() {
        return (int)(elapsedTimeSeconds + (System.currentTimeMillis() - startTime) / 1000.0);
    }
}
