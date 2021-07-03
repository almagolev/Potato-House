package com.alma.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.alma.Objects.WorkoutDataModel;
import com.alma.R;
import java.util.List;

public class WorkoutsListAdapter extends RecyclerView.Adapter<WorkoutsListAdapter.MyViewHolder> {

    Context context;
    List<WorkoutDataModel> workoutDataModelList;

    public WorkoutsListAdapter(Context context, List<WorkoutDataModel> workoutDataModelList) {
        this.context = context;
        this.workoutDataModelList = workoutDataModelList;
    }

    public void setWorkoutDataModelList(List<WorkoutDataModel> workoutDataModelList) {
        this.workoutDataModelList = workoutDataModelList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.workout_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final WorkoutDataModel workoutDataModel = workoutDataModelList.get(position);
        if (workoutDataModel != null) {
            holder.labelTitle.setText(workoutDataModel.getTitle());
            holder.labelTime.setText(workoutDataModel.getLength_min()+" minutes");
            holder.labelMax.setText("Max "+workoutDataModel.getMax_trainers());
        }
    }

    @Override
    public int getItemCount() {
        if(workoutDataModelList != null)
            return workoutDataModelList.size();
        else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView labelTitle,labelTime,labelMax,min;

        public MyViewHolder(View itemView) {
            super(itemView);
            labelTitle = itemView.findViewById(R.id.wi_label_title);
            labelTime = itemView.findViewById(R.id.wi_label_length);
            labelMax = itemView.findViewById(R.id.wi_label_max);
        }
    }
}