package com.example.juh.poikeeper;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.activeandroid.query.Select;
import com.example.juh.poikeeper.adapters.TaskAdapter;
import com.example.juh.poikeeper.model.PoiTask;
import com.example.juh.poikeeper.model.PointOfInterest;

import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    private long mPoiId;
    private List<PoiTask> mTaskList;

    private FloatingActionButton mAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPoiId = getIntent().getExtras().getLong("poi_id");
        mTaskList = new ArrayList<>();

        PointOfInterest point = PointOfInterest.getById(mPoiId);
        mTaskList = point.getTasks();

        RecyclerView recyclerView = findViewById(R.id.tasks_recycler);
        recyclerView.setAdapter(new TaskAdapter(mTaskList));

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
            Log.e("IMPLEMENTATION", "Not implemented. ---");
        }
    };
}