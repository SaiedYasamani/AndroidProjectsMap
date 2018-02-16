package com.android.yasamani.ilocation.Utils;

import android.location.Location;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Saeed on 2/7/2018.
 */

public class LocationPoints extends RealmObject {

    @PrimaryKey
    int id;
    double pointLat;
    double pointLng;
    double pointTime;

    public LocationPoints(int id, double pointLat, double pointLng, double pointTime) {
        this.id = id;
        this.pointLat = pointLat;
        this.pointLng = pointLng;
        this.pointTime = pointTime;
    }

    public LocationPoints(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPointLat() {
        return pointLat;
    }

    public void setPointLat(double pointLat) {
        this.pointLat = pointLat;
    }

    public double getPointLng() {
        return pointLng;
    }

    public void setPointLng(double pointLng) {
        this.pointLng = pointLng;
    }

    public double getPointTime() {
        return pointTime;
    }

    public void setPointTime(double pointTime) {
        this.pointTime = pointTime;
    }

    @Override
    public String toString() {
        return "LocationPoints{" +
                "id=" + id +
                ", pointLat=" + pointLat +
                ", pointLng=" + pointLng +
                ", pointTime=" + pointTime +
                '}';
    }
}
