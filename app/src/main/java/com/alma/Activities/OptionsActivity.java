package com.alma.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import com.alma.Assists.Api;
import com.alma.Assists.ApiInterface;
import com.alma.Adapters.BirthdaysAdapter;
import com.alma.Objects.ClientDataModel;
import com.alma.R;
import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.view.View.GONE;

public class OptionsActivity extends AppCompatActivity implements View.OnClickListener {

    private List<ClientDataModel> clientDataModelList;
    private BirthdaysAdapter adapter;
    private AlertDialog alertDialog;
    private String today="";
    private View view;
    private TextView labelEmpty;
    private TableRow bdi_layout_send;
    private ImageView btnSend;


    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        findViewById(R.id.btnClients).setOnClickListener(this);
        findViewById(R.id.btnSchedule).setOnClickListener(this);
        findViewById(R.id.btnWorkouts).setOnClickListener(this);
        findViewById(R.id.btnBirthday).setOnClickListener(this);
        findViewById(R.id.btnHistory).setOnClickListener(this);
        findViewById(R.id.btnAttendance).setOnClickListener(this);
        findViewById(R.id.btnEquipment).setOnClickListener(this);
        findViewById(R.id.btnIdeas).setOnClickListener(this);
        findViewById(R.id.btnStatistics).setOnClickListener(this);

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        alertDialog = builder.create();
        OptionsActivity optionsActivity = this;
        clientDataModelList=null;
        LinearLayoutManager layoutManager = new LinearLayoutManager(OptionsActivity.this);
        view = LayoutInflater.from(OptionsActivity.this).inflate(R.layout.birthday_dialog_item, null);
        labelEmpty = view.findViewById(R.id.bdi_labelEmpty);
        bdi_layout_send = view.findViewById(R.id.bdi_layout_send);
        btnSend=view.findViewById(R.id.bdi_btn_send);
        btnSend.setColorFilter(Color.GREEN);
        RecyclerView recyclerView = view.findViewById(R.id.bdi_recycler);
        adapter = new BirthdaysAdapter(OptionsActivity.this, clientDataModelList, optionsActivity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(OptionsActivity.this, DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent open;
        switch (view.getId()) {
            case R.id.btnClients:
                open = new Intent(this, ClientsActivity.class);
                startActivity(open);
                break;
            case R.id.btnSchedule:
                open = new Intent(this, ScheduleActivity.class);
                startActivity(open);
                break;
            case R.id.btnWorkouts:
                open = new Intent(this, WorkoutsActivity.class);
                startActivity(open);
                break;
            case R.id.btnHistory:
                open = new Intent(this, ActionActivity.class);
                startActivity(open);
                break;
            case R.id.btnBirthday:
                doBirthday();
                break;
            case R.id.btnAttendance:
                open=new Intent(this,AttendanceActivity.class);
                startActivity(open);
                break;
            case R.id.btnEquipment:
            case R.id.btnIdeas:
            case R.id.btnStatistics:
                View viewDialog = LayoutInflater.from(this).inflate(R.layout.soon_to_be_dialog_item, null);
                android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this)
                        .setTitle(R.string.potato_house)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) { }})
                        .setView(viewDialog)
                        .create();
                dialog.show();
                break;
        }
    }

    public void doBirthday() {
        Calendar calendar = Calendar.getInstance();
        today = String.format("%d-%02d-%02d", calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH));
        Api.getApi().create(ApiInterface.class).getAllClientsBirthday(today).enqueue(new Callback<List<ClientDataModel>>() {
            @Override
            public void onResponse(Call<List<ClientDataModel>> call, Response<List<ClientDataModel>> response) {
                clientDataModelList = response.body();
                adapter.setClientDataModelList(clientDataModelList);
                if (clientDataModelList == null) {
                    labelEmpty.setVisibility(View.VISIBLE);
                    bdi_layout_send.setVisibility(GONE);
                } else {
                    bdi_layout_send.setVisibility(View.VISIBLE);
                    labelEmpty.setVisibility(GONE);
                }
            }

            @Override
            public void onFailure(Call<List<ClientDataModel>> call, Throwable t) { }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( ContextCompat.checkSelfPermission(OptionsActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(OptionsActivity.this, Manifest.permission.SEND_SMS))
                        System.out.println("isSms = false;");
                    ActivityCompat.requestPermissions(OptionsActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
                } else{
                    for (int j = 0; j < clientDataModelList.size(); j++) {
                        ClientDataModel clientDataModel = clientDataModelList.get(j);
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(clientDataModel.getPhone_number(), null,
                                String.format("%s,\tPOTATO HOUSE STUDIO WISHES YOU A VERY HAPPY BIRTHDAY!", clientDataModel.getFirst_name()), null, null);
                        clientDataModel.setLast_b_wish(today);
                        Api.getApi().create(ApiInterface.class).updateBirthdayWish(clientDataModel).enqueue(new Callback<ClientDataModel>() {
                            @Override
                            public void onResponse(Call<ClientDataModel> call, Response<ClientDataModel> response) { }
                            @Override
                            public void onFailure(Call<ClientDataModel> call, Throwable t) { }
                        });
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        alertDialog.setTitle(getString(R.string.potato_house) + " - " + getString(R.string.today_s_bithdays));
        alertDialog.setView(view);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "DONE", (Message) null);
        alertDialog.create();
        alertDialog.show();
    }
}