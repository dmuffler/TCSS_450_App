package group6.tcss450.uw.edu.smartconvert.misc;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Utility class to save and retrieve data to prefs with one call, rather than multiple.
 *
 * @author Irene Fransiga, Donald Muffler, Josh Lau
 * @version Dec 7, 2017
 */
public class Prefs {

    /**
     * String constant.
     */
    public static final int STRING = 0;

    /**
     * Int constant.
     */
    public static final int INT = 1;

    /**
     * Boolean constant.
     */
    public static final int BOOLEAN = 2;

    /**
     * Float constant.
     */
    public static final int FLOAT = 3;

    /**
     * Long constant.
     */
    public static final int LONG = 4;

    /**
     * Private constructor to prevent instantiation.
     */
    private Prefs() {

    }

    /**
     * Saves data to shared preferences with one call.
     * @param theContext the context of the app.
     * @param thePrefs the prefs to save to.
     * @param theKey the key.
     * @param theValue the value.
     * @param theSwitch type of data to save.
     */
    public static void saveToPrefs(Context theContext, String thePrefs, String theKey, Object theValue, int theSwitch) {
        SharedPreferences pref = theContext.getSharedPreferences(thePrefs, theContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        switch (theSwitch) {
            case (STRING):
                editor.putString(theKey, (String) theValue);
                break;
            case (INT):
                editor.putInt(theKey, (Integer) theValue);
                break;
            case (BOOLEAN):
                editor.putBoolean(theKey, (Boolean) theValue);
                break;
            case (FLOAT):
                editor.putFloat(theKey, (Float) theValue);
                break;
            case (LONG):
                editor.putLong(theKey, (Long) theValue);
                break;
            default:
                return;
        }
        editor.apply();
    }

    /**
     * Retrieves data from shared preferences with once call.
     * @param theContext the context of the app.
     * @param thePrefs the prefs to save to.
     * @param theKey the key.
     * @param theSwitch type of data to retrieve.
     * @return the data.
     */
    public static Object getFromPrefs(Context theContext, String thePrefs, String theKey, int theSwitch) {
        SharedPreferences pref = theContext.getSharedPreferences(thePrefs, theContext.MODE_PRIVATE);
        switch (theSwitch) {
            case (STRING):
                return pref.getString(theKey, null);
            case (INT):
                return pref.getInt(theKey, new Integer(0));
            case (BOOLEAN):
                return pref.getBoolean(theKey, new Boolean(false));
            case (FLOAT):
                return pref.getFloat(theKey, new Float(0));
            case (LONG):
                return pref.getLong(theKey, new Long(0));
            default:
                return null;
        }
    }
}
