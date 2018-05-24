package fragments.map;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.activeandroid.Model;
import com.example.juh.poikeeper.R;
import com.example.juh.poikeeper.utils.BasePoiAlert;
import com.example.juh.poikeeper.utils.ConnectionWrapper;
import com.example.juh.poikeeper.utils.PoiAlertCreate;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.List;

public class MapFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_LAT = "latitude";

    private static final String ARG_LNG = "longitude";

    private static final double DEFAULT_LAT = 44.836151;

    private static final double DEFAULT_LNG = -0.580816;

    private boolean mEditMode;

    private LatLng latLng;

    private Marker lastMarker;

    private MapView mapView;

    private MapboxMap mMapboxMap;

    private OnFragmentInteractionListener mListener;

    public MapFragment() { }

    public static MapFragment newInstance(@NonNull LatLng latLng) {
        MapFragment fragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble(ARG_LAT, latLng.getLatitude());
        bundle.putDouble(ARG_LNG, latLng.getLongitude());
        fragment.setArguments(bundle);
        return fragment;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// ANDROID FRAGMENT LIFECYCLE
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            latLng = new LatLng();
            latLng.setLatitude(getArguments().getDouble(ARG_LAT));
            latLng.setLongitude(getArguments().getDouble(ARG_LNG));

            mEditMode = (getArguments().getBoolean("edit_mode"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view;
        if (ConnectionWrapper.hasConnexion(getContext())) {
            view = inflater.inflate(R.layout.fragment_map, container, false);
        } else {
            view = inflater.inflate(R.layout.no_map_fragment, container, false);
            return view;
        }

        /// SET ONCLICK LISTENERS
        Button zoomIn = view.findViewById(R.id.map_zoom_in);
        Button zoomOut = view.findViewById(R.id.map_zoom_out);
        zoomIn.setOnClickListener(this);
        zoomOut.setOnClickListener(this);

        /// INITIALIZE MAP VIEW
        mapView = view.findViewById(R.id.map_view_fragment);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;
                CameraPosition.Builder cam = new CameraPosition.Builder();
                cam.zoom(16);

                ImageButton goBtn = view.findViewById(R.id.map_go_btn);

                if (latLng != null) {
                    cam.target(latLng);
                    addMarker(latLng, mapboxMap);
                    mapboxMap.setCameraPosition(cam.build());

                    // Navigation
                    goBtn.setOnClickListener(goNavigate);
                } else {
                    cam.target(new LatLng(DEFAULT_LAT, DEFAULT_LNG));
                    mapboxMap.setCameraPosition(cam.build());
                }

                if (mEditMode) {
                    Toast.makeText(MapFragment.this.getContext(), "Tap somewhere to add a new point",
                            Toast.LENGTH_LONG).show();
                    mapboxMap.addOnMapClickListener(mapClickListener);
                    mapboxMap.setOnMarkerClickListener(markerClickListener);
                    goBtn.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mapView != null)
            mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null)
            mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null)
            mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mapView != null)
            mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null)
            mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (latLng != null) {
            outState.putDouble(ARG_LAT, latLng.getLatitude());
            outState.putDouble(ARG_LNG, latLng.getLongitude());
        }
        super.onSaveInstanceState(outState);

        if (mapView != null)
            mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null)
            mapView.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mapView != null)
            mListener = null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// PUBLIC HELPERS
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void setLatLng(@NonNull LatLng latLng) {
        this.latLng = latLng;
    }

    public void addMarker(@NonNull final LatLng latLng, @NonNull MapboxMap mapboxMap) {
        Marker marker = mapboxMap.addMarker(new MarkerOptions().position(latLng));

        mapboxMap.easeCamera(new CameraUpdate() {
            @Nullable
            @Override
            public CameraPosition getCameraPosition(@NonNull MapboxMap mapboxMap) {
                CameraPosition.Builder cameraPosition = new CameraPosition.Builder();
                cameraPosition.zoom(mapboxMap.getCameraPosition().zoom);
                cameraPosition.target(latLng);
                return cameraPosition.build();
            }
        });

        if (lastMarker != null)
            mapboxMap.removeMarker(lastMarker);

        lastMarker = marker;
        this.latLng = latLng;
    }

    public void setEditMode(boolean e) {
        mEditMode = e;
    }

    public MapboxMap getMapView() {
        return mMapboxMap;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// LISTENERS
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onClick(View v) {
        double zoom = mMapboxMap.getCameraPosition().zoom;
        switch (v.getId()) {
            case R.id.map_zoom_in:
                mMapboxMap.setCameraPosition((new CameraPosition.Builder()).zoom(++zoom).build());
                break;
            case R.id.map_zoom_out:
                mMapboxMap.setCameraPosition((new CameraPosition.Builder()).zoom(--zoom).build());
                break;
        }
    }

    MapboxMap.OnMapClickListener mapClickListener = new MapboxMap.OnMapClickListener() {
        @Override
        public void onMapClick(@NonNull LatLng point) {
            Toast.makeText(MapFragment.this.getContext(), "Click on the marker to add a new POI",
                    Toast.LENGTH_LONG).show();
            addMarker(point, mMapboxMap);
        }
    };

    MapboxMap.OnMarkerClickListener markerClickListener = new MapboxMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(@NonNull Marker marker) {
            PoiAlertCreate.Builder builder = new PoiAlertCreate.Builder();
            builder.setContext(getContext())
                   .setLat(marker.getPosition().getLatitude())
                   .setLng(marker.getPosition().getLongitude())
                   .setViewId(R.layout.add_poi_alert_view)
                   .setListener(new BasePoiAlert.IOnClick() {
                       @Override
                       public <T extends Model> void OnOkClickListenr(T pointOfInterest) {
                           MapFragment.this.getActivity().onBackPressed();
                       }

                       @Override
                        public void OnCancelClickListener() {
                            Log.e("hello:", "not imp");
                        }
                    }).create();
            return false;
        }
    };

    View.OnClickListener goNavigate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Geo will get the camera to the current loc, ?q= (query) will ask for navigation
            Uri location = Uri.parse("geo:" + latLng.getLatitude() + "," +
                    latLng.getLongitude() + "('mission')" + "?q=" + latLng.getLatitude() + ","
                    + latLng.getLongitude());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

            PackageManager packageManager = getActivity().getPackageManager();
            List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);

            boolean isIntentSafe = (activities.size() > 0);

            for (ResolveInfo ri : activities) {
                System.out.print(ri.activityInfo.name);
                System.out.print(ri.activityInfo.describeContents());
            }
            if (isIntentSafe) {
                startActivity(mapIntent);
            }
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// INTERFACE USED AS CALLBACK
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public interface OnFragmentInteractionListener {
    }
}
