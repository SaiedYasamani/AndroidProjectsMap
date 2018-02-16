package com.android.yasamani.ilocation.Map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.yasamani.ilocation.R;
import com.android.yasamani.ilocation.Utils.GoogleGeocodeEnteties.GoogleGeocodes;
import com.android.yasamani.ilocation.Utils.LocationPoints;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.realm.Realm;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        contract.view, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveStartedListener {

    private GoogleMap mMap;
    List<LatLng> pointsList = new ArrayList<>();
    contract.presenter presenter;
    EditText input;
    Button go;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }

        Intent serviceIntent = new Intent(this,LocationUpdateService.class);
        startService(serviceIntent);

        presenter = new Presenter();
        presenter.onAttachView(this);

        input = (EditText) findViewById(R.id.searchBar);
        go = (Button) findViewById(R.id.go);
        delete = (Button) findViewById(R.id.delete);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAddressEntered(input.getText().toString());
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.deleteAll();
                realm.commitTransaction();
                drawRoutGraph();
                Toast.makeText(MapsActivity.this, "History deleted.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMapConfig();
        LatLng sydney = new LatLng(-34, 151);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        drawRoutGraph();
        Toast.makeText(this, "You have walked about : " + Math.round(ComputeDistance(pointsList.size())) + " meters.", Toast.LENGTH_SHORT).show();
    }

    void setMapConfig() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveStartedListener(this);
        mMap.setMyLocationEnabled(true);
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
        LatLng userLocation = new LatLng(lat, lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
    }

    @Override
    public void showException() {
        Toast.makeText(this, "Invalid Address!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void drawRoutGraph() {
        presenter.onLoadPoints();
        if(pointsList.size() != 0){
            Polyline userRout = mMap.addPolyline(new PolylineOptions().addAll(pointsList));
            userRout.setColor(Color.GREEN);
            userRout.setWidth(5);
        }
    }

    @Override
    public void loadPoints(List<LatLng> pointList) {
        pointsList = pointList;
    }

    @Override
    public void onCameraIdle() {
        Toast.makeText(this, "Lat: " + mMap.getCameraPosition().target.latitude
                + ", Long: " + mMap.getCameraPosition().target.longitude, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraMoveStarted(int i) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventReceive(Location location){
        Toast.makeText(this, "Your Current Location is: Lng: " + location.getLongitude() + " Lat: " + location.getLatitude(), Toast.LENGTH_LONG).show();
        drawRoutGraph();
        Toast.makeText(this, "You have walked about : " + Math.round(ComputeDistance(pointsList.size())) + " meters.", Toast.LENGTH_SHORT).show();
    }

    public double ComputeDistance(int size){

        double distance = 0;
        if(size != 0)
        {
            for (int i = 0; i < size - 1; i++) {
                distance =+ SphericalUtil.computeDistanceBetween(pointsList.get(i+1),pointsList.get(i));
            }
        }
        return distance;
    }
}
