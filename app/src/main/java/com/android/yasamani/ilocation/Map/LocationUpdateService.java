package com.android.yasamani.ilocation.Map;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.yasamani.ilocation.Utils.LocationPoints;

import org.greenrobot.eventbus.EventBus;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.realm.Realm;

/**
 * Created by Saeed on 2/12/2018.
 */

public class LocationUpdateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();
        Log.d("service", "onCreate: Service created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("serviceCommand", "onCreate: Service command");
        Toast.makeText(this, "Service Started!", Toast.LENGTH_LONG).show();
        Realm realm = Realm.getDefaultInstance();

        SmartLocation.with(this).location().start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location) {

                realm.beginTransaction();
                LocationPoints point = realm.createObject(LocationPoints.class,getNextKey());
                point.setPointLat(location.getLatitude());
                point.setPointLng(location.getLongitude());
                point.setPointTime(location.getTime());
                realm.commitTransaction();
                Toast.makeText(LocationUpdateService.this, "Current Location Saved", Toast.LENGTH_SHORT).show();

                EventBus.getDefault().post(location);
            }
        });

        Toast.makeText(this, "realm obj: " + realm.where(LocationPoints.class).findAll().size(), Toast.LENGTH_LONG).show();

        return START_STICKY;
    }

    public int getNextKey() {
        Realm realm = Realm.getDefaultInstance();
        try {
            Number number = realm.where(LocationPoints.class).max("id");
            if (number != null) {
                return number.intValue() + 1;
            } else {
                return 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

}
