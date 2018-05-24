package com.example.juh.poikeeper.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.Date;
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

    public List<PoiTask> getTasks() {
        return getMany(PoiTask.class, "point_of_interest");
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // STATIC HELPERS
    //////////////////////////////////////////////////////////////////////////////////////

    public static PointOfInterest getById(long id) {
        return (new Select()).from(PointOfInterest.class)
                .where("Id = ?", id)
                .executeSingle();
    }

    public static boolean isEmpty() {
        int entries = (new Select()).from(PointOfInterest.class).count();
        return entries <= 0;
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // FAKING DATA
    //////////////////////////////////////////////////////////////////////////////////////

    public static void FakeDateInit() {
        PointOfInterest[] pois = new PointOfInterest[4];
        String[] titles = {
                "Meriadeck Pôle Emplois",
                "Meriadeck Auchan Sud",
                "Chartron Ecole Osteo",
                "Le Lac Stade"
        };
        String[] descs = {
          "Spot mental avec de gros saut en hauteurs",
          "Saut de bras avec une suite de saut de prec'",
          "Escalade urbaine avec un toit hors-norme",
          "Longue piste avec beaucoup de vault"
        };
        LatLng[] latLng = {
                new LatLng(44.83819,-0.5896532),
                new LatLng(44.83838,-0.585887),
                new LatLng(44.85521,-0.5692514),
                new LatLng(44.898838,-0.565737)
        };

        for (int i = 0; i < pois.length; i++) {
            pois[i] = new PointOfInterest(titles[i], descs[i], null, latLng[i]);
            pois[i].save();
            fakeTask(pois[i]);
        }
    }

    static String[] titles = {
            "Saut de chat",
            "Passe muraille",
            "Saut de précision",
            "Speed vault"
    };

    static String[] names = new String[]{
            "Muret blanc",
            "Mur du batiment gris",
            "Barre pour piétons",
            "barrière à vélos"
    };

    static String[] descs = {
            "Prendre de l'élan à partir du banc pour effectuer le saut de chat sur le muret en face",
            "Continuer sa course vers le mur du bar tabac gris, et effectuer un passe muraille sur 3m10",
            "Courir vers le fond du toit et sauter en précision sur la barrière pietonne",
            "Effectuer le dernier mouvement, un speed vault pour revenir sur le trottoir"
    };

    private static void fakeTask(PointOfInterest poi) {
        for (int i = 0; i < titles.length; i++) {
            PoiTask task = new PoiTask();

            task.checked = false;
            task.title = titles[i];
            task.name = names[i];
            task.description = descs[i];
            task.date = new Date();
            task.pointOfInterest = poi;

            task.save();
        }
    }
}
