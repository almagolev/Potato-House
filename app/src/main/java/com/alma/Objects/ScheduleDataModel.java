package com.alma.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleDataModel {
    @SerializedName("id")
    @Expose
    private int id;

    private int w_id, w_max, w_signed, w_length;
    private String w_title, w_date, w_start,w_end;

    public ScheduleDataModel(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getW_id() {
        return w_id;
    }

    public void setW_id(int w_id) {
        this.w_id = w_id;
    }

    public int getW_max() {
        return w_max;
    }

    public void setW_max(int w_max) {
        this.w_max = w_max;
    }

    public int getW_signed() {
        return w_signed;
    }

    public void setW_signed(int w_signed) {
        this.w_signed = w_signed;
    }

    public int getW_length() {
        return w_length;
    }

    public void setW_length(int w_length) {
        this.w_length = w_length;
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
}
