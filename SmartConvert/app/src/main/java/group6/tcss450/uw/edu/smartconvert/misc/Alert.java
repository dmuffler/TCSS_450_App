package group6.tcss450.uw.edu.smartconvert.misc;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import group6.tcss450.uw.edu.smartconvert.R;

/**
 * Creates an alert dialog to ask if the app can save current location as default.
 *
 * @author Irene Fransiga, Donald Muffler, Josh Lau
 * @version Dec 7, 2017
 */
public class Alert extends AlertDialog implements Dialog.OnClickListener{

    /**
     * Context of the app.
     */
    Context mContext;

    /**
     * Current location.
     */
    Location mLocation;

    /**
     * Constructs the alert.
     * @param theContext the context of the app.
     * @param theLocation the current location.
     */
    public Alert(@NonNull Context theContext, Location theLocation) {
        super(theContext);
        mContext = theContext;
        mLocation = theLocation;
        create();
    }

    /**
     * Builds the alert and set the listener.
     */
    public void create() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getContext().getString(R.string.alert_title_string));
        builder.setMessage(getContext().getString(R.string.alert_message_string));
        builder.setPositiveButton("Yes", this);
        builder.setNegativeButton("No", this);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    /**
     * Listener for the yes or no click.
     * @param dialogInterface the dialog.
     * @param i which button was clicked.
     */
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        Boolean accept = false;
        // -1 for yes, -2 for no.
        if (i == -1) {
            accept = true;
        }

        // Saves alert data to prefs, so the app only calls the alert once per device.
        Prefs.saveToPrefs(mContext, mContext.getString(R.string.prefs), mContext.getString(R.string.alert_boo), accept, Prefs.BOOLEAN);
        Prefs.saveToPrefs(mContext, mContext.getString(R.string.prefs), mContext.getString(R.string.alert_done_boo), (Boolean) true, Prefs.BOOLEAN);
    }
}
