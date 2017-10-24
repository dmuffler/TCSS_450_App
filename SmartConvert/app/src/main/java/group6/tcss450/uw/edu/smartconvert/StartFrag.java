package group6.tcss450.uw.edu.smartconvert;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StartFrag.StartFragInteractionListener} interface
 * to handle interaction events.
 */
public class StartFrag extends Fragment implements View.OnClickListener {

    private StartFragInteractionListener mListener;

    public StartFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_start, container, false);

        Button b = (Button) v.findViewById(R.id.loginStartButton);
        b.setOnClickListener(this);
        b = (Button) v.findViewById(R.id.skipLoginButton);
        b.setOnClickListener(this);
        b = (Button) v.findViewById(R.id.registerStartButton);
        b.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            switch (view.getId()) {
                case R.id.loginStartButton:
                    String loginFrag = "Login";
                    mListener.startFragInteraction(loginFrag);
                    break;
                case R.id.skipLoginButton:
                    String skipFrag = "Skip";
                    mListener.startFragInteraction(skipFrag);
                    break;
                case R.id.registerStartButton:
                    String registerFrag = "Register";
                    mListener.startFragInteraction(registerFrag);
                    break;
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StartFragInteractionListener) {
            mListener = (StartFragInteractionListener) context;
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
    public interface StartFragInteractionListener {
        // TODO: Update argument type and name
        void startFragInteraction(String fragString);
    }
}
