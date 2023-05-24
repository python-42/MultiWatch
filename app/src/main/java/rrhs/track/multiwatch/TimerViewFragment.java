package rrhs.track.multiwatch;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import rrhs.track.multiwatch.databinding.FragmentTimerviewBinding;
import rrhs.track.multiwatch.time.Timer;

public class TimerViewFragment extends Fragment {

    private FragmentTimerviewBinding binding;
    private TimerService service;
    private Timer timer;

    private boolean timerRunning = false;
    private int lapNumber = 0;
    private long workerID;

    @Override
    public View onCreateView(

            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentTimerviewBinding.inflate(inflater, container, false);

        service = AppState.getInstance().getTimerService();
        String timerName = AppState.getInstance().getInViewTimerName();

        if(timerName == null) {
            timer = new Timer(400);
            binding.timerName.setText(R.string.anonymous_timer);
        }else {
            if(!service.timerExists(timerName)) {
                service.addTimer(timerName);
            }
            timer = service.getTimer(timerName);
            binding.timerName.setText(timerName);
        }
        binding.lapLength.setText(getString(R.string.lap_length_text, Integer.toString(timer.getLapLengthMeters())));

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.lapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.lap();
            }
        });

        binding.reset.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 timer.reset();
             }
         });

        binding.startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timer.isTimerRunning()) {
                    timer.stopTimer();
                }else {
                    timer.startTimer();
                }
            }
        });

        workerID = service.startUpdateThread(() -> updateData());
    }

    public void updateData() {
        if(binding != null) {
            binding.timerTime.setText(timer.getElapsedTime().toString());
            binding.halfPace.setText(timer.halfMilePace().toString());
            binding.milePace.setText(timer.milePace().toString());
            binding.twoPace.setText(timer.twoMilePace().toString());

            if(lapNumber != timer.getLapCount()) {
                if(timer.getLapCount() == 0) {
                    resetLapDisplay();
                }else {
                    TextView previousLap = (TextView) binding.lapContainer.getChildAt(lapNumber);
                    previousLap.setText(getString(R.string.lap_text, Integer.toString(lapNumber + 1), timer.getLapTimes()[lapNumber]));
                    addLap();
                }
            }

            TextView child = (TextView) binding.lapContainer.getChildAt(lapNumber);
            child.setText(getString(R.string.lap_text, Integer.toString(lapNumber + 1), timer.getIncompleteLapTime()));

            if(timerRunning != timer.isTimerRunning()) {
                if(timer.isTimerRunning()) {
                    binding.startStop.setImageDrawable(ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.baseline_pause_circle_outline_24));
                }else {
                    binding.startStop.setImageDrawable(ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.baseline_play_circle_outline_24));
                }
            }

            lapNumber = timer.getLapCount();
            timerRunning = timer.isTimerRunning();
        }
    }

    private void addLap() {
        lapNumber++;
        TextView child = new TextView(this.getContext());

        child.setGravity(Gravity.CENTER_HORIZONTAL);
        child.setTextColor(Color.WHITE);

        binding.lapContainer.addView(child);//text is set within update loop
    }

    private void resetLapDisplay() {
        binding.lapContainer.removeAllViews();
        addLap();
        lapNumber = 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        service.stopWorker(workerID);

        binding = null;
    }

}