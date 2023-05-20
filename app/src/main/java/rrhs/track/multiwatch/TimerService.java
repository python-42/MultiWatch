package rrhs.track.multiwatch;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import java.util.HashMap;

import rrhs.track.multiwatch.time.Timer;

public class TimerService extends Service {
    private final HashMap<String, Timer> map = new HashMap<String, Timer>();
    private final int lapLength;
    private Thread worker;

    public TimerService(int lapLengthMeters) {
        lapLength = lapLengthMeters;
    }

    public void addTimer(String name) {
        map.put(name, new Timer(lapLength));
    }

    public Timer getTimer(String name) {
        return map.get(name);
    }

    public boolean timerExists(String name) {
        return map.containsKey(name);
    }

    public void startUpdateThread(Runnable run) {
        Handler handler = new Handler(Looper.getMainLooper());

        Runnable toRun = new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Log.d("TimerService", "TimerService worker thread interrupted");
                        break;
                    }
                    handler.post(run);
                }
            }
        };
        worker = new Thread(toRun);
        worker.start();
    }

    public void stopWorker() {
        if(worker != null && worker.isAlive()) {
            worker.interrupt();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}