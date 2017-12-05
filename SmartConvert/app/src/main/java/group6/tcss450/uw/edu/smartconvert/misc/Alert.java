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
 * Created by donal on 12/4/2017.
 */

public class Alert extends AlertDialog implements Dialog.OnClickListener{

    Context mContext;

    Location mLocation;

    public Alert(@NonNull Context theContext, Location theLocation) {
        super(theContext);
        mContext = theContext;
        mLocation = theLocation;
        create();
    }

    public void create() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getContext().getString(R.string.alert_title_string));
        builder.setMessage(getContext().getString(R.string.alert_message_string));
        builder.setPositiveButton("Yes", this);
        builder.setNegativeButton("No", this);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        // -1 for yes, -2 for no.
        if (i == -1) {
/*            String country = Translate.translateCoord(mContext, mLocation, Translate.COUNTRY_NAME);
            Prefs.saveToPrefs(mContext, mContext.getString(R.string.prefs), mContext.getString(R.string.location_key), country, Prefs.STRING);*/
        }
    }
}
