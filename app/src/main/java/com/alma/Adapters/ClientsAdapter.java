package com.alma.Adapters;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alma.Assists.Api;
import com.alma.Assists.ApiInterface;
import com.alma.Fragments.ClientsAllFragment;
import com.alma.Objects.ClientDataModel;
import com.alma.R;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.MyViewHolder> {

    Context context;
    List<ClientDataModel> clientDataModelList;
    ClientsAllFragment clientsAllFragment;

    public ClientsAdapter(Context context, List<ClientDataModel> clientDataModelList, ClientsAllFragment clientsAllFragment) {
        this.context = context;
        this.clientDataModelList = clientDataModelList;
        this.clientsAllFragment = clientsAllFragment;

    }

    public void setClientDataModelList(List<ClientDataModel> clientDataModelList) {
        this.clientDataModelList = clientDataModelList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ClientDataModel clientDataModel = clientDataModelList.get(position);
        if (clientDataModel != null) {
            holder.labelSince.setText("Member since " + clientDataModel.getJoin_date());
            holder.labelName.setText(clientDataModel.getFirst_name() + " " + clientDataModel.getLast_name());
            holder.labelAddress.setText(clientDataModel.getHome_address());
            holder.labelId.setText("id: "+clientDataModel.getId_client());
            holder.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clientDataModel.setEditable(!clientDataModel.isEditable());
                    notifyDataSetChanged();
                }
            });
            holder.ci_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clientDataModel.setEditable(!clientDataModel.isEditable());
                    notifyDataSetChanged();
                }
            });

            holder.btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clientDataModel.setFirst_name(holder.txt_first.getText().toString());
                    clientDataModel.setLast_name(holder.txt_last.getText().toString());
                    clientDataModel.setIs_active(Boolean.valueOf(holder.btnActive.isChecked()));
                    clientDataModel.setEmail(holder.txt_email.getText().toString());
                    if(holder.btnGender.isChecked())
                        clientDataModel.setGender("F");
                    else
                        clientDataModel.setGender("M");
                    clientDataModel.setHome_address(holder.txt_address.getText().toString());
                    clientDataModel.setPhone_number(holder.txt_phone.getText().toString());

                    Api.getApi().create(ApiInterface.class).updateClient(clientDataModel.getId(), clientDataModel).enqueue(new Callback<ClientDataModel>() {
                        @Override
                        public void onResponse(Call<ClientDataModel> call, Response<ClientDataModel> response) { }
                        @Override
                        public void onFailure(Call<ClientDataModel> call, Throwable t) { }
                    });
                    clientDataModel.setEditable(false);
                    holder.txt_phone.setEnabled(false);
                    holder.txt_address.setEnabled(false);
                    holder.txt_email.setEnabled(false);
                    holder.txt_first.setEnabled(false);
                    holder.txt_last.setEnabled(false);
                    holder.btnGender.setEnabled(false);
                    holder.btnActive.setEnabled(false);
                    holder.btnHealth.setEnabled(false);

                    holder.btnSave.setVisibility(View.GONE);
                    holder.btnEdit.setVisibility(View.VISIBLE);
                    holder.layout_more.setVisibility(View.GONE);
                    holder.layout_first.setVisibility(View.VISIBLE);
                    holder.ci_layout_second.setVisibility(View.VISIBLE);
                    clientsAllFragment.onResume();
                }
            });

            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.txt_phone.setEnabled(true);
                    holder.txt_address.setEnabled(true);
                    holder.txt_email.setEnabled(true);
                    holder.txt_first.setEnabled(true);
                    holder.txt_last.setEnabled(true);
                    holder.btnGender.setEnabled(true);
                    holder.btnActive.setEnabled(true);
                    holder.btnHealth.setEnabled(true);

                    holder.btnSave.setVisibility(View.VISIBLE);
                    holder.btnEdit.setVisibility(View.GONE);
                }
            });

            if (clientDataModel.isEditable()) {
                holder.layout_more.setVisibility(View.VISIBLE);
                holder.layout_first.setVisibility(View.GONE);
                holder.ci_layout_second.setVisibility(View.GONE);
                holder.labelBirthday.setText(clientDataModel.getBirthday());
                holder.labelSigned.setText("W.Signed "+clientDataModel.getW_sign());
                holder.labelCanceled.setText("W.Canceled "+clientDataModel.getW_canceled());
                holder.labelDone.setText("W.Done "+clientDataModel.getW_done());
                holder.txt_first.setText(clientDataModel.getFirst_name());
                holder.txt_last.setText(clientDataModel.getLast_name());
                holder.txt_phone.setText(clientDataModel.getPhone_number());
                holder.txt_email.setText(clientDataModel.getEmail());
                holder.txt_address.setText(clientDataModel.getHome_address());
                holder.btnSave.setVisibility(View.GONE);
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnActive.setChecked(clientDataModel.isIs_active());
                if (clientDataModel.getGender().contains("F"))
                    holder.btnGender.setChecked(true);
                else
                    holder.btnGender.setChecked(false);
            } else {
                holder.layout_more.setVisibility(View.GONE);
                holder.layout_first.setVisibility(View.VISIBLE);
                holder.ci_layout_second.setVisibility(View.VISIBLE);
            }

            holder.btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("TITLE")
                            .setMessage("MESSAGE")
                            .setNeutralButton("CALL", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                        Intent intentCall = new Intent();
                                        intentCall.setAction(Intent.ACTION_CALL);
                                        intentCall.setData(Uri.parse("tel:" + clientDataModel.getPhone_number()));
                                        context.startActivity(intentCall); } }
                            })
                            .setNegativeButton("NO",null)
                            .create();
                    dialog.show();
                }});
        }
    }

    @Override
    public int getItemCount() {

        if(clientDataModelList != null)
        return clientDataModelList.size();
        else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout_first, layout_more,ci_layout, ci_layout_second;
        ToggleButton btnActive, btnGender, btnHealth;
        Button btnSave, btnEdit, btnCancel;
        TextView labelName, labelAddress, labelSince, labelId,txt_first,txt_last,txt_phone, txt_email,txt_address, labelBirthday,
        labelSigned,labelCanceled,labelDone;
        ImageView btnCall;

        public MyViewHolder(View itemView) {
            super(itemView);
            ci_layout = itemView.findViewById(R.id.ci_layout);
            ci_layout_second = itemView.findViewById(R.id.ci_layout_second);
            layout_first = itemView.findViewById(R.id.ci_layout_first);
            layout_more = itemView.findViewById(R.id.ci_layout_more);
            labelId = itemView.findViewById(R.id.ci_label_id);
            labelBirthday=itemView.findViewById(R.id.ci_label_birthday);
            btnCall = itemView.findViewById(R.id.ci_btn_call);
            btnSave = itemView.findViewById(R.id.ci_btnSave);
            btnEdit = itemView.findViewById(R.id.ci_btnEdit);
            btnActive = itemView.findViewById(R.id.ci_toggleBtnActive);
            btnGender = itemView.findViewById(R.id.ci_toggleBtnGender);
            btnHealth = itemView.findViewById(R.id.ci_toggleBtnHealth);
            btnCancel = itemView.findViewById(R.id.ci_btnCancel);

            labelName = itemView.findViewById(R.id.ci_label_name);
            labelAddress = itemView.findViewById(R.id.ci_label_address);
            labelSince =itemView.findViewById(R.id.ci_label_since);
            labelSigned=itemView.findViewById(R.id.ci_label_sign);
            labelCanceled=itemView.findViewById(R.id.ci_label_canceled);
            labelDone=itemView.findViewById(R.id.ci_label_w_done);

            txt_first = itemView.findViewById(R.id.ci_txt_first);
            txt_last = itemView.findViewById(R.id.ci_txt_last);
            txt_phone = itemView.findViewById(R.id.ci_txt_phone);
            txt_email = itemView.findViewById(R.id.ci_txt_email);
            txt_address = itemView.findViewById(R.id.ci_txt_address);

        }
    }
}