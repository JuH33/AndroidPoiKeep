package com.example.juh.poikeeper.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.List;

@Table(name = "point_of_interest")
public final class PointOfInterest extends Model {

    //////////////////////////////////////////////////////////////////////////////////////
    // DATA SHARING CONSTANTS
    //////////////////////////////////////////////////////////////////////////////////////

    public enum PoiKeys {

        POI_ID("poi_id");

        private String keyName = "default_key_name";

        private PoiKeys(String keyName) {
            this.keyName = keyName;
        }

        @Override
        public String toString() {
            return keyName;
        }
    }

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

    @Column(name = "image_url")
    public String imageUrl;

    /// RELATIONSHIP

    public List<PoiTask> tasks;

    //////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    //////////////////////////////////////////////////////////////////////////////////////

    public PointOfInterest() {
        super();
    }

    public PointOfInterest(String name, String desc, @Nullable String imageUrl, double lat,
                           double lng) {
        super();

        this.name = name;
        this.imageUrl = imageUrl;
        this.lat = lat;
        this.lng = lng;
        description = desc;
    }

    public PointOfInterest(String name, String desc, @Nullable String imageUrl, LatLng latLng) {
        super();

        this.name = name;
        this.imageUrl = imageUrl;
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

    //////////////////////////////////////////////////////////////////////////////////////
    // STATIC HELPERS
    //////////////////////////////////////////////////////////////////////////////////////

    public static PointOfInterest getById(long id) {
        return (new Select()).from(PointOfInterest.class)
                .where("Id = ?", id)
                .executeSingle();
    }
}
