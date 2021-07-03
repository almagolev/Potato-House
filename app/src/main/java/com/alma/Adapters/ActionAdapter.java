package com.alma.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.alma.Objects.ActionDataModel;
import com.alma.R;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class ActionAdapter extends RecyclerView.Adapter<ActionAdapter.MyViewHolder> {

    Context context;
    List<ActionDataModel> actionDataModelList;

    public ActionAdapter(Context context, List<ActionDataModel> actionDataModelList) {
        this.context = context;
        this.actionDataModelList = actionDataModelList;
    }

    public void setActionDataModelList(List<ActionDataModel> actionDataModelList) {
        this.actionDataModelList = actionDataModelList;
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.action_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull final MyViewHolder holder, final int position) {
        final ActionDataModel actionDataModel = actionDataModelList.get(position);
        if (actionDataModel != null) {
            holder.ai_name.setText(actionDataModel.getSub_do()+ " ID: "+actionDataModel.getSub_do_id());
            holder.ai_info.setText(actionDataModel.getInfo());
            holder.ai_date.setText(actionDataModel.getDate());
        }
    }

    @Override
    public int getItemCount() {
        if(actionDataModelList != null)
            return actionDataModelList.size();
        else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ai_info,ai_date,ai_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            ai_name = itemView.findViewById(R.id.ai_name);
            ai_info = itemView.findViewById(R.id.ai_info);
            ai_date = itemView.findViewById(R.id.ai_date);
        }
    }
}