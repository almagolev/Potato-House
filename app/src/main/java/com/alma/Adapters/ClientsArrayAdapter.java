package com.alma.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alma.Objects.ClientDataModel;
import com.alma.R;

import java.util.List;

public class ClientsArrayAdapter extends ArrayAdapter<ClientDataModel> {

    Context context;
    List<ClientDataModel> clientDataModelList;

    public ClientsArrayAdapter(Context context, List<ClientDataModel> clientDataModelList) {
        super(context, 0, clientDataModelList);
        this.context = context;
        this.clientDataModelList = clientDataModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.client_dialog_item, parent, false);
        }
        TextView labelTitle = convertView.findViewById(R.id.cid_label_title);
        TextView labelPhone = convertView.findViewById(R.id.cid_label_phone);

        ClientDataModel clientDataModel = getItem(position);
        labelTitle.setText(clientDataModel.getFirst_name()+" "+clientDataModel.getLast_name());
        labelPhone.setText(clientDataModel.getPhone_number());
        return convertView;
    }
}