package group6.tcss450.uw.edu.smartconvert;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Start Fragment is a fragment that handles the start page when the user launch the app.
 *
 * This fragment does not do any ASYNC TASK call
 *
 * @author Irene Fransiga, Donald Muffler, Josh Lau
 * @version Nov 10, 2017
 */
public class StartFrag extends Fragment implements View.OnClickListener {
    /**The Listener to communicate with main activity class**/
    private StartFragInteractionListener mListener;

    /**
     * Constructor.
     */
    public StartFrag() {}

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
        View v = inflater.inflate(R.layout.fragment_start, container, false);

        Button b = (Button) v.findViewById(R.id.loginStartButton);
        b.setOnClickListener(this);

        b = (Button) v.findViewById(R.id.skipLoginButton);
        b.setOnClickListener(this);

        b = (Button) v.findViewById(R.id.registerStartButton);
        b.setOnClickListener(this);

        return v;
    }
    /**
     * Listener of items in the fragment.
     * @param view of the item that is being clicked
     */
    @Override
    public void onClick(View view) {
        if (mListener != null) {
            String frag = "";
            switch (view.getId()) {
                case R.id.loginStartButton:
                    frag = "Login";
                    break;

                case R.id.skipLoginButton:
                    frag = "Skip";
                    break;

                case R.id.registerStartButton:
                    frag = "Register";
                    break;
            }
            mListener.startFragInteraction(frag);
        }
    }

    /**
     * Attaches a fragment to an activity.
     * @param context context of the current state.
     */
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

    /**
     * Detaches the fragment from the activity.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * The interface that should be implemented by main activities
     * or any activities that contain this fragment.
     */
    public interface StartFragInteractionListener {
        void startFragInteraction(String fragString);
    }
}
