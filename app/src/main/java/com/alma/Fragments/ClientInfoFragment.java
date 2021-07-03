package com.alma.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.alma.Activities.ClientsActivity;
import com.alma.Assists.Api;
import com.alma.Assists.ApiInterface;
import com.alma.Objects.ClientDataModel;
import com.alma.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientInfoFragment extends Fragment implements View.OnClickListener {

    private TextView txt_first,txt_last,txt_num,txt_address,txt_email,txt_birthday,txt_id;
    private ClientDataModel clientDataModel;
    private ToggleButton toggleBtnGender;
    private ClientsActivity clientsActivity;

    public ClientInfoFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_info, container, false);

        txt_id = view.findViewById(R.id.txt_id);
        txt_first = view.findViewById(R.id.txt_firstName);
        txt_last = view.findViewById(R.id.txt_lastName);
        txt_num = view.findViewById(R.id.txt_phoneNumber);
        txt_address = view.findViewById(R.id.txt_homeAddress);
        txt_email = view.findViewById(R.id.txt_email);
        txt_birthday = view.findViewById(R.id.txt_birthDate);
        toggleBtnGender = view.findViewById(R.id.toggleBtnGender);
        toggleBtnGender.setOnClickListener(this);

        view.findViewById(R.id.btn_saveClient).setOnClickListener(this);
        view.findViewById(R.id.btn_cancelClient).setOnClickListener(this);

        clientDataModel = new ClientDataModel();
        clientsActivity= (ClientsActivity) getActivity();

        return view;
    }

    private void doSave() {
        clientDataModel.setFirst_name(txt_first.getText().toString());
        clientDataModel.setLast_name(txt_last.getText().toString());
        clientDataModel.setEmail(txt_email.getText().toString());
        if(toggleBtnGender.isChecked())
            clientDataModel.setGender("F");
        else
            clientDataModel.setGender("M");
        clientDataModel.setHome_address(txt_address.getText().toString());
        clientDataModel.setPhone_number(txt_num.getText().toString());
        clientDataModel.setId_client(txt_id.getText().toString());
        clientDataModel.setBirthday(txt_birthday.getText().toString().replace('/','-'));
        clientDataModel.setIs_active(true);
        clientDataModel.setHas_health(true);
        Api.getApi().create(ApiInterface.class).createClient(clientDataModel).enqueue(new Callback<ClientDataModel>() {
            @Override
            public void onResponse(Call<ClientDataModel> call, Response<ClientDataModel> response) {
                if (response.isSuccessful()) {
                    new AlertDialog.Builder(getContext()).setTitle("POTATO HOUSE").setMessage("ADDED!").setPositiveButton("OK", null).create().show();
                    clientsActivity.changeTab(0);
                }
                else{
                    new AlertDialog.Builder(getContext()).setTitle("POTATO HOUSE").setMessage("ERROR! TRY AGAIN").setPositiveButton("OK", null).create().show();
                    clientsActivity.changeTab(1);
                }
            }

            @Override
            public void onFailure(Call<ClientDataModel> call, Throwable t) {
                new AlertDialog.Builder(getContext()).setTitle("POTATO HOUSE").setMessage("ERROR! TRY AGAIN").setPositiveButton("OK", null).create().show();
                clientsActivity.changeTab(1);
            }
        });
    }

    @Override
    public void onClick(View view) {
        hideKey();
        switch (view.getId()){
            case R.id.btn_saveClient:
                check();
                break;
            case R.id.btn_cancelClient:
                clientsActivity.changeTab(0);
                break;
        }
    }

    private void check(){
        String errors=" ";
        if(!checkName(txt_first.getText().toString()))
            errors+="\nFirst name invalid!";
        if(!checkName(txt_last.getText().toString()))
            errors+="\nLast name invalid!";
        if(checkNum(txt_num.getText().toString(),10))
            errors+="\nPhone number must be 10 digits!";
        if(checkNum(txt_id.getText().toString(),9))
            errors+="\nId number must be 9 digits!";
        if(checkEmail(txt_email.getText().toString()))
            errors+="\nEmail invalid!";
        if(txt_address.getText().length()<2)
            errors+="\nAddress invalid!";
        if(!checkDate(txt_birthday.getText().toString().replace('/','-')))
            errors+="\nBirthday invalid!";

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+errors);
        if(errors.length()>1)
            new AlertDialog.Builder(getContext()).setTitle("POTATO HOUSE").setMessage(errors).setPositiveButton("OK", null).create().show();
        else
            doSave();
    }

    private boolean checkEmail(String email){
        return email.matches("[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]");
    }
    private boolean checkNum(String num,int len){
        return num.matches("[0-9]") && num.length()==len;
    }

    private boolean checkName(String name){
        return name.matches("[a-zA-Z]+") && name.length()>1;
    }

    private boolean checkDate(String dateStr) {
        DateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    private void hideKey(){
        try {
            InputMethodManager keyboard = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(this.getView().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}