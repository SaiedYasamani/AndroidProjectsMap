package com.android.yasamani.ilocation;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Saeed on 2/7/2018.
 */

public class BaseApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //initialize realm
        Realm.init(this);
        RealmConfiguration defaultConfig = new RealmConfiguration.Builder()
                .name("defaultRealm.realm")
                .build();
        Realm.setDefaultConfiguration(defaultConfig);
    }
}
