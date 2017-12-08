package group6.tcss450.uw.edu.smartconvert.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import group6.tcss450.uw.edu.smartconvert.R;


/**
 * Edit profile Fragment that allows the user to change their profile in the app.
 *
 * @author Irene Fransiga, Donald Muffler, Josh Lau
 * @version Dec 7, 2017
 */
public class EditProfileFragment extends Fragment implements View.OnClickListener{

    /**
     * Listener for edit profile.
     */
    private EditProfileOnFragmentInteractionListener mListener;

    /**
     * Construcs edit profile fragment.
     */
    public EditProfileFragment() {
        // Required empty public constructor
    }

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
        View v = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        Button b = v.findViewById(R.id.Psave_Button);
        b.setOnClickListener(this);

        return v;
    }

    /**
     * Attaches a fragment to an activity.
     * @param context context of the current state.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EditProfileOnFragmentInteractionListener) {
            mListener = (EditProfileOnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
     * @param v the button that was pressed.
     */
    @Override
    public void onClick(View v) {
        if (mListener != null) {
            if (v.getId() == R.id.Psave_Button) {
                Log.d("PROFILE", "user wants to save profile page");
                //save the data in the sharedPref
                mListener.editProfileOnFragmentInteraction("SaveProfile");
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
    public interface EditProfileOnFragmentInteractionListener {
        void editProfileOnFragmentInteraction(String page);
    }
}
