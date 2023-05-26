package rrhs.track.multiwatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;

import rrhs.track.multiwatch.time.Timer;

public class SmallTimerView extends LinearLayout {

    private final String timerName;
    private final int lapDistanceMeters;
    private ImageButton resetButton;
    private ImageButton startStopButton;
    private ImageButton infoButton;
    private TextView timerTime;
    private boolean timerRunning;
    private Timer timer;

    public SmallTimerView(Context context) {
        this(context, null);
    }

    public SmallTimerView(Context context, String timerName) {
        super(context);
        this.timerName = timerName;
        this.lapDistanceMeters = 400;
        init(context);
    }

    public SmallTimerView(Context context, String timerName, int lapDistanceMeters) {
        super(context);
        this.timerName = timerName;
        this.lapDistanceMeters = lapDistanceMeters;
        init(context);
    }

    private void init(Context context) {
        initLayout(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_timercomponent, this);

        TimerService service = AppState.getInstance().getTimerService();
        if(! service.timerExists(timerName)) {
            service.addTimer(timerName, lapDistanceMeters);
        }
        timer = service.getTimer(timerName);

        resetButton = this.findViewById(R.id.timer_view_component_reset_button);
        startStopButton = this.findViewById(R.id.timer_view_component_startStop_button);
        infoButton = this.findViewById(R.id.timer_view_component_info_button);
        timerTime = this.findViewById(R.id.timer_view_component_timertime);

        TextView timerNameView = this.findViewById(R.id.timer_view_component_timerName);
        timerNameView.setText(timerName);

        initButtonListeners();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initLayout(Context context) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(30, 30, 30, 30);
        this.setLayoutParams(params);

        this.setPadding(20, 20, 20, 20);
        this.setOrientation(LinearLayout.VERTICAL);
        this.setGravity(Gravity.CENTER);
        this.setBackground(getResources().getDrawable(R.drawable.small_timer_view_round_corner, context.getTheme()));
    }

    private void initButtonListeners() {
        resetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.reset();
            }
        });

        infoButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AppState.getInstance().setInViewTimerName(timerName);
                Navigation.findNavController(SmallTimerView.this).navigate(R.id.TimerViewFragment);
            }
        });

        startStopButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timer.isTimerRunning()) {
                    timer.stopTimer();
                }else {
                    timer.startTimer();
                }
            }
        });
    }

    public void updateData() {
        if(timer != null) {
            timerTime.setText(timer.getElapsedTime().toString());

            if(timerRunning != timer.isTimerRunning()) {
                if(timer.isTimerRunning()) {
                    startStopButton.setImageDrawable(ContextCompat.getDrawable(this.startStopButton.getContext(), R.drawable.baseline_pause_circle_outline_24));
                }else {
                    startStopButton.setImageDrawable(ContextCompat.getDrawable(this.startStopButton.getContext(), R.drawable.baseline_play_circle_outline_24));
                }
            }

            timerRunning = timer.isTimerRunning();
        }
    }
}
