package rrhs.track.multiwatch;

public class AppState {
    private final TimerService timerService;
    private String desiredTimerName;
    private boolean createFragmentVisible = false;
    private static AppState instance;


    private AppState() {
        timerService = new TimerService(400);
    }

    public static AppState getInstance() {
        if(instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public TimerService getTimerService() {
        return timerService;
    }

    public void setDesiredTimerName(String desiredTimerName) {
        this.desiredTimerName = desiredTimerName;
    }
    public String getDesiredTimerName() {return desiredTimerName;}

    public boolean isCreateFragmentVisible() {return createFragmentVisible;}
    public void setCreateFragmentVisible(boolean b) {
        this.createFragmentVisible = b;
    }
}
