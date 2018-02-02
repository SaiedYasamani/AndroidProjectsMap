package com.android.yasamani.ilocation.Map;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.yasamani.ilocation.R;
import com.android.yasamani.ilocation.Utils.GoogleGeocodeEnteties.GoogleGeocodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        contract.view, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveStartedListener {

    private GoogleMap mMap;
    contract.presenter presenter;
    EditText input;
    Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        presenter = new Presenter();
        presenter.onAttachView(this);

        input = (EditText) findViewById(R.id.searchBar);
        go = (Button) findViewById(R.id.go);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getCoordinates(input.getText().toString());
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMapConfig();

        LatLng sydney = new LatLng(-34, 151);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    void setMapConfig(){
        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveStartedListener(this);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    @Override
    public void showCoordinates(GoogleGeocodes g) {

        double lat = g.getResults().get(0).getGeometry().getLocation().getLat();
        double lng = g.getResults().get(0).getGeometry().getLocation().getLng();
        LatLng userLocation = new LatLng(lat,lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
    }

    @Override
    public void makeToaste() {
        Toast.makeText(this, "getted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraIdle() {
        Toast.makeText(this, "Lat: " + mMap.getCameraPosition().target.latitude
                + ", Long: " + mMap.getCameraPosition().target.longitude, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraMoveStarted(int i) {
    }
}
