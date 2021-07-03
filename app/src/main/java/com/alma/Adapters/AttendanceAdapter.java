package com.alma.Adapters;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alma.Assists.Api;
import com.alma.Assists.ApiInterface;
import com.alma.Objects.PartDataModel;
import com.alma.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder> {

    private Context context;
    private List<PartDataModel> partDataModelList;

    public AttendanceAdapter(Context context, List<PartDataModel> partDataModelList ) {
        this.context = context;
        this.partDataModelList = partDataModelList;
    }

    public void setPartDataModelList(List<PartDataModel> partDataModelList) {
        this.partDataModelList = partDataModelList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.attendance_item,parent,false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final PartDataModel partDataModel = partDataModelList.get(position);
        if (partDataModel != null) {
            final String full_name = partDataModel.getC_first()+" "+partDataModel.getC_last();
            if(partDataModel.notInRange() || partDataModel.getC_came().equals("Y"))
                holder.btnName.setEnabled(false);
            if (partDataModel.getC_came().equals("Y"))
                holder.btnName.setChecked(true);
            holder.btnName.setText(full_name);
            holder.btnName.setTextOn(full_name);
            holder.btnName.setTextOff(full_name);
            holder.btnName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    String came = (b ? "Y" : "N");
                    partDataModel.setC_came(came);
                    Api.getApi().create(ApiInterface.class).updatePart(partDataModel).enqueue(new Callback<PartDataModel>() {
                        @Override
                        public void onResponse(Call<PartDataModel> call, Response<PartDataModel> response) { }

                        @Override
                        public void onFailure(Call<PartDataModel> call, Throwable t) { }
                    });
                    notifyDataSetChanged();
                }
            });
            holder.btnName.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle(R.string.potato_house)
                            .setMessage(String.format("WHAT WOULD YOU LIKE TO DO FOR %s?", full_name))
                            .setNegativeButton("CALL", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                        Intent intentCall = new Intent();
                                        intentCall.setAction(Intent.ACTION_CALL);
                                        intentCall.setData(Uri.parse("tel:" + partDataModel.getC_phone()));
                                        context.startActivity(intentCall);
                                    }
                                }
                            })
                            .setPositiveButton("SEND ALERT", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                                        SmsManager smsManager = SmsManager.getDefault();
                                        smsManager.sendTextMessage(partDataModel.getC_phone(), null,
                                                String.format("HI, %s!\t%s", partDataModel.getC_first(), R.string.the_workout_is_about_to_begin), null, null);
                                    }
                                }
                            })
                            .setNeutralButton("NOTHING", null)
                            .create();
                    dialog.show();
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(partDataModelList != null)
            return partDataModelList.size();
        else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ToggleButton btnName;

        public MyViewHolder(View itemView) {
            super(itemView);
            btnName=itemView.findViewById(R.id.attenditem_btncheck);
        }
    }
}