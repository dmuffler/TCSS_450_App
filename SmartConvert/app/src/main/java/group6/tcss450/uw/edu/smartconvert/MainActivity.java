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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, StartFrag.StartFragInteractionListener,
        LoginFrag.LoginFragmentInteractionListener, RegisterFragment.RegisterFragmentInteractionListener,
        ConfirmEmailFragment.ConfirmEmailFragmentInteractionListener, Tutorial1Fragment.TutorialFragmentInteractionListener,
        Tutorial2Fragment.TutorialFragmentInteractionListener, Tutorial3Fragment.TutorialFragmentInteractionListener,
        HomeFragment.HomeFragmentInteractionListener, ConvertFragment.ConvertFragmentInteractionListener{



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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

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

    private void switchFragment(Fragment frag) {
        FragmentTransaction t = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, frag)
                .addToBackStack(null);
        t.commit();
    }

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

    @Override
    public void loginFragmentInteraction(String fragString) {
        if (fragString.equals("Home")) {
            switchFragment(new HomeFragment());
        }
    }

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

    @Override
    public void confirmEmailFragmentInteraction(String fragString) {
        if (fragString.equals("Tutorial1")) {
            switchFragment(new Tutorial1Fragment());
        }
    }

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

    @Override
    public void homeFragmentInteraction(String fragString) {
        switch (fragString) {
            case "Convert":
                switchFragment(new ConvertFragment());
                break;
        }
    }

    @Override
    public void convertFragmentInteraction(String Str) {

    }
}
