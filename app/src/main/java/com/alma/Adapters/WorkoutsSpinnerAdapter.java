package com.alma.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.alma.Objects.WorkoutDataModel;
import com.alma.R;
import java.util.List;

public class WorkoutsSpinnerAdapter extends ArrayAdapter<WorkoutDataModel> {

    Context context;
    List<WorkoutDataModel> workoutDataModelList;

    public WorkoutsSpinnerAdapter(Context context, List<WorkoutDataModel> workoutDataModelList) {
        super(context, 0, workoutDataModelList);
        this.context = context;
        this.workoutDataModelList = workoutDataModelList;
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
                    R.layout.workout_item, parent, false);
        }
        TextView labelTitle = convertView.findViewById(R.id.wi_label_title);
        TextView labelTime = convertView.findViewById(R.id.wi_label_length);
        TextView labelMax = convertView.findViewById(R.id.wi_label_max);

        WorkoutDataModel workoutDataModel = getItem(position);
        labelTitle.setText(workoutDataModel.getTitle());
        labelTime.setText(workoutDataModel.getLength_min()+" minutes");
        labelMax.setText("Max "+workoutDataModel.getMax_trainers());

        return convertView;
    }
}