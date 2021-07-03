package com.alma.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alma.Activities.ClientsActivity;
import com.alma.Assists.Api;
import com.alma.Assists.ApiInterface;
import com.alma.Adapters.ClientsAdapter;
import com.alma.Objects.ClientDataModel;
import com.alma.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientsAllFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    
    private List<ClientDataModel> clientDataModelList;
    private RecyclerView recyclerView;
    private ClientsAdapter clientsAdapter;
    private RadioButton btnActive, btnUnActive, btnAll, btnActiveW, btnActiveM;
    private RadioGroup radioActives;
    private TextView labelEmpty;
    private Call<List<ClientDataModel>> call;
    private ApiInterface apiService;
    private boolean start=true;

    private enum btn{
        ACTIVE,UNACTIVE,ALL,ACTIVEW,ACTIVEM
    }

    private btn flag= btn.ALL;

    public ClientsAllFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clients_all, container, false);
        radioActives = view.findViewById(R.id.clients_radio_actives);
        radioActives.setOnCheckedChangeListener(this);
        btnAll=view.findViewById(R.id.radioBtn_all);
        btnActive=view.findViewById(R.id.radioBtn_active);
        btnUnActive=view.findViewById(R.id.radioBtn_unactive);
        btnActiveW=view.findViewById(R.id.radioBtn_active_w);
        btnActiveM=view.findViewById(R.id.radioBtn_active_m);

        labelEmpty = view.findViewById(R.id.clients_labelEmpty);
        clientDataModelList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.clientsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        clientsAdapter = new ClientsAdapter(this.getContext(), clientDataModelList,this);
        recyclerView.setAdapter(clientsAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        apiService = Api.getApi().create(ApiInterface.class);
        recyclerView.setHasFixedSize(true);
        flag=btn.ALL;
        return view;
    }
    
    public void callEnqueue() {
        call.enqueue(new Callback<List<ClientDataModel>>() {
            @Override
            public void onResponse(Call<List<ClientDataModel>> call, Response<List<ClientDataModel>> response) {
                clientDataModelList = response.body();
                clientsAdapter.setClientDataModelList(clientDataModelList);
                if(clientDataModelList ==null)
                    labelEmpty.setVisibility(View.VISIBLE);
                else
                    labelEmpty.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ClientDataModel>> call, Throwable t) { call.request(); }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(start) {
            btnAll.setChecked(true);
            start=false;
        }
        else if(call!=null)
            doAct();
    }

    private void doAct(){
        switch (flag){
            case ALL:
                btnActiveW.setClickable(true);
                btnActiveM.setClickable(true);
                btnActive.setClickable(true);
                btnUnActive.setClickable(true);
                btnAll.setClickable(false);
                call = apiService.getAllClients();
                break;

                case ACTIVE:
                    btnActiveW.setClickable(true);
                    btnActiveM.setClickable(true);
                    btnActive.setClickable(false);
                    btnUnActive.setClickable(true);
                    btnAll.setClickable(true);
                    call = apiService.getAllClientsByStatus(true);
                    break;

                    case UNACTIVE:
                    btnActiveW.setClickable(true);
                    btnActiveM.setClickable(true);
                    btnActive.setClickable(true);
                    btnUnActive.setClickable(false);
                    btnAll.setClickable(true);
                    call = apiService.getAllClientsByStatus(false);
                    break;

            case ACTIVEW:
                btnActiveW.setClickable(false);
                btnActiveM.setClickable(true);
                btnActive.setClickable(true);
                btnUnActive.setClickable(true);
                btnAll.setClickable(true);
                call = apiService.getAllClientsByStatus(true,"F");
                break;

            case ACTIVEM:
                btnActiveW.setClickable(true);
                btnActiveM.setClickable(false);
                btnActive.setClickable(true);
                btnUnActive.setClickable(true);
                btnAll.setClickable(true);
                call = apiService.getAllClientsByStatus(true,"M");
                break;
        }
        callEnqueue();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int radioBtnId) {
        switch (radioBtnId){
            case R.id.radioBtn_active:
                flag= btn.ACTIVE;
                break;
                case R.id.radioBtn_unactive:
                    flag= btn.UNACTIVE;
                    break;
                case R.id.radioBtn_all:
                    flag= btn.ALL;
                    break;
            case R.id.radioBtn_active_w:
                flag= btn.ACTIVEW;
                break;
            case R.id.radioBtn_active_m:
                flag= btn.ACTIVEM;
                break;
        }
        doAct();
    }
}