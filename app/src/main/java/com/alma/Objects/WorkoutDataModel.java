package com.alma.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkoutDataModel {
    @SerializedName("id")
    @Expose
    private int id;

    private int max_trainers, min_age, length_min, many_times;
    private String title, difficulty, added_date;
    private Boolean is_active;
    private boolean is_clicked;

    public WorkoutDataModel(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMax_trainers() {
        return max_trainers;
    }

    public void setMax_trainers(int max_trainers) {
        this.max_trainers = max_trainers;
    }

    public int getMin_age() {
        return min_age;
    }

    public void setMin_age(int min_age) {
        this.min_age = min_age;
    }

    public int getLength_min() {
        return length_min;
    }

    public void setLength_min(int length_min) {
        this.length_min = length_min;
    }

    public int getMany_times() {
        return many_times;
    }

    public void setMany_times(int many_times) {
        this.many_times = many_times;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getAdded_date() {
        return added_date;
    }

    public void setAdded_date(String added_date) {
        this.added_date = added_date;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public boolean isIs_clicked() {
        return is_clicked;
    }

    public void setIs_clicked(boolean is_clicked) {
        this.is_clicked = is_clicked;
    }
}
