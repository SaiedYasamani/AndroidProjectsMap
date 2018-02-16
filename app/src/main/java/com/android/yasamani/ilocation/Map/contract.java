package com.android.yasamani.ilocation.Map;

import android.location.Location;

import com.android.yasamani.ilocation.Utils.GoogleGeocodeEnteties.GoogleGeocodes;
import com.android.yasamani.ilocation.Utils.LocationPoints;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Saeed on 1/30/2018.
 */

public interface contract {

    interface view{
        public void showCoordinates(GoogleGeocodes g);
        public void showException();
        public void drawRoutGraph();
        public void loadPoints(List<LatLng> pointList);
    }

    interface presenter{
        public void onAttachView(view v);
        public void onAddressEntered(String address);
        public void loadCoordinates(GoogleGeocodes g);
        public void onLoadPoints();
        public void loadPoints(List<LatLng> pointList);
    }

    interface model{
        public void getCoordinates(String address);
        public void loadPoints();
    }
}
