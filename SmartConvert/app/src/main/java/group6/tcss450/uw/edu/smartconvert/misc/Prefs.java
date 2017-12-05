package group6.tcss450.uw.edu.smartconvert.misc;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by donald on 12/4/2017.
 */

public class Prefs {

    public static final int STRING = 0;
    public static final int INT = 1;
    public static final int BOOLEAN = 2;
    public static final int FLOAT = 3;
    public static final int LONG = 4;


    private Prefs() {

    }

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
