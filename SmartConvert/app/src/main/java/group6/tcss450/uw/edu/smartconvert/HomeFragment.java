<<<<<<< HEAD
package group6.tcss450.uw.edu.smartconvert;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    DataPassListener mCallback;
    public interface DataPassListener {
        public void passData(String data);
    }
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try
        {
            mCallback = ()
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.convertButton) {
            mCallback.passData()
        }
        if (mListener != null) {
            if (view.getId() == R.id.submitButton) {
                AsyncTask<String, String, String> task = null;
                String username = ((EditText) v.findViewById(R.id.usernameField)).getText().toString();
                String password = ((EditText) v.findViewById(R.id.passwordField)).getText().toString();
                Log.d("LOGIN", username + " " + password);
                task = new CheckLoginData();
                task.execute(PARTIAL_URL, username, password);
                //String homeFrag = "Home";
                //mListener.loginFragmentInteraction(homeFrag);
            }
        }
    }
}
=======
package group6.tcss450.uw.edu.smartconvert;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.HomeFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class HomeFragment extends Fragment implements  View.OnClickListener {

    private HomeFragmentInteractionListener mListener;
    private TextView currentLocationField;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface HomeFragmentInteractionListener {
        // TODO: Update argument type and name
        void homeFragmentInteraction(String fragString);
    }
}
>>>>>>> 2ea8e3dd2d5e6b40fd4913db7c1a4042e0f1b325
