package group6.tcss450.uw.edu.smartconvert.misc;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by donal on 12/4/2017.
 */

public class Translate {

    public static final int COUNTRY_NAME = 0;

    public static final int COUNTRY_CODE = 1;


    private Translate() {

    }

    public static String translateCoord(Context theContext, Location theLocation, int theSwitch) {
        String theString = null;
        Geocoder geo = new Geocoder(theContext, Locale.getDefault());
        try {
            List<Address> list = geo.getFromLocation(theLocation.getLatitude(), theLocation.getLongitude(), 1);
            if (list.size() > 0) {
                if (theSwitch == COUNTRY_NAME) {
                    theString = list.get(0).getCountryName();
                } else if (theSwitch == COUNTRY_CODE) {
                    theString = list.get(0).getCountryCode();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return theString;
    }
}
