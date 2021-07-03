package com.alma.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.alma.Activities.WorkoutsActivity;
import com.alma.Assists.Api;
import com.alma.Assists.ApiInterface;
import com.alma.Objects.WorkoutDataModel;
import com.alma.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkoutsAddFragment extends Fragment implements View.OnClickListener {

    private TextView title,length,max,min;
    private WorkoutDataModel workoutDataModel;
    private Spinner spinner;
    private WorkoutsActivity workoutsActivity;

    public WorkoutsAddFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_add, container, false);

        title = view.findViewById(R.id.fwa_title);
        length = view.findViewById(R.id.fwa_length);
        max = view.findViewById(R.id.fwa_max);
        min = view.findViewById(R.id.fwa_min_age);
        spinner = view.findViewById(R.id.fwa_spinner_difficulty);

        view.findViewById(R.id.fwa_btnAdd).setOnClickListener(this);
        view.findViewById(R.id.fwa_btnClear).setOnClickListener(this);
        view.findViewById(R.id.fwa_btnCancel).setOnClickListener(this);

        workoutDataModel = new WorkoutDataModel();
        workoutsActivity= (WorkoutsActivity) getActivity();

        return view;
    }

    private void doSave() {
        try {
            workoutDataModel.setTitle(title.getText().toString());
            workoutDataModel.setLength_min(Integer.parseInt(length.getText().toString()));
            workoutDataModel.setMin_age(Integer.parseInt(min.getText().toString()));
            workoutDataModel.setMax_trainers(Integer.parseInt(max.getText().toString()));
            workoutDataModel.setDifficulty(spinner.getSelectedItem().toString());
            workoutDataModel.setIs_active(true);

            Api.getApi().create(ApiInterface.class).createWorkout(workoutDataModel).enqueue(new Callback<WorkoutDataModel>() {
                @Override
                public void onResponse(Call<WorkoutDataModel> call, Response<WorkoutDataModel> response) { }

                @Override
                public void onFailure(Call<WorkoutDataModel> call, Throwable t) {
                    System.out.println(t.getMessage());
                }
            });
            workoutsActivity.changeTab(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onClick(View view) {
        try {
            InputMethodManager keyboard = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (view.getId()){
            case R.id.fwa_btnAdd:
                doSave();
                break;
            case R.id.fwa_btnClear:
                title.setText("");
                length.setText("");
                max.setText("");
                min.setText("");
                spinner.setSelection(0);
                break;
            case R.id.fwa_btnCancel:
                workoutsActivity.changeTab(0);
                break;
        }
    }
}