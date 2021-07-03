package com.alma.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.alma.Assists.Api;
import com.alma.Assists.ApiInterface;
import com.alma.Objects.PartDataModel;
import com.alma.R;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.MyViewHolder> {

    Context context;
    List<PartDataModel> partDataModelList;
    ApiInterface apiService;
    ScheduleAdapter scheduleAdapter;

    public RegisterAdapter(Context context, List<PartDataModel> partDataModelList, ScheduleAdapter scheduleAdapter) {
        this.context = context;
        this.partDataModelList = partDataModelList;
        this.apiService = Api.getApi().create(ApiInterface.class);
        this.scheduleAdapter=scheduleAdapter;
    }

    public void setPartDataModelList(List<PartDataModel> partDataModelList) {
        this.partDataModelList = partDataModelList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.registered_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final PartDataModel partDataModel = partDataModelList.get(position);
        if (partDataModel != null) {
            holder.imageClient.setColorFilter(R.color.colorPrimary);
            if(partDataModel.getC_gender().contains("M"))
                holder.imageClient.setColorFilter(Color.BLUE);
            else
                holder.imageClient.setColorFilter(Color.MAGENTA);
            holder.ri_name.setText(partDataModel.getC_first()+" "+partDataModel.getC_last());
            holder.ri_name.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final AlertDialog whatsNext = new AlertDialog.Builder(context).create();
                    whatsNext.setTitle(R.string.potato_house);
                    whatsNext.setMessage(String.format("DO YOU WANT TO DELETE %s FROM WORKOUT?",holder.ri_name.getText()));
                    whatsNext.setButton(Dialog.BUTTON_NEGATIVE,"YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Api.getApi().create(ApiInterface.class).deletePartById(partDataModel.getId()).enqueue(new Callback() {
                                @Override
                                public void onResponse(Call call, Response response) {
                                    scheduleAdapter.notifyDataSetChanged();
                                    scheduleAdapter.scheduleAllFragment.onResume();
                                }
                                @Override
                                public void onFailure(Call call, Throwable t) {
                                    scheduleAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });
                    whatsNext.setButton(Dialog.BUTTON_POSITIVE, "NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) { }});
                    whatsNext.show();
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
        TextView ri_name;
        ImageView imageClient;

        public MyViewHolder(View itemView) {
            super(itemView);
            ri_name = itemView.findViewById(R.id.ri_name);
            imageClient = itemView.findViewById(R.id.ri_imageClient);
        }
    }
}