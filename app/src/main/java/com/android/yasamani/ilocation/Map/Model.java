package com.android.yasamani.ilocation.Map;

import android.location.Location;
import android.util.Log;

import com.android.yasamani.ilocation.Utils.Constants;
import com.android.yasamani.ilocation.Utils.GoogleGeocodeEnteties.GoogleGeocodes;
import com.android.yasamani.ilocation.Utils.LocationPoints;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;

/**
 * Created by Saeed on 1/30/2018.
 */

public class Model implements contract.model {

    String TAG = "webservice Error";
    contract.presenter presenter;
    Realm realm = Realm.getDefaultInstance();

    public Model(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getCoordinates(String address) {
            Constants.webService.getGeocodes(address,"AIzaSyCikFHKIIgMb-GJRGD62x9deSySjgp15SQ").enqueue(new Callback<GoogleGeocodes>() {
                @Override
                public void onResponse(Call<GoogleGeocodes> call, Response<GoogleGeocodes> response) {

                    if(response.body().getResults().size() != 0){

                        presenter.loadCoordinates(response.body());
                    }
                }

                @Override
                public void onFailure(Call<GoogleGeocodes> call, Throwable t) {

                }
            });
    }

    @Override
    public void getLocation(Location l) {
        LocationPoints point = new LocationPoints(l.getLatitude(),l.getLongitude(),l.getTime());
        presenter.loadLocation(point);
    }

    @Override
    public void storeLocation(LocationPoints p) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(p);
        realm.commitTransaction();
    }

    @Override
    public void loadPoints() {

        List<LocationPoints> points = realm.where(LocationPoints.class).findAll();
        List<LatLng> pointList = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            pointList.add(new LatLng(points.get(i).getPointLat(),points.get(i).getPointLng()));
        }

        presenter.loadPoints(pointList);
    }

    private void onComplete() {
    }

    private void onError(Throwable throwable) {
        Log.d(TAG, "onError: " + throwable.getMessage());
    }

    private void onResponse(GoogleGeocodes googleGeocodes) {
        presenter.loadCoordinates(googleGeocodes);
    }
}
