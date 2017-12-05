package group6.tcss450.uw.edu.smartconvert.main;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import group6.tcss450.uw.edu.smartconvert.misc.Alert;
import group6.tcss450.uw.edu.smartconvert.R;
import group6.tcss450.uw.edu.smartconvert.fragments.ConfirmEmailFragment;
import group6.tcss450.uw.edu.smartconvert.fragments.ConvertFragment;
import group6.tcss450.uw.edu.smartconvert.fragments.EditProfileFragment;
import group6.tcss450.uw.edu.smartconvert.fragments.HomeFragment;
import group6.tcss450.uw.edu.smartconvert.fragments.LoginFrag;
import group6.tcss450.uw.edu.smartconvert.fragments.ProfileFragment;
import group6.tcss450.uw.edu.smartconvert.fragments.RegisterFragment;
import group6.tcss450.uw.edu.smartconvert.fragments.SettingFragment;
import group6.tcss450.uw.edu.smartconvert.fragments.StartFrag;
import group6.tcss450.uw.edu.smartconvert.fragments.Tutorial1Fragment;
import group6.tcss450.uw.edu.smartconvert.fragments.Tutorial2Fragment;
import group6.tcss450.uw.edu.smartconvert.fragments.Tutorial3Fragment;
import group6.tcss450.uw.edu.smartconvert.misc.Prefs;
import group6.tcss450.uw.edu.smartconvert.misc.Translate;

/**
 * Main class that handles fragment transactions and data passing between screens.
 * @author Irene Fransiga, Donald Muffler, Josh Lau
 * @version Nov 10, 2017
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, StartFrag.StartFragInteractionListener,
        LoginFrag.LoginFragmentInteractionListener, RegisterFragment.RegisterFragmentInteractionListener,
        ConfirmEmailFragment.ConfirmEmailFragmentInteractionListener, Tutorial1Fragment.TutorialFragmentInteractionListener,
        Tutorial2Fragment.TutorialFragmentInteractionListener, Tutorial3Fragment.TutorialFragmentInteractionListener,
        HomeFragment.HomeFragmentInteractionListener, ConvertFragment.ConvertFragmentInteractionListener,
        ProfileFragment.ProfileOnFragmentInteractionListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, SettingFragment.SettingOnFragmentInteractionListener, EditProfileFragment.EditProfileOnFragmentInteractionListener{


    private GoogleApiClient mGoogleApiClient;

   // private static final String TAG = "MyLocationsActivity";
    /**
     * The desired interval for location updates.
     * Inexact. Updates may be more or less frequent.  */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     *  The fastest rate for active location updates.
     *  Exact. Updates will never be more frequent
     *  than this value.  */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    private static final int MY_PERMISSIONS_LOCATIONS = 814;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;





    /**
     * The view of the main container adds in the first fragment.
     * @param savedInstanceState the state the program was last in.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, new StartFrag(), "Start")
                    .commit();

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        }

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than //
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS) ;
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                            , Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_LOCATIONS);
        }

        new Alert(this, mCurrentLocation);
    }

    /**
     * Handler for when the back button is pressed. This will skip the login and register screens
     * if the user attempts to navigate back on the screens immediately following them.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            /* Series of conditionals checking if the screens following login and registration are
             current. If so, the login, register, and confirm email will be skipped.*/
            FragmentManager fragMan = getSupportFragmentManager();

            String fragName = "";
            if (fragMan.getBackStackEntryCount() > 0) {
                fragName = fragMan.getBackStackEntryAt(fragMan.getBackStackEntryCount() - 1).getName();
            }

            if (fragName.equals("Home") || fragName.equals("Confirm Email") || fragName.equals("Tutorial1")) {
                popStack(1);
                onBackPressed();
            } else if (fragMan.getBackStackEntryCount() > 0) {
                super.onBackPressed();
            }
        }
    }

    /**
     * Inflates the menu.
     * @param menu the menu to be inflated.
     * @return boolean if inflation occurred.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu;
        // this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Handler for the options selection.
     * @param item which item was selected.
     * @return boolean if an option was selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            switchFragment(new SettingFragment(), "Setting");
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handler for navigation items.
     * @param item the item that was selected.
     * @return boolean if an item was selected.
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            switchFragment(new ProfileFragment(), "Profile");
        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Helper method for dealing with fragment transactions.
     * @param theFrag the frag to show.
     * @param theFragString the String to retrieve the frag.
     */
    private void switchFragment(final Fragment theFrag, final String theFragString) {
        FragmentTransaction t = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, theFrag, theFragString)
                .addToBackStack(theFragString);
        t.commit();
    }

    /**
     * Recursive function to pop the backstack of fragments. If the backstack is empty,
     * an error is thrown to the user. Warning: index 0 of the backstack is the main screen.
     * If index 0 is popped, the application will close.
     * @param theCount the amount of times the backstack will be poppped.
     */
    private void popStack(final int theCount) {
        if (theCount == 0) {
            return;
        } else {
            if (getSupportFragmentManager().popBackStackImmediate()) {
                popStack(theCount - 1);
            } else {
                Log.e("Error popping backstack", "Backstack is empty");
                return;
            }
        }
    }

    /**
     * Fragment interaction for the starting screen.
     * @param fragString which button was pressed on the start screen.
     */
    @Override
    public void startFragInteraction(String fragString) {
        switch (fragString) {
            case "Login":
                switchFragment(new LoginFrag(), fragString);
                break;
            case "Skip":
                switchFragment(new HomeFragment(), fragString);
                break;
            case "Register":
                switchFragment(new RegisterFragment(), fragString);
                break;
        }
    }

    /**
     * Fragment interaction for the login screen.
     * @param fragString which button was pressed on the login screen.
     * @param emailString the user's email who logged in.
     */
    @Override
    public void loginFragmentInteraction(String fragString, String emailString) {
        if (fragString.equals("Home")) {
            switchFragment(new HomeFragment(), fragString);
        } else if (fragString.equals("Confirm Email")) {
            ConfirmEmailFragment confirmEmailFragment = new ConfirmEmailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.email_key), emailString);
            confirmEmailFragment.setArguments(bundle);
            switchFragment(confirmEmailFragment, fragString);
        }
    }

    /**
     * Fragment interaction for the register screen.
     * @param fragString which button was pressed on the register screen.
     * @param emailString the user's email who registered.
     */
    @Override
    public void registerFragmentInteraction(String fragString, String emailString) {
        if (fragString.equals("Confirm Email")) {
            ConfirmEmailFragment confirmEmailFragment = new ConfirmEmailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.email_key), emailString);
            confirmEmailFragment.setArguments(bundle);
            switchFragment(confirmEmailFragment, fragString);
        }
    }

    /**
     * Fragment interaction for the email confirmation screen.
     * @param fragString which button was pressed on the email confirmation screen.
     */
    @Override
    public void confirmEmailFragmentInteraction(String fragString) {
        if (fragString.equals("Tutorial1")) {
            switchFragment(new Tutorial1Fragment(), "Tutorial1");
        }
    }

    /**
     * Fragment interaction for the tutorial screens.
     * @param fragString which button was pressed on the tutorial screens.
     */
    @Override
    public void tutorialFragmentInteraction(String fragString) {
        switch (fragString) {
            case "Tutorial1":
                switchFragment(new Tutorial1Fragment(), "Tutorial1");
                break;
            case "Tutorial2":
                switchFragment(new Tutorial2Fragment(), "Tutorial2");
                break;
            case "Tutorial3":
                switchFragment(new Tutorial3Fragment(), "Tutorial3");
                break;
            case "Home":
                switchFragment(new HomeFragment(), "Home");
                break;
        }
    }

    /**
     * Fragment interaction for the home screen.
     * @param fragString which button was pressed on the home screen.
     */
    @Override
    public void homeFragmentInteraction(String fragString) {
        switch (fragString) {
            case "Convert":
                switchFragment(new ConvertFragment(), "Convert");
                break;
        }
    }

    /**
     * Fragment interaction for the convert screen.
     * @param Str which button was pressed on the convert screen.
     */
    @Override
    public void convertFragmentInteraction(String Str) {

    }

    @Override
    public void profileOnFragmentInteraction(String page) {
        switchFragment(new EditProfileFragment(), "EditProfile");
    }

    @Override
    public void settingOnFragmentInteraction(Uri uri) {

    }

    @Override
    public void editProfileOnFragmentInteraction(String page) {
        switchFragment(new ProfileFragment(), "Profile");
    }

    /***********************************************************************************************
     **************************************Locations************************************************
     ***********************************************************************************************
     */


    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        String code = Translate.translateCoord(this, mCurrentLocation, Translate.COUNTRY_CODE);
        String name = Translate.translateCoord(this, mCurrentLocation, Translate.COUNTRY_NAME);
        Prefs.saveToPrefs(this, getString(R.string.prefs), getString(R.string.location_key), name);
        Prefs.saveToPrefs(this, getString(R.string.prefs), getString(R.string.locationCode_key), code);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // If the initial location was never previously requested, we use
        // FusedLocationApi.getLastLocation() to get it. If it was previously requested, we store
        // its value in the Bundle and check for it in onCreate(). We
        // do not request it again unless the user specifically requests location updates by pressing
        // the Start Updates button.

        // Because we cache the value of the initial location in the Bundle, it means that if the
        // user launches the activity,
        // moves to a new location, and then changes the device orientation, the original location
        // is displayed as the activity is re-created.
        if (mCurrentLocation == null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                //if (mCurrentLocation != null)
                //    Log.i(TAG, mCurrentLocation.toString());
                startLocationUpdates();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        //Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // Refer to the javadoc for ConnectionResult to see what error codes
        // might be returned in
        // onConnectionFailed.
        //Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_LOCATIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // locations-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Locations need to be working for this portion, please provide permission"
                            , Toast.LENGTH_SHORT)
                            .show();
                }             return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     *  Requests location updates from the FusedLocationApi.
     */
    protected void startLocationUpdates() {
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/Loca tionListener.html).
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        }
    }

    /**
     *  Removes location updates from the FusedLocationApi.
     */
    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/Loca tionListener.html).
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    @Override
    public void onStart() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop(); }

    /**
     * The interface that should be implemented by main activities
     * or any activities that contain this fragment.
     */
    public interface HomeFragmentInteractionListener {
        void homeFragmentInteraction(String fragString);
    }
}