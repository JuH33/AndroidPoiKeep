package com.example.juh.poikeeper.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

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

    /// RELATIONSHIP

    public PointOfInterest pointOfInterest;

    public List<PoiTaskItem> items;

    //////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    //////////////////////////////////////////////////////////////////////////////////////

    public PoiTask() {
        super();
    }
}
