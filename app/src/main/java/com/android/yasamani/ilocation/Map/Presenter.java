package com.android.yasamani.ilocation.Map;

import android.util.Log;
import android.widget.Toast;

import com.android.yasamani.ilocation.BaseActivity;
import com.android.yasamani.ilocation.Utils.GoogleGeocodeEnteties.GoogleGeocodes;

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
    public void getCoordinates(String address) {
        model.getCoordinates(address);
    }

    @Override
    public void loadCoordinates(GoogleGeocodes g) {
        view.showCoordinates(g);
    }
}
