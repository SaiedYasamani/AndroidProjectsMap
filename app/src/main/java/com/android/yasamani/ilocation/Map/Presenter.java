package com.android.yasamani.ilocation.Map;

import android.content.Context;
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
        if (address.isEmpty()){
            view.showException();
        }
        else {

            model.getCoordinates(address);
        }
    }

    @Override
    public void loadCoordinates(GoogleGeocodes g) {
        view.showCoordinates(g);
    }

    @Override
    public void onLoadPoints() {
        model.loadPoints();
    }

    @Override
    public void loadPoints(List<LatLng> pointList) {
        view.loadPoints(pointList);
    }
}
