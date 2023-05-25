package rrhs.track.multiwatch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rrhs.track.multiwatch.databinding.FragmentCreatetimerBinding;

public class CreateTimerFragment extends Fragment {

    FragmentCreatetimerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreatetimerBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.timerNameInput.setText("");
                binding.lapDistanceInput.setText(R.string.lap_distance_default_value);
                AppState.getInstance().setCreateFragmentVisible(false);
                NavHostFragment.findNavController(CreateTimerFragment.this).navigateUp();
            }
        });

        binding.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.timerNameInput.getText().toString();
                String lapDistance = binding.lapDistanceInput.getText().toString();

                if(name.equals("") || lapDistance.equals("")) {
                    binding.errorText.setText(R.string.input_blank_error_message);
                    return;
                }

                int distance;
                try {
                    distance = Integer.parseInt(lapDistance);
                }catch (NumberFormatException e) {
                    binding.errorText.setText(R.string.input_bad_number_error_message);
                    return;
                }
                if(distance < 1) {
                    binding.errorText.setText(R.string.input_bad_number_error_message);
                }

                AppState.getInstance().getTimerService().addTimer(name, distance);
                AppState.getInstance().setOperationTimerName(name);
                AppState.getInstance().setCreateFragmentVisible(false);
                NavHostFragment.findNavController(CreateTimerFragment.this).navigate(R.id.action_createTimerFragment_to_ListViewFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}