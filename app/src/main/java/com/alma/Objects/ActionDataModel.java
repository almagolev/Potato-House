package com.alma.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ActionDataModel implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;

    private int sub_do_id,sub_on_id;
    private String sub_do, sub_on, type, info, date;

    public ActionDataModel(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSub_do_id() {
        return sub_do_id;
    }

    public void setSub_do_id(int sub_do_id) {
        this.sub_do_id = sub_do_id;
    }

    public int getSub_on_id() {
        return sub_on_id;
    }

    public void setSub_on_id(int sub_on_id) {
        this.sub_on_id = sub_on_id;
    }

    public String getSub_do() {
        return sub_do;
    }

    public void setSub_do(String sub_do) {
        this.sub_do = sub_do;
    }

    public String getSub_on() {
        return sub_on;
    }

    public void setSub_on(String sub_on) {
        this.sub_on = sub_on;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}