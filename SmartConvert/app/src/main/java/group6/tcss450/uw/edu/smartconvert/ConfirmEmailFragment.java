package group6.tcss450.uw.edu.smartconvert;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConfirmEmailFragment.ConfirmEmailFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ConfirmEmailFragment extends Fragment implements View.OnClickListener {

    private ConfirmEmailFragmentInteractionListener mListener;
    private EditText verificationTextField;

    public ConfirmEmailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_confirm_email, container, false);

        Button b = (Button) v.findViewById(R.id.enterVerificationButton);
        b.setOnClickListener(this);

        verificationTextField = (EditText) v.findViewById(R.id.verificationField);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ConfirmEmailFragmentInteractionListener) {
            mListener = (ConfirmEmailFragmentInteractionListener) context;
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
            if (view.getId() == R.id.enterVerificationButton) {
                String confirmFrag = "Tutorial1";
                mListener.confirmEmailFragmentInteraction(confirmFrag);
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
    public interface ConfirmEmailFragmentInteractionListener {
        // TODO: Update argument type and name
        void confirmEmailFragmentInteraction(String fragString);
    }
}
