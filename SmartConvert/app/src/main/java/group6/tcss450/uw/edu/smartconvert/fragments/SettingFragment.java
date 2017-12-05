package group6.tcss450.uw.edu.smartconvert.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import group6.tcss450.uw.edu.smartconvert.R;
import group6.tcss450.uw.edu.smartconvert.misc.Prefs;


public class SettingFragment extends Fragment {

    private SettingOnFragmentInteractionListener mListener;

    private TextView mDefaultField;
    private TextView mCurrentField;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        mDefaultField = (TextView) v.findViewById(R.id.defCountryName);
        mCurrentField = (TextView) v.findViewById(R.id.curCountryName);

        Boolean accepted = (Boolean) Prefs.getFromPrefs(getActivity(), getString(R.string.prefs), getString(R.string.alert_boo), Prefs.BOOLEAN);
        if (accepted) {
            mDefaultField.setText((String) Prefs.getFromPrefs(getActivity(), getString(R.string.prefs), getString(R.string.location_key), Prefs.STRING));
            mCurrentField.setText((String) Prefs.getFromPrefs(getActivity(), getString(R.string.prefs), getString(R.string.location_key), Prefs.STRING));
        }
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SettingOnFragmentInteractionListener) {
            mListener = (SettingOnFragmentInteractionListener) context;
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
    public interface SettingOnFragmentInteractionListener {
        // TODO: Update argument type and name
        void settingOnFragmentInteraction(Uri uri);
    }
}
