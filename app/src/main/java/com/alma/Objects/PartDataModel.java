package com.alma.Objects;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalTime;

public class PartDataModel {
    @SerializedName("id")
    @Expose
    private int id;

    private int c_id,w_id,s_id;
    private String c_came,w_title,w_date,w_start,w_end,c_phone,c_gender,c_first,c_last;

    public PartDataModel(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getW_id() {
        return w_id;
    }

    public void setW_id(int w_id) {
        this.w_id = w_id;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public String getC_came() {
        return c_came;
    }

    public void setC_came(String c_came) {
        this.c_came = c_came;
    }

    public String getW_title() {
        return w_title;
    }

    public void setW_title(String w_title) {
        this.w_title = w_title;
    }

    public String getW_date() {
        return w_date;
    }

    public void setW_date(String w_date) {
        this.w_date = w_date;
    }

    public String getW_start() {
        return w_start;
    }

    public void setW_start(String w_start) {
        this.w_start = w_start;
    }

    public String getW_end() {
        return w_end;
    }

    public void setW_end(String w_end) {
        this.w_end = w_end;
    }

    public String getC_phone() {
        return c_phone;
    }

    public void setC_phone(String c_phone) {
        this.c_phone = c_phone;
    }

    public String getC_gender() {
        return c_gender;
    }

    public void setC_gender(String c_gender) {
        this.c_gender = c_gender;
    }

    public String getC_first() {
        return c_first;
    }

    public void setC_first(String c_first) {
        this.c_first = c_first;
    }

    public String getC_last() {
        return c_last;
    }

    public void setC_last(String c_last) {
        this.c_last = c_last;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean notInRange() {
        return w_end.compareTo(String.valueOf(LocalTime.now()))<0 || w_start.compareTo(String.valueOf(LocalTime.now()))>0;
    }
}
