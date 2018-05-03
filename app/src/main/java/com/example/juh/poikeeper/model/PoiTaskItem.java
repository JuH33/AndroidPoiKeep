package com.example.juh.poikeeper.model;

import android.support.annotation.NonNull;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

@Table(name = "poi_task_item")
public final class PoiTaskItem extends Model {

    //////////////////////////////////////////////////////////////////////////////////////
    // MODEL PROPERTIES
    //////////////////////////////////////////////////////////////////////////////////////

    @Column(name = "tite")
    public String title;

    @Column(name = "description")
    public String description;

    @Column(name = "date", index = true)
    public Date date;

    @Column(name = "done", index = true)
    public boolean done;

    /// RELATIONS

    @Column(name = "poi_task", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public PoiTask poiTask;

    //////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    //////////////////////////////////////////////////////////////////////////////////////

    public PoiTaskItem() {
        super();
    }

    public PoiTaskItem(String title, String desc, Date date, boolean done) {
        super();
        this.title = title;
        this.description = desc;
        this.date = date;
        this.done = done;
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // ACCESSORS
    //////////////////////////////////////////////////////////////////////////////////////

    public boolean isDone() {
        return done;
    }

    public Date getDate() {
        return date;
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // SETTERS
    //////////////////////////////////////////////////////////////////////////////////////

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setDate(@NonNull Date date) {
        this.date = date;
    }
}
