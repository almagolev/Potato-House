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
import com.alma.Activities.ScheduleActivity;
import com.alma.Assists.Api;
import com.alma.Assists.ApiInterface;
import com.alma.Adapters.ScheduleAdapter;
import com.alma.Objects.ScheduleDataModel;
import com.alma.R;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleAllFragment extends Fragment {

    private List<ScheduleDataModel> scheduleDataModelList;
    private ScheduleAdapter scheduleAdapter;
    private Call<List<ScheduleDataModel>> call;
    private ApiInterface apiService;

    private TextView labelEmpty;
    private RecyclerView recyclerView;
    private ScheduleActivity scheduleActivity;

    private String chosenDate;

    public ScheduleAllFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_all, container, false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        scheduleDataModelList = new ArrayList<>();
        scheduleAdapter = new ScheduleAdapter(this.getContext(), scheduleDataModelList,this);

        recyclerView = view.findViewById(R.id.fse_recycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(scheduleAdapter);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);

        labelEmpty = view.findViewById(R.id.fse_labelEmpty);
        apiService = Api.getApi().create(ApiInterface.class);

        scheduleActivity= (ScheduleActivity) getActivity();
        chosenDate=scheduleActivity.getDate();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        callEnqueue();
    }

    public void callEnqueue() {
        call = apiService.getScheduleByDate(chosenDate);
        call.enqueue(new Callback<List<ScheduleDataModel>>() {
            @Override
            public void onResponse(Call<List<ScheduleDataModel>> call, Response<List<ScheduleDataModel>> response) {
                scheduleDataModelList = response.body();
                if (scheduleDataModelList != null)
                    labelEmpty.setVisibility(View.GONE);
                else
                    labelEmpty.setVisibility(View.VISIBLE);
                scheduleAdapter.setScheduleDataModelList(scheduleDataModelList);
            }

            @Override
            public void onFailure(Call<List<ScheduleDataModel>> call, Throwable t) { }
        });
    }
}