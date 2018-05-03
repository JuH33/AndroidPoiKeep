package com.example.juh.poikeeper.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.juh.poikeeper.R;
import com.example.juh.poikeeper.model.PoiTask;

import java.util.List;

public final class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    private List<PoiTask> mList;

    public TaskAdapter(List<PoiTask> list) {
        super();
        mList = list;
    }


    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        PoiTask task = mList.get(position);

        holder.getDate().setText(task.date.toString());
        holder.getDescription().setText(task.description);
        holder.getName().setText(task.name);
        holder.getTitle().setText(task.title);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //////////////////////////////////////////////////////////////////
    /// INNER CLASS HOLDER
    //////////////////////////////////////////////////////////////////

    public static class TaskHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView name;
        private TextView description;
        private TextView Date;

        public TaskHolder(View itemView) {
            super(itemView);

            setTitle((TextView) itemView.findViewById(R.id.task_item_title));
            setName((TextView) itemView.findViewById(R.id.task_item_name));
            setDate((TextView) itemView.findViewById(R.id.task_item_date));
            setDescription((TextView) itemView.findViewById(R.id.task_item_desc));
        }

        // =========================
        // GETTERS | SETTERS
        // =========================

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public TextView getDescription() {
            return description;
        }

        public void setDescription(TextView description) {
            this.description = description;
        }

        public TextView getDate() {
            return Date;
        }

        public void setDate(TextView date) {
            Date = date;
        }
    }
}
