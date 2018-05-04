package com.example.juh.poikeeper;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.example.juh.poikeeper.adapters.TaskAdapter;
import com.example.juh.poikeeper.model.PoiTask;
import com.example.juh.poikeeper.model.PointOfInterest;
import com.example.juh.poikeeper.utils.BasePoiAlert;
import com.example.juh.poikeeper.utils.PoiTaskAlert;

import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    private long mPoiId;
    private List<PoiTask> mTaskList;

    private FloatingActionButton mAddTask;

    private TaskAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPoiId = getIntent().getExtras().getLong("poi_id");
        mTaskList = new ArrayList<>();

        PointOfInterest point = PointOfInterest.getById(mPoiId);
        mTaskList = point.getTasks();

        mAdapter = new TaskAdapter(mTaskList, mListener);
        RecyclerView recyclerView = findViewById(R.id.tasks_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        mAddTask = findViewById(R.id.task_list_add_task_btn);
        mAddTask.setOnClickListener(addListnener);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    /////////////////////////////////////////////////////////////////
    /// Listeners
    /////////////////////////////////////////////////////////////////

    private View.OnClickListener addListnener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PoiTaskAlert.Builder builder = new PoiTaskAlert.Builder();
            builder.setPoiId(TaskListActivity.this.mPoiId)
                    .setViewId(R.layout.add_task_alert_view)
                    .setContext(TaskListActivity.this)
                    .setListener(new BasePoiAlert.IOnClick() {
                        @Override
                        public <T extends Model> void OnOkClickListenr(T pointOfInterest) {
                            PoiTask poiTask = (PoiTask) pointOfInterest;
                            mAdapter.addTask(poiTask);
                        }

                        @Override
                        public void OnCancelClickListener() { }
                    });
            builder.create();
        }
    };

    private TaskAdapter.OnTaskInteraction mListener = new TaskAdapter.OnTaskInteraction() {
        @Override
        public void onCheckedClick(long taskId, boolean checked) {
            PoiTask task = PoiTask.getById(taskId);
            if (task != null) {
                task.checked = checked;
                task.save();
                Toast.makeText(TaskListActivity.this,
                        "Item Updated",
                        Toast.LENGTH_LONG).show();
                Log.i("DATABASE", String.format("TASK N°%s HAS BEEN UPLOADED", task.getId().toString()));
            }
        }
    };
}