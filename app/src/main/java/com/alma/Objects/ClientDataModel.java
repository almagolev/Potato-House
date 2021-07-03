package com.alma.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ClientDataModel implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;

//    @SerializedName("id_client")
//    @Expose
//    String id_client;

    private int w_sign, w_canceled,w_done;
    private String id_client,first_name, last_name, phone_number,email, home_address,birthday, join_date, gender,last_b_wish;
    private boolean is_active, has_health, editable;


    public ClientDataModel(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public int getW_sign() {
        return w_sign;
    }

    public void setW_sign(int w_sign) {
        this.w_sign = w_sign;
    }

    public int getW_canceled() {
        return w_canceled;
    }

    public void setW_canceled(int w_canceled) {
        this.w_canceled = w_canceled;
    }

    public int getW_done() {
        return w_done;
    }

    public void setW_done(int w_done) {
        this.w_done = w_done;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHome_address() {
        return home_address;
    }

    public void setHome_address(String home_address) {
        this.home_address = home_address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getJoin_date() {
        return join_date;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLast_b_wish() {
        return last_b_wish;
    }

    public void setLast_b_wish(String last_b_wish) {
        this.last_b_wish = last_b_wish;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public boolean isHas_health() {
        return has_health;
    }

    public void setHas_health(boolean has_health) {
        this.has_health = has_health;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

//    public String toString(){
//        return String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t %d\t %d\t",
//                first_name,last_name,phone_number,email,home_address,is_active,has_health,gender, w_sign, w_canceled);
//    }
}