package group6.tcss450.uw.edu.smartconvert.misc;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * Utility class to return a country code or country name given a location.
 *
 * @author Irene Fransiga, Donald Muffler, Josh Lau
 * @version Dec 7, 2017
 */
public class Translate {

    /**
     * Country name constant.
     */
    public static final int COUNTRY_NAME = 0;

    /**
     * Country code constant.
     */
    public static final int COUNTRY_CODE = 1;

    /**
     * Private constructor to prevent instantiation.
     */
    private Translate() {

    }

    /**
     * Translates a location to a country code or country name.
     * @param theContext the context of the app.
     * @param theLocation the location to translate.
     * @param theSwitch the switch to choose which to return.
     * @return the name or code.
     */
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
