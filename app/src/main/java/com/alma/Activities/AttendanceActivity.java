package com.alma.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alma.Adapters.AttendanceScheduleAdapter;
import com.alma.Assists.Api;
import com.alma.Assists.ApiInterface;
import com.alma.Objects.ScheduleDataModel;
import com.alma.R;
import com.google.android.material.tabs.TabLayout;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceActivity extends AppCompatActivity {

    private TextView labelEmpty;
    private String today;
    private List<ScheduleDataModel> scheduleDataModelList;
    private AttendanceScheduleAdapter adapter;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        TabLayout tabLayout = findViewById(R.id.attendance_tabbedLayout);
        RecyclerView recyclerView = findViewById(R.id.attendance_recyclerView);
        labelEmpty=findViewById(R.id.attendance_labelEmpty);
        Calendar calendar=Calendar.getInstance();
        today= String.format("%d-%02d-%02d",calendar.get(Calendar.YEAR),(calendar.get(Calendar.MONTH)+1),calendar.get(Calendar.DAY_OF_MONTH));
        tabLayout.getTabAt(0).setText(today+ " TODAY'S WORKOUTS");
        scheduleDataModelList = new ArrayList<>();
        adapter = new AttendanceScheduleAdapter(this,scheduleDataModelList);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        callEnqueue();
    }

    public void callEnqueue() {
        Api.getApi().create(ApiInterface.class).getScheduleByDate(today).enqueue(new Callback<List<ScheduleDataModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<ScheduleDataModel>> call, @NotNull Response<List<ScheduleDataModel>> response) {
                scheduleDataModelList = response.body();
                if (scheduleDataModelList != null)
                    labelEmpty.setVisibility(View.GONE);
                else
                    labelEmpty.setVisibility(View.VISIBLE);
                adapter.setScheduleDataModelList(scheduleDataModelList);
            }

            @Override
            public void onFailure(@NotNull Call<List<ScheduleDataModel>> call, @NotNull Throwable t) { }
        });
    }
}