package fragments.map;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.juh.poikeeper.R;
import com.example.juh.poikeeper.utils.ConnectionWrapper;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class MapFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_LAT = "latitude";

    private static final String ARG_LNG = "longitude";

    private static final double DEFAULT_LAT = 44.836151;

    private static final double DEFAULT_LNG = -0.580816;

    private LatLng latLng;

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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if (ConnectionWrapper.hasConnexion(getContext())) {
            view = inflater.inflate(R.layout.fragment_map, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_map, container, false);
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
            public void onMapReady(MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;
                CameraPosition.Builder cam = new CameraPosition.Builder();
                cam.zoom(10);

                if (latLng != null) {
                    cam.target(latLng);
                    addMarker(latLng, mapboxMap);
                    mapboxMap.setCameraPosition(cam.build());
                } else {
                    cam.target(new LatLng(DEFAULT_LAT, DEFAULT_LNG));
                    mapboxMap.setCameraPosition(cam.build());
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (latLng != null) {
            outState.putDouble(ARG_LAT, latLng.getLatitude());
            outState.putDouble(ARG_LNG, latLng.getLongitude());
        }
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
        mListener = null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// PUBLIC HELPERS
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void setLatLng(@NonNull LatLng latLng) {
        this.latLng = latLng;
    }

    public void addMarker(@NonNull LatLng latLng, @NonNull MapboxMap mapboxMap) {
        mapboxMap.addMarker(new MarkerOptions().position(latLng));
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

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// INTERFACE USED AS CALLBACK
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public interface OnFragmentInteractionListener {
    }
}
