package rrhs.track.multiwatch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public View onCreateView(

            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentTimerviewBinding.inflate(inflater, container, false);

        service = AppState.getInstance().getTimerService();
        String timerName = AppState.getInstance().getDesiredTimerName();
        if(!service.timerExists(timerName)) {
            service.addTimer(timerName);
        }
        timer = service.getTimer(timerName);
        binding.timerName.setText(timerName);

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

        service.startUpdateThread(() -> updateData());
    }

    public void updateData() {
        binding.timerTime.setText(timer.getElapsedTime().toString());
        binding.halfPace.setText(timer.halfMilePace().toString());
        binding.milePace.setText(timer.milePace().toString());
        binding.twoPace.setText(timer.twoMilePace().toString());

        if(timerRunning != timer.isTimerRunning()) {
            if(timer.isTimerRunning()) {
                binding.startStop.setImageDrawable(ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.baseline_pause_circle_outline_24));
            }else {
                binding.startStop.setImageDrawable(ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.baseline_play_circle_outline_24));
            }
        }
        timerRunning = timer.isTimerRunning();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        service.stopWorker();
        //binding = null;
    }

}