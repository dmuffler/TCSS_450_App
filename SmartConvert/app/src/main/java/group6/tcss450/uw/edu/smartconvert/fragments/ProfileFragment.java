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
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{

    private ProfileOnFragmentInteractionListener mListener;
    public ProfileFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        Button b = v.findViewById(R.id.Pedit_Button);
        b.setOnClickListener(this);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProfileOnFragmentInteractionListener) {
            mListener = (ProfileOnFragmentInteractionListener) context;
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
    public void onClick(View v) {
        if (mListener != null) {
            if (v.getId() == R.id.Pedit_Button) {
                //Log.d("PROFILE", "user wants to edit profile page");
                mListener.profileOnFragmentInteraction("EditProfile");
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
    public interface ProfileOnFragmentInteractionListener {
        void profileOnFragmentInteraction(String page);
    }
}
