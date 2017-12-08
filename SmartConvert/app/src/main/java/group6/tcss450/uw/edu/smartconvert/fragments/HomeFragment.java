package group6.tcss450.uw.edu.smartconvert.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import group6.tcss450.uw.edu.smartconvert.R;
import group6.tcss450.uw.edu.smartconvert.misc.Prefs;

/**
 * Home Fragment is a fragment that handles the home fragment or where the user will go to after login.
 * User who have completed their tutorial or successfully login will be redirected to this fragment.
 * Home Fragment is not yet doing any API calls of ASYNC TASK call
 *
 * As of version Nov10,2017 Home Fragment is not display user's current location yet
 * as we have not implemented the API calls for this
 *
 * @author Irene Fransiga, Donald Muffler, Josh Lau
 * @version Nov 10, 2017
 */
public class HomeFragment extends Fragment implements  View.OnClickListener {

    /**The Listener to communicate with main activity class**/
    private HomeFragmentInteractionListener mListener;

    /**The field that will handle the TEXT VIEW to display user's current location**/
    private TextView mCurrentLocationField;

    /**A reference to the confirm email fragment**/
    private View mView;

    /** Current location **/
    private String mCurrentLocation;

    /**
     * Constructor.
     */
    public HomeFragment() {}


    /**
     * Creates the view of the fragment.
     * @param inflater infates the view.
     * @param container the container.
     * @param savedInstanceState the saved state.
     * @return the view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        Button b = mView.findViewById(R.id.convertHomeButton);
        b.setOnClickListener(this);
        mCurrentLocationField = (TextView) mView.findViewById(R.id.currentLocationText);
        postCountry();

        return mView;
    }

    /**
     * Attaches a fragment to an activity.
     * @param context context of the current state.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragmentInteractionListener) {
            mListener = (HomeFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * Sets the country is the back button is pushed on the following frag.
     */
    @Override
    public void onResume() {
        super.onResume();
        postCountry();
    }

    /**
     * Detaches the fragment from the activity.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Listener for button presses.
     * @param view the button that was pressed.
     */
    @Override
    public void onClick(View view) {
        if (mListener != null) {
            if (view.getId() == R.id.convertHomeButton) {
                mListener.homeFragmentInteraction("Convert");
            }
        }
    }

    /**
     * The interface that should be implemented by main activities
     * or any activities that contain this fragment.
     */
    public interface HomeFragmentInteractionListener {
        void homeFragmentInteraction(String fragString);
    }

    /**
     * Helper method to post the current country.
     */
    private void postCountry() {
        mCurrentLocation = (String) Prefs.getFromPrefs(getActivity(), getActivity().getString(R.string.prefs), getActivity().getString(R.string.location_key), Prefs.STRING);
        if (mCurrentLocation != null) {
            mCurrentLocationField.setText(mCurrentLocation);
        }
    }
}