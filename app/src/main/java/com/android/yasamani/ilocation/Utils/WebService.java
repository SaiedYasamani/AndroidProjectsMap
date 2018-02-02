package com.android.yasamani.ilocation.Utils;

import com.android.yasamani.ilocation.Utils.GoogleGeocodeEnteties.GoogleGeocodes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Saeed on 1/30/2018.
 */

public interface WebService {

    @GET("geocode/json?")
    Call<GoogleGeocodes> getGeocodes(@Query("address") String address , @Query("apiKey") String apiKey);
}
