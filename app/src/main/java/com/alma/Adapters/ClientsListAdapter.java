package com.alma.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alma.Objects.ClientDataModel;
import com.alma.Objects.ClientDataModel;
import com.alma.R;

import java.util.List;

public class ClientsListAdapter extends RecyclerView.Adapter<ClientsListAdapter.MyViewHolder> {

    Context context;
    List<ClientDataModel> clientDataModelList;

    public ClientsListAdapter(Context context, List<ClientDataModel> clientDataModelList) {
        this.context = context;
        this.clientDataModelList = clientDataModelList;
    }

    public void setClientDataModelList(List<ClientDataModel> clientDataModelList) {
        this.clientDataModelList = clientDataModelList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_solid_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ClientDataModel clientDataModel = clientDataModelList.get(position);
        if (clientDataModel != null) {
            holder.labelName.setText(clientDataModel.getFirst_name() +" "+clientDataModel.getLast_name());
            if(clientDataModel.getGender().contains("M"))
                holder.img.setColorFilter(Color.BLUE);
            else
                holder.img.setColorFilter(Color.MAGENTA);
            holder.labelExtra.setText(clientDataModel.getHome_address());
        }
    }

    @Override
    public int getItemCount() {
        if(clientDataModelList != null)
            return clientDataModelList.size();
        else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView labelName, labelExtra;
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            labelName = itemView.findViewById(R.id.csi_name);
            labelExtra = itemView.findViewById(R.id.csi_extra);
            img = itemView.findViewById(R.id.csi_imageClient);
        }
    }
}