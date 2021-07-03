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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceScheduleAdapter extends RecyclerView.Adapter<AttendanceScheduleAdapter.MyViewHolder> {

    Context context;
    List<ScheduleDataModel> scheduleDataModelList;
    List<PartDataModel> partDataModelList;
    ApiInterface apiService;
    AttendanceScheduleAdapter scheduleAdapter;

    public AttendanceScheduleAdapter(Context context, List<ScheduleDataModel> scheduleDataModelList) {
        this.context = context;
        this.scheduleDataModelList = scheduleDataModelList;
        this.apiService = Api.getApi().create(ApiInterface.class);
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
            Api.getApi().create(ApiInterface.class).getPartBySID(scheduleDataModel.getId()).enqueue(new Callback<List<PartDataModel>>() {
                @Override
                public void onResponse(Call<List<PartDataModel>> call, Response<List<PartDataModel>> response) {
                    partDataModelList = response.body();
                    if (partDataModelList != null)
                        holder.labelRegistered.setVisibility(View.GONE);
                    else
                        holder.labelRegistered.setVisibility(View.VISIBLE);
                    AttendanceAdapter attendanceAdapter = new AttendanceAdapter(context, partDataModelList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    holder.recyclerView.setLayoutManager(layoutManager);
                    holder.recyclerView.setAdapter(attendanceAdapter);
                    holder.recyclerView.setHasFixedSize(true);
//                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL);
//                    holder.recyclerView.addItemDecoration(dividerItemDecoration);
                    attendanceAdapter.setPartDataModelList(partDataModelList);
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
}