package com.alma.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alma.Assists.Api;
import com.alma.Assists.ApiInterface;
import com.alma.Adapters.WorkoutsListAdapter;
import com.alma.Objects.WorkoutDataModel;
import com.alma.R;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkoutsAllFragment extends Fragment {
    
    private List<WorkoutDataModel> workoutDataModelList;
    private RecyclerView recyclerView;
    private WorkoutsListAdapter workoutsAdapter;
    private Call<List<WorkoutDataModel>> call;
    private ApiInterface apiService;
    private TextView labelEmpty;

    public WorkoutsAllFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workouts_all, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);

        workoutDataModelList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.fwa_recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        workoutsAdapter = new WorkoutsListAdapter(this.getContext(), workoutDataModelList);
        recyclerView.setAdapter(workoutsAdapter);
        recyclerView.addItemDecoration(dividerItemDecoration);
        apiService = Api.getApi().create(ApiInterface.class);
        recyclerView.setHasFixedSize(true);
        call= apiService.getAllWorkoutsByStatus(true);
        labelEmpty=view.findViewById(R.id.fwa_labelEmpty);
        callEnqueue();
        return view;
    }
    
    public void callEnqueue() {
        call.enqueue(new Callback<List<WorkoutDataModel>>() {
            @Override
            public void onResponse(Call<List<WorkoutDataModel>> call, Response<List<WorkoutDataModel>> response) {
                workoutDataModelList = response.body();
                workoutsAdapter.setWorkoutDataModelList(workoutDataModelList);
                if(workoutDataModelList ==null)
                    labelEmpty.setVisibility(View.VISIBLE);
                else
                    labelEmpty.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<WorkoutDataModel>> call, Throwable t) {}
        });
    }
}