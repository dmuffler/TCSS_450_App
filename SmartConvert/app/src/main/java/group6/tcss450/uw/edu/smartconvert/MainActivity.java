package group6.tcss450.uw.edu.smartconvert;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import java.util.ArrayList;
import java.util.List;

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
        HomeFragment.HomeFragmentInteractionListener, ConvertFragment.ConvertFragmentInteractionListener, ProfileFragment.ProfileOnFragmentInteractionListener{

    /**
     * The current fragment displayed on the screen.
     */
    String mCurrentFrag = null;

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

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        }
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
             current. If so, the login, register, and confirm email will be skipped.
             However, if the email is not confirmed, pressing the back button at confirm email
             will instead bring the user back to the login screen with the credentials entered in
             for ease of use.*/
            if (mCurrentFrag.equals("Home") || mCurrentFrag.equals("Confirm Email")) {
                popStack(1);
            } else if (mCurrentFrag.equals("Tutorial1")) {
                popStack(2);
            }

            // The back action.
            super.onBackPressed();

            // The back stack check to make sure the the back press won't cause an exception.
            if (getSupportFragmentManager().getBackStackEntryCount() < 2) {
                mCurrentFrag = "Start";
            } else {
                mCurrentFrag = getSupportFragmentManager().getBackStackEntryAt(
                        getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
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
            switchFragment(new ProfileFragment(), "Profile");
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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
        mCurrentFrag = theFragString;
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
                switchFragment(new LoginFrag(), "Login");
                break;
            case "Skip":
                switchFragment(new HomeFragment(), "Home_Skip");
                break;
            case "Register":
                switchFragment(new RegisterFragment(), "Register");
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
            switchFragment(new HomeFragment(), "Home");
        } else if (fragString.equals("Confirm Email")) {
            ConfirmEmailFragment confirmEmailFragment = new ConfirmEmailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.email_key), emailString);
            confirmEmailFragment.setArguments(bundle);
            switchFragment(confirmEmailFragment, "Comfirm Email");
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
            switchFragment(confirmEmailFragment, "Confirm Email");
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

    }
}
