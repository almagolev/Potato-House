package com.alma.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.alma.Activities.ScheduleActivity;
import com.alma.Assists.Api;
import com.alma.Assists.ApiInterface;
import com.alma.Adapters.WorkoutsScheduleAdapter;
import com.alma.Adapters.WorkoutsSpinnerAdapter;
import com.alma.Objects.ScheduleDataModel;
import com.alma.Objects.WorkoutDataModel;
import com.alma.R;
import org.jetbrains.annotations.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Calendar.getInstance;

public class ScheduleAddFragment extends Fragment implements View.OnKeyListener, View.OnClickListener {

    private List<WorkoutDataModel> workoutDataModelList;
    private WorkoutDataModel workoutDataModel;
    private ScheduleDataModel scheduleDataModel;
    private WorkoutsScheduleAdapter workoutsScheduleAdapter;
    private Call<List<WorkoutDataModel>> callW;
    private ApiInterface apiService;
    private ScheduleActivity scheduleActivity;

    private EditText timeStart, timeEnd;
    private Spinner workoutSpinner;
    private int length=0;

    public ScheduleAddFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_add, container, false);
        view.findViewById(R.id.fsaw_btn_add_workout).setOnClickListener(this);
        workoutDataModelList = new ArrayList<>();
        workoutsScheduleAdapter = new WorkoutsScheduleAdapter(this.getContext(), workoutDataModelList,this);
        scheduleDataModel=new ScheduleDataModel();

        workoutSpinner = view.findViewById(R.id.fsaw_workouts);

        timeStart = view.findViewById(R.id.fsaw_time_start);
        timeEnd = view.findViewById(R.id.fsaw_time_end);
        timeStart.setOnKeyListener(this);
        apiService = Api.getApi().create(ApiInterface.class);

        scheduleActivity = (ScheduleActivity) getActivity();
        callEnqueue();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(callW !=null)
            callEnqueue();
        hideKey();
    }

    public void callEnqueue() {
        callW = apiService.getAllWorkoutsByStatus(true);
        callW.enqueue(new Callback<List<WorkoutDataModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<WorkoutDataModel>> call, Response<List<WorkoutDataModel>> response) {
                workoutDataModelList = response.body();
                workoutsScheduleAdapter.setWorkoutDataModelList(workoutDataModelList);
                WorkoutsSpinnerAdapter adapter = new WorkoutsSpinnerAdapter(getContext(),workoutDataModelList);
                workoutSpinner.setAdapter(adapter);
                workoutSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        workoutDataModel= (WorkoutDataModel) parent.getItemAtPosition(position);
                        length = workoutDataModel.getLength_min();
                    }
                    @Override
                    public void onNothingSelected(AdapterView <?> parent) { }});
            }
            @Override
            public void onFailure(@NotNull Call<List<WorkoutDataModel>> call, @NotNull Throwable t) { }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        Calendar calendar = getInstance();
        if (i == KeyEvent.KEYCODE_ENTER && timeStart.getText().toString().length() ==5) {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            Date d = null;
            try {
                d = df.parse(timeStart.getText().toString());
            } catch (ParseException e) { e.printStackTrace(); }
            calendar.setTime(d);
            calendar.add(Calendar.MINUTE, length);
            String newTime = df.format(calendar.getTime());
            timeEnd.setText(newTime);
            hideKey();
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        hideKey();
        switch (view.getId()){
            case R.id.fsaw_btn_add_workout:
                if (LocalDate.now().isAfter(LocalDate.parse(scheduleActivity.getDate()))||(
                        LocalDate.now().isEqual(LocalDate.parse(scheduleActivity.getDate()))&&
                                LocalTime.parse(timeStart.getText().toString()).isBefore(LocalTime.now()))) {
                    new AlertDialog.Builder(this.getContext())
                            .setMessage("CANT ADD WORKOUT TO THE PAST!")
                            .setIcon(R.drawable.ic_baseline_error_24)
                            .setTitle("Error!")
                            .setNegativeButton("OK", null)
                            .show();
                }
                else {
                    scheduleDataModel.setW_id(workoutDataModel.getId());
                    scheduleDataModel.setW_title(workoutDataModel.getTitle());
                    scheduleDataModel.setW_date(scheduleActivity.getDate());
                    scheduleDataModel.setW_max(workoutDataModel.getMax_trainers());
                    scheduleDataModel.setW_start(timeStart.getText().toString());
                    scheduleDataModel.setW_end(timeEnd.getText().toString());
                    scheduleDataModel.setW_length(workoutDataModel.getLength_min());
                    Call<ScheduleDataModel> callS = apiService.createSchedule(scheduleDataModel);
                    callS.enqueue(new Callback<ScheduleDataModel>() {
                        @Override
                        public void onResponse(@NotNull Call<ScheduleDataModel> call, @NotNull Response<ScheduleDataModel> response) { }

                        @Override
                        public void onFailure(@NotNull Call<ScheduleDataModel> call, @NotNull Throwable t) { }
                    });
                    scheduleActivity.changeTab(0);
                }
                break;
            case R.id.fsaw_btn_cancel:
                scheduleActivity.changeTab(0);
                break;
        }
    }
    private void clear(){
        scheduleActivity.changeTab(1);
    }
    private void hideKey(){
        try {
            InputMethodManager keyboard = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(this.getView().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}