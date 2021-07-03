package com.alma.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alma.Adapters.ClientsListAdapter;
import com.alma.Adapters.RegisterAdapter;
import com.alma.Assists.Api;
import com.alma.Assists.ApiInterface;
import com.alma.Objects.ClientDataModel;
import com.alma.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientsAllNarrowFragment extends Fragment {

    private List<ClientDataModel> clientDataModelList;
    private RecyclerView recyclerView;
    private ClientsListAdapter clientsAdapter;
    private Call<List<ClientDataModel>> call;
    private ApiInterface apiService;

    public ClientsAllNarrowFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_all, container, false);

        clientDataModelList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.fse_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        clientsAdapter = new ClientsListAdapter(this.getContext(), clientDataModelList);
        recyclerView.setAdapter(clientsAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        apiService = Api.getApi().create(ApiInterface.class);
        recyclerView.setHasFixedSize(true);
        callEnqueue();
        return view;
    }
    
    public void callEnqueue() {
        call = apiService.getAllClients();
        call.enqueue(new Callback<List<ClientDataModel>>() {
            @Override
            public void onResponse(Call<List<ClientDataModel>> call, Response<List<ClientDataModel>> response) {
                clientDataModelList = response.body();
                clientsAdapter.setClientDataModelList(clientDataModelList);
            }

            @Override
            public void onFailure(Call<List<ClientDataModel>> call, Throwable t) {
                call.request();
            }
        });
    }
}