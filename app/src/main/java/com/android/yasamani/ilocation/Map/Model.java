package com.android.yasamani.ilocation.Map;

import android.util.Log;

import com.android.yasamani.ilocation.Utils.Constants;
import com.android.yasamani.ilocation.Utils.GoogleGeocodeEnteties.GoogleGeocodes;

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

    private void onComplete() {
    }

    private void onError(Throwable throwable) {
        Log.d(TAG, "onError: " + throwable.getMessage());
    }

    private void onResponse(GoogleGeocodes googleGeocodes) {
        presenter.loadCoordinates(googleGeocodes);
    }
}
