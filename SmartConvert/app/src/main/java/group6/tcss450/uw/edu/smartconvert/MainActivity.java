package group6.tcss450.uw.edu.smartconvert;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        HomeFragment.HomeFragmentInteractionListener, ConvertFragment.ConvertFragmentInteractionListener{


    /**
     * The view of the main container adds in the first fragment.
     * @param savedInstanceState the state the program was last in.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, new StartFrag())
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

    /**
     * Handler for when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Inflates the menu.
     * @param menu the menu to be inflated.
     * @return boolean if inflation occurred.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            return true;
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
     * @param frag the frag to show.
     */
    private void switchFragment(Fragment frag) {
        FragmentTransaction t = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, frag)
                .addToBackStack(null);
        t.commit();
    }

    /**
     * Fragment interaction for the starting screen.
     * @param fragString which button was pressed on the start screen.
     */
    @Override
    public void startFragInteraction(String fragString) {
        switch (fragString) {
            case "Login":
                switchFragment(new LoginFrag());
                break;
            case "Skip":
                switchFragment(new HomeFragment());
                break;
            case "Register":
                switchFragment(new RegisterFragment());
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
            switchFragment(new HomeFragment());
        } else if (fragString.equals("Confirm Email")) {
            ConfirmEmailFragment confirmEmailFragment = new ConfirmEmailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.email_key), emailString);
            confirmEmailFragment.setArguments(bundle);
            switchFragment(confirmEmailFragment);
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
            switchFragment(confirmEmailFragment);
        }
    }

    /**
     * Fragment interaction for the email confirmation screen.
     * @param fragString which button was pressed on the email confirmation screen.
     */
    @Override
    public void confirmEmailFragmentInteraction(String fragString) {
        if (fragString.equals("Tutorial1")) {
            switchFragment(new Tutorial1Fragment());
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
                switchFragment(new Tutorial1Fragment());
                break;
            case "Tutorial2":
                switchFragment(new Tutorial2Fragment());
                break;
            case "Tutorial3":
                switchFragment(new Tutorial3Fragment());
                break;
            case "Home":
                switchFragment(new HomeFragment());
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
                switchFragment(new ConvertFragment());
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
}
