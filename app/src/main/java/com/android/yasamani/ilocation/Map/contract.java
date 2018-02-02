package com.android.yasamani.ilocation.Map;

import com.android.yasamani.ilocation.Utils.GoogleGeocodeEnteties.GoogleGeocodes;

/**
 * Created by Saeed on 1/30/2018.
 */

public interface contract {

    interface view{
        public void showCoordinates(GoogleGeocodes g);
        public void makeToaste();
    }

    interface presenter{
        public void onAttachView(view v);
        public void getCoordinates(String address);
        public void loadCoordinates(GoogleGeocodes g);
    }

    interface model{
        public void getCoordinates(String address);
    }
}
