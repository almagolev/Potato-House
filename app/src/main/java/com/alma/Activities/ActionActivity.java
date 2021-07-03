package com.alma.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alma.Adapters.ActionAdapter;
import com.alma.Assists.Api;
import com.alma.Assists.ApiInterface;
import com.alma.Objects.ActionDataModel;
import com.alma.R;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActionActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout1;
    private ActionAdapter actionAdapter;
    private List<ActionDataModel> actionDataModelList;
    private ApiInterface apiService;
    private final String[] tab1={"CLIENT","WORKOUT","SCHEDULE"};
    private final String[] tab2={"ADD","UPDATE","DELETE","JOIN","SIGN","CANCEL","ATTENDED"};
    private int i1=0,i2=0;
    private TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        noData=findViewById(R.id.action_labelEmpty);
        tabLayout1 = findViewById(R.id.action_tabbedLayout1);
        tabLayout1.addOnTabSelectedListener(this);
        TabLayout tabLayout2 = findViewById(R.id.action_tabbedLayout2);
        tabLayout2.addOnTabSelectedListener(this);
        RecyclerView recyclerView = findViewById(R.id.action_recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        actionDataModelList = new ArrayList<>();
        actionAdapter = new ActionAdapter(this, actionDataModelList);
        recyclerView.setAdapter(actionAdapter);
        recyclerView.addItemDecoration(dividerItemDecoration);
        apiService = Api.getApi().create(ApiInterface.class);
        recyclerView.setHasFixedSize(true);
        showData();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        assert tab.parent != null;
        if(tab.parent.getId()==tabLayout1.getId())
            i1 = tab.getPosition();
        else
            i2=tab.getPosition();
        showData();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) { }

    @Override
    public void onTabReselected(TabLayout.Tab tab) { }

    private void showData(){
        Call<List<ActionDataModel>> call = apiService.getAllActionsByChoice(tab1[i1], tab2[i2]);
        call.enqueue(new Callback<List<ActionDataModel>>() {
            @Override
            public void onResponse(Call<List<ActionDataModel>> call, Response<List<ActionDataModel>> response) {
                actionDataModelList = response.body();
                if(actionDataModelList !=null)
                    noData.setVisibility(View.GONE);
                else
                    noData.setVisibility(View.VISIBLE);
                actionAdapter.setActionDataModelList(actionDataModelList);
            }

            @Override
            public void onFailure(Call<List<ActionDataModel>> call, Throwable t) {
                noData.setVisibility(View.VISIBLE);
                noData.setTextColor(Color.RED);
            }
        });
    }
}