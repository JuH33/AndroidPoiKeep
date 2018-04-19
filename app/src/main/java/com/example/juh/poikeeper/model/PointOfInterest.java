package com.example.juh.poikeeper.model;

import android.support.annotation.NonNull;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.List;

@Table(name = "point_of_interest")
public final class PointOfInterest extends Model {

    //////////////////////////////////////////////////////////////////////////////////////
    // MODEL PROPERTIES
    //////////////////////////////////////////////////////////////////////////////////////

    @Column(name = "name")
    public String name;

    @Column(name = "description")
    public String description;

    @Column(name = "lat")
    @NonNull
    public double lat;

    @Column(name = "lng")
    @NonNull
    public double lng;

    /// RELATIONSHIP

    public List<PoiTask> tasks;

    //////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    //////////////////////////////////////////////////////////////////////////////////////

    public PointOfInterest() {
        super();
    }

    public PointOfInterest(String name, String desc, double lat, double lng) {
        super();

        this.name = name;
        this.lat = lat;
        this.lng = lng;
        description = desc;
    }

    public PointOfInterest(String name, String desc, LatLng latLng) {
        super();

        this.name = name;
        description = desc;
        lat = latLng.getLatitude();
        lng = latLng.getLongitude();
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // ACCESSORS
    //////////////////////////////////////////////////////////////////////////////////////

    public LatLng getLatLng() {
        return new LatLng(lat, lng);
    }
}
