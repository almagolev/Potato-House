package com.alma.Adapters;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.alma.Activities.OptionsActivity;
import com.alma.Assists.Api;
import com.alma.Assists.ApiInterface;
import com.alma.Objects.ClientDataModel;
import com.alma.R;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BirthdaysAdapter extends RecyclerView.Adapter<BirthdaysAdapter.MyViewHolder> {

    Context context;
    List<ClientDataModel> clientDataModelList;
    OptionsActivity optionsActivity;

    public BirthdaysAdapter(Context context, List<ClientDataModel> clientDataModelList, OptionsActivity optionsActivity) {
        this.context = context;
        this.clientDataModelList = clientDataModelList;
        this.optionsActivity=optionsActivity;
    }

    public void setClientDataModelList(List<ClientDataModel> clientDataModelList) {
        this.clientDataModelList = clientDataModelList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_birthday_item,parent,false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ClientDataModel clientDataModel = clientDataModelList.get(position);
        final String today = LocalDate.now().toString();
        if (clientDataModel != null) {
            if(clientDataModel.getGender().contains("M"))
                holder.imageGender.setColorFilter(Color.BLUE);
            else
                holder.imageGender.setColorFilter(Color.MAGENTA);

            holder.labelName.setText(clientDataModel.getFirst_name() + " " + clientDataModel.getLast_name());
            if (clientDataModel.getLast_b_wish().equals(today)) {
                holder.btnSms.setColorFilter(Color.BLACK);
                holder.btnSms.setClickable(false);
            } else {
                holder.btnSms.setColorFilter(Color.GREEN);
                holder.btnSms.setClickable(true);
                holder.btnSms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ( ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(optionsActivity, Manifest.permission.SEND_SMS))
                                System.out.println("isSms = false;");
                            ActivityCompat.requestPermissions(optionsActivity, new String[]{Manifest.permission.SEND_SMS}, 1);
                        } else{
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(clientDataModel.getPhone_number(), null,
                                String.format("%s,\tPOTATO HOUSE STUDIO WISHES YOU A VERY HAPPY BIRTHDAY!", clientDataModel.getFirst_name()), null, null);
                        clientDataModel.setLast_b_wish(today);
                        Api.getApi().create(ApiInterface.class).updateBirthdayWish(clientDataModel).enqueue(new Callback<ClientDataModel>() {
                            @Override
                            public void onResponse(@NotNull Call<ClientDataModel> call, @NotNull Response<ClientDataModel> response) {
                                notifyItemChanged(position);
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(@NotNull Call<ClientDataModel> call, @NotNull Throwable t) {
                                notifyDataSetChanged();
                            }
                        });
                        holder.btnSms.setClickable(false);
                        holder.btnSms.setColorFilter(Color.BLACK);
                        notifyDataSetChanged();
                    }
                }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if(clientDataModelList != null)
        return clientDataModelList.size();
        else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView labelName;
        ImageView btnSms, imageGender;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageGender=itemView.findViewById(R.id.cbdi_image);
            labelName = itemView.findViewById(R.id.cbdi_label_name);
            btnSms = itemView.findViewById(R.id.cbdi_btn_send);
        }
    }
}