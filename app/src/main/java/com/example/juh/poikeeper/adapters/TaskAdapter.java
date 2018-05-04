package com.example.juh.poikeeper.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.juh.poikeeper.R;
import com.example.juh.poikeeper.model.PoiTask;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    private List<PoiTask> mList;
    private OnTaskInteraction mlistener;

    public TaskAdapter(List<PoiTask> list, @NonNull OnTaskInteraction listener) {
        super();
        mList = list;
        mlistener = listener;
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
        final long id = task.getId();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm",
                new Locale("FR-fr"));
        String date = format.format(task.date);

        holder.getDate().setText(date);
        holder.getDescription().setText(task.description);
        holder.getName().setText(task.name);
        holder.getTitle().setText(task.title);
        holder.getChecked().setChecked(task.checked);
        holder.getChecked().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mlistener.onCheckedClick(id, isChecked);
            }
        });
        holder.setOnLongClickListener(mlistener, id, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addTask(@NonNull PoiTask task) {
        int position = mList.size();
        mList.add(task);
        notifyItemInserted(position);
    }

    public void removeTask(@NonNull int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }
    //////////////////////////////////////////////////////////////////
    /// INNER CLASS HOLDER
    //////////////////////////////////////////////////////////////////

    public static class TaskHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView name;
        private TextView description;
        private TextView Date;
        private CheckBox Checked;

        public TaskHolder(View itemView) {
            super(itemView);

            setTitle((TextView) itemView.findViewById(R.id.task_item_title));
            setName((TextView) itemView.findViewById(R.id.task_item_name));
            setDate((TextView) itemView.findViewById(R.id.task_item_date));
            setDescription((TextView) itemView.findViewById(R.id.task_item_desc));
            setChecked((CheckBox) itemView.findViewById(R.id.task_item_checked));
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

        public CheckBox getChecked() {
            return Checked;
        }

        public void setChecked(CheckBox checked) {
            Checked = checked;
        }

        public void setOnLongClickListener(final OnTaskInteraction interaction,
                                           final long taskId,
                                           final int position) {
            final Button deleteBtn = itemView.findViewById(R.id.task_item_deleted_button);
            final Button cancelBtn = itemView.findViewById(R.id.task_item_cancel);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteBtn.setVisibility(View.GONE);
                    cancelBtn.setVisibility(View.GONE);
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interaction.deleteOnLongClick(taskId, position);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    deleteBtn.setVisibility(View.VISIBLE);
                    cancelBtn.setVisibility(View.VISIBLE);
                    return true;
                }
            });
        }
    }

    //////////////////////////////////////////////////////////////////
    /// INNER INTERFACE
    //////////////////////////////////////////////////////////////////

    public interface OnTaskInteraction {
        void onCheckedClick(long taskId, boolean checked);
        void deleteOnLongClick(long taskId, int position);
    }
}
