package com.android.yasamani.ilocation.Map;

import android.location.Location;

import com.android.yasamani.ilocation.Utils.GoogleGeocodeEnteties.GoogleGeocodes;
import com.android.yasamani.ilocation.Utils.LocationPoints;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Saeed on 1/30/2018.
 */

public class Presenter implements contract.presenter {

    contract.view view;
    contract.model model;

    @Override
    public void onAttachView(contract.view v) {
        this.view = v;
        model = new Model(this);
    }

    @Override
    public void onAddressEntered(String address) {
        model.getCoordinates(address);
    }

    @Override
    public void loadCoordinates(GoogleGeocodes g) {
        view.showCoordinates(g);
    }

    @Override
    public void onLocationUpdate(Location l) {
        model.getLocation(l);
    }

    @Override
    public void loadLocation(LocationPoints p) {
        model.storeLocation(p);
    }

    @Override
    public void onLoadPoints() {
        model.loadPoints();
    }

    @Override
    public void loadPoints(List<LatLng> pointList) {

    }
}
