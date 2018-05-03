package com.example.juh.poikeeper.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

@Table(name = "poi_task")
public final class PoiTask extends Model {

    //////////////////////////////////////////////////////////////////////////////////////
    // MODEL PROPERTIES
    //////////////////////////////////////////////////////////////////////////////////////

    @Column(name = "name")
    public String name;

    @Column(name = "description")
    public String description;

    @Column(name = "title")
    public String title;

    @Column(name = "date")
    public Date date;

    /// RELATIONSHIP
    @Column(name = "point_of_interest", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public PointOfInterest pointOfInterest;

    public List<PoiTaskItem> items;

    //////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    //////////////////////////////////////////////////////////////////////////////////////

    public PoiTask() {
        super();
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // ACCESSORS
    //////////////////////////////////////////////////////////////////////////////////////

    public List<PoiTaskItem> getItems() {
        return getMany(PoiTaskItem.class, "poi_task");
    }

    public static PoiTask getById(int id) {
        return (new Select()).from(PoiTask.class)
                .where("Id = ?", id)
                .executeSingle();
    }
}
