package com.alma.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alma.Assists.Api;
import com.alma.Assists.ApiInterface;
import com.alma.Fragments.ScheduleAllFragment;
import com.alma.Objects.ClientDataModel;
import com.alma.Objects.PartDataModel;
import com.alma.Objects.ScheduleDataModel;
import com.alma.R;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {

    Context context;
    List<ScheduleDataModel> scheduleDataModelList;
    List<PartDataModel> partDataModelList;
    ApiInterface apiService;
    ScheduleAdapter scheduleAdapter;
    ClientDataModel clientDataModel;
    PartDataModel partDataModel;
    ScheduleAllFragment scheduleAllFragment;

    public ScheduleAdapter(Context context, List<ScheduleDataModel> scheduleDataModelList,ScheduleAllFragment scheduleAllFragment) {
        this.context = context;
        this.scheduleDataModelList = scheduleDataModelList;
        this.apiService = Api.getApi().create(ApiInterface.class);
        this.scheduleAllFragment = scheduleAllFragment;
        scheduleAdapter = this;
    }

    public void setScheduleDataModelList(List<ScheduleDataModel> scheduleDataModelList) {
        this.scheduleDataModelList = scheduleDataModelList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.schedule_item, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ScheduleDataModel scheduleDataModel = scheduleDataModelList.get(position);
        if (scheduleDataModel != null) {
            holder.labelTitle.setText(scheduleDataModel.getW_title());
            holder.labelTime.setText(scheduleDataModel.getW_start() + "-" + scheduleDataModel.getW_end());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(LocalDate.parse(scheduleDataModel.getW_date()).isAfter(LocalDate.now()) || (scheduleDataModel.getW_date().equals(LocalDate.now().toString())
                            && scheduleDataModel.getW_start().compareTo(String.valueOf(LocalTime.now()))>0)){
                        openDialog(scheduleDataModel);
//                    final AlertDialog whatsNext = new AlertDialog.Builder(context).create();
//                    whatsNext.setTitle(context.getString(R.string.potato_house));
//                    whatsNext.setMessage("what would u like to do?");
//                    whatsNext.setButton(Dialog.BUTTON_NEGATIVE, String.format("DELETE %s FROM SCHEDULE", scheduleDataModel.getW_title().toUpperCase()), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            Api.getApi().create(ApiInterface.class).deleteScheduleById(scheduleDataModel.getId()).enqueue(new Callback<Void>() {
//                                @Override
//                                public void onResponse(Call<Void> call, Response<Void> response) {
//                                    scheduleAllFragment.onResume();
//                                }
//
//                                @Override
//                                public void onFailure(Call<Void> call, Throwable t) { }
//                            });
//                        }
//                    });
//                    whatsNext.setButton(DialogInterface.BUTTON_POSITIVE, "ADD A TRAINER TO THIS WORKOUT", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            Api.getApi().create(ApiInterface.class).getAllAvailableClients(scheduleDataModel.getId()).enqueue(new Callback<List<ClientDataModel>>() {
//                                @Override
//                                public void onResponse(Call<List<ClientDataModel>> call, Response<List<ClientDataModel>> response) {
//                                    if (scheduleDataModel.getW_max() > scheduleDataModel.getW_signed()) {
//                                        View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_item, null);
//                                        final List<ClientDataModel> clientDataModelList = response.body();
//                                        if (!clientDataModelList.isEmpty()) {
//                                            final Spinner spinner = view.findViewById(R.id.adi_spinner);
//                                            final ClientsArrayAdapter adapter = new ClientsArrayAdapter(context, clientDataModelList);
//                                            spinner.setAdapter(adapter);
//                                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                                @Override
//                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                                    clientDataModel = (ClientDataModel) parent.getItemAtPosition(position);
//                                                    partDataModel = new PartDataModel();
//                                                    partDataModel.setC_first(clientDataModel.getFirst_name());
//                                                    partDataModel.setC_last(clientDataModel.getLast_name());
//                                                    partDataModel.setC_phone(clientDataModel.getPhone_number());
//                                                    partDataModel.setC_gender(clientDataModel.getGender());
//                                                    partDataModel.setC_id(clientDataModel.getId());
//                                                    partDataModel.setS_id(scheduleDataModel.getId());
//                                                    partDataModel.setW_date(scheduleDataModel.getW_date());
//                                                    partDataModel.setW_start(scheduleDataModel.getW_start());
//                                                    partDataModel.setW_end(scheduleDataModel.getW_end());
//                                                    partDataModel.setW_title(scheduleDataModel.getW_title());
//                                                    partDataModel.setW_id(scheduleDataModel.getW_id());
//                                                }
//
//                                                @Override
//                                                public void onNothingSelected(AdapterView<?> parent) {
//                                                }
//                                            });
//                                            AlertDialog dialog = new AlertDialog.Builder(context)
//                                                    .setTitle(R.string.potato_house)
//                                                    .setMessage("Adding trainer to workout")
//                                                    .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                                            Api.getApi().create(ApiInterface.class).insertPart(partDataModel).enqueue(new Callback<PartDataModel>() {
//                                                                @Override
//                                                                public void onResponse(Call<PartDataModel> call, Response<PartDataModel> response) {
//                                                                    notifyDataSetChanged();
//                                                                }
//
//                                                                @Override
//                                                                public void onFailure(Call<PartDataModel> call, Throwable t) {
//                                                                }
//                                                            });
//                                                            scheduleDataModel.setW_signed(scheduleDataModel.getW_signed() + 1);
//                                                            scheduleAdapter.notifyDataSetChanged();
//                                                        }
//                                                    })
//                                                    .setNegativeButton("CANCEL", null)
//                                                    .setView(view)
//                                                    .create();
//                                            dialog.show();
//                                        }
//                                    } else
//                                        openDialogError("WORKOUT IS FULL!");
//                                }
//
//                                @Override
//                                public void onFailure(Call<List<ClientDataModel>> call, Throwable t) { }
//                            });
//                            notifyDataSetChanged();
//                        }
//                    });
//                    whatsNext.show();
                    scheduleAdapter.notifyDataSetChanged();
                } else
                        openDialogError("WORKOUT HAS PASSED!");
                }
            });
            Api.getApi().create(ApiInterface.class).getPartBySID(scheduleDataModel.getId()).enqueue(new Callback<List<PartDataModel>>() {
                @Override
                public void onResponse(Call<List<PartDataModel>> call, Response<List<PartDataModel>> response) {
                    partDataModelList = response.body();
                    if (partDataModelList != null)
                        holder.labelRegistered.setVisibility(View.GONE);
                    else
                        holder.labelRegistered.setVisibility(View.VISIBLE);
                    RegisterAdapter registerAdapter = new RegisterAdapter(context, partDataModelList, scheduleAdapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    holder.recyclerView.setLayoutManager(layoutManager);
                    holder.recyclerView.setAdapter(registerAdapter);
                    holder.recyclerView.setHasFixedSize(true);
//                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL);
//                    holder.recyclerView.addItemDecoration(dividerItemDecoration);
                    registerAdapter.setPartDataModelList(partDataModelList);
                }
                @Override
                public void onFailure(Call<List<PartDataModel>> call, Throwable t) { }
            });
            holder.labelSigned.setText(scheduleDataModel.getW_signed() + "/" + scheduleDataModel.getW_max());
        }
    }


    @Override
    public int getItemCount() {
        if(scheduleDataModelList != null)
            return scheduleDataModelList.size();
        else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView labelTitle, labelTime, labelRegistered,labelSigned;
        RecyclerView recyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);
            labelTitle = itemView.findViewById(R.id.si_label_title);
            labelTime = itemView.findViewById(R.id.si_label_time);
            labelRegistered = itemView.findViewById(R.id.si_label_registered);
            labelSigned = itemView.findViewById(R.id.si_label_signed);
            recyclerView = itemView.findViewById(R.id.si_recycler);
        }
    }
    private void openDialogError(String errorMsg){
        new AlertDialog.Builder(context)
                .setMessage(errorMsg)
                .setIcon(R.drawable.ic_baseline_error_24)
                .setTitle("Error!")
                .setNegativeButton("OK", null)
                .show();
    }

    private void openDialog(final ScheduleDataModel scheduleDataModelOther){
        final AlertDialog whatsNext = new AlertDialog.Builder(context).create();
        whatsNext.setTitle(context.getString(R.string.potato_house));
        whatsNext.setMessage("what would u like to do?");
        whatsNext.setButton(Dialog.BUTTON_NEGATIVE, String.format("DELETE %s FROM SCHEDULE", scheduleDataModelOther.getW_title().toUpperCase()), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Api.getApi().create(ApiInterface.class).deleteScheduleById(scheduleDataModelOther.getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        scheduleAllFragment.onResume();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) { }
                });
            }
        });
        whatsNext.setButton(DialogInterface.BUTTON_POSITIVE, "ADD A TRAINER TO THIS WORKOUT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Api.getApi().create(ApiInterface.class).getAllAvailableClients(scheduleDataModelOther.getId()).enqueue(new Callback<List<ClientDataModel>>() {
                    @Override
                    public void onResponse(Call<List<ClientDataModel>> call, Response<List<ClientDataModel>> response) {
                        if (scheduleDataModelOther.getW_max() > scheduleDataModelOther.getW_signed()) {
                            View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_item, null);
                            final List<ClientDataModel> clientDataModelList = response.body();
                            if (clientDataModelList!=null) {
                                final Spinner spinner = view.findViewById(R.id.adi_spinner);
                                final ClientsArrayAdapter adapter = new ClientsArrayAdapter(context, clientDataModelList);
                                spinner.setAdapter(adapter);
                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        clientDataModel = (ClientDataModel) parent.getItemAtPosition(position);
                                        partDataModel = new PartDataModel();
                                        partDataModel.setC_first(clientDataModel.getFirst_name());
                                        partDataModel.setC_last(clientDataModel.getLast_name());
                                        partDataModel.setC_phone(clientDataModel.getPhone_number());
                                        partDataModel.setC_came("N");
                                        partDataModel.setC_gender(clientDataModel.getGender());
                                        partDataModel.setC_id(clientDataModel.getId());
                                        partDataModel.setS_id(scheduleDataModelOther.getId());
                                        partDataModel.setW_date(scheduleDataModelOther.getW_date());
                                        partDataModel.setW_start(scheduleDataModelOther.getW_start());
                                        partDataModel.setW_end(scheduleDataModelOther.getW_end());
                                        partDataModel.setW_title(scheduleDataModelOther.getW_title());
                                        partDataModel.setW_id(scheduleDataModelOther.getW_id());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) { }
                                });
                                AlertDialog dialog = new AlertDialog.Builder(context)
                                        .setTitle(R.string.potato_house)
                                        .setMessage("Adding trainer to workout")
                                        .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Api.getApi().create(ApiInterface.class).insertPart(partDataModel).enqueue(new Callback<PartDataModel>() {
                                                    @Override
                                                    public void onResponse(Call<PartDataModel> call, Response<PartDataModel> response) {
                                                        notifyDataSetChanged();
                                                    }

                                                    @Override
                                                    public void onFailure(Call<PartDataModel> call, Throwable t) { }
                                                });
                                                scheduleDataModelOther.setW_signed(scheduleDataModelOther.getW_signed() + 1);
                                                scheduleAdapter.notifyDataSetChanged();
                                            }
                                        })
                                        .setNegativeButton("CANCEL", null)
                                        .setView(view)
                                        .create();
                                dialog.show();
                            }else
                                openDialogError("NO ACTIVE CLIENTS TO ADD!");
                        } else
                            openDialogError("WORKOUT IS FULL!");
                    }

                    @Override
                    public void onFailure(Call<List<ClientDataModel>> call, Throwable t) { }
                });
                notifyDataSetChanged();
            }
        });
        whatsNext.show();
    }
}