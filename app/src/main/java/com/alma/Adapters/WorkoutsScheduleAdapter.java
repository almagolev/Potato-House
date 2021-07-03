package com.alma.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.alma.Fragments.ScheduleAddFragment;
import com.alma.Objects.WorkoutDataModel;
import com.alma.R;
import java.util.List;

public class WorkoutsScheduleAdapter extends RecyclerView.Adapter<WorkoutsScheduleAdapter.MyViewHolder> {

    Context context;
    List<WorkoutDataModel> workoutDataModelList;
    ScheduleAddFragment scheduleAddFragment;
    Drawable back;

    public WorkoutsScheduleAdapter(Context context, List<WorkoutDataModel> workoutDataModelList, ScheduleAddFragment scheduleAddFragment) {
        this.context = context;
        this.workoutDataModelList = workoutDataModelList;
        this.scheduleAddFragment =scheduleAddFragment;
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
            holder.labelTime.setText(workoutDataModel.getLength_min() + " minutes");
            holder.labelMax.setText("Max "+workoutDataModel.getMax_trainers());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < workoutDataModelList.size(); i++) {
                        workoutDataModelList.get(i).setIs_clicked(false);
                    }
                    workoutDataModel.setIs_clicked(true);
                    notifyDataSetChanged();
                }
            });
            if (workoutDataModel.isIs_clicked()) {
                back = holder.itemView.getBackground();
                holder.itemView.setBackgroundResource(R.drawable.clear_btn);
            } else {
                holder.itemView.setBackground(back);
            }
        }
    }

    @Override
    public int getItemCount() {
        if(workoutDataModelList != null)
            return workoutDataModelList.size();
        else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView labelTitle,labelTime,labelMax;

        public MyViewHolder(View itemView) {
            super(itemView);
            labelTitle = itemView.findViewById(R.id.wi_label_title);
            labelTime = itemView.findViewById(R.id.wi_label_length);
            labelMax= itemView.findViewById(R.id.wi_label_max);
        }
    }
}