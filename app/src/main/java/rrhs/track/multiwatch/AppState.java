package rrhs.track.multiwatch;

public class AppState {
    private final TimerService timerService;
    private String inViewTimerName;
    private String operationTimerName;
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

    public void setInViewTimerName(String inViewTimerName) {
        this.inViewTimerName = inViewTimerName;
    }
    public String getInViewTimerName() {return inViewTimerName;}

    public void setOperationTimerName(String operationTimerName) {
        this.operationTimerName = operationTimerName;
    }
    public String getOperationTimerName() {return operationTimerName;}

    public boolean isCreateFragmentVisible() {return createFragmentVisible;}
    public void setCreateFragmentVisible(boolean b) {
        this.createFragmentVisible = b;
    }
}
