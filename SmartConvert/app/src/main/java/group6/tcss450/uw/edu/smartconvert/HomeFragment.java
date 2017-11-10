package group6.tcss450.uw.edu.smartconvert;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


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
    private TextView currentLocationField;
    /**A reference to the confirm email fragment**/
    private View v;

    public HomeFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        Button b = v.findViewById(R.id.convertHomeButton);
        b.setOnClickListener(this);
        currentLocationField = (TextView) v.findViewById(R.id.currentLocationNameView);

        return  v;
    }

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

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
}
