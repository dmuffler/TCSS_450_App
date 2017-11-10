package group6.tcss450.uw.edu.smartconvert;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Tutorial Fragment that shows the user the different screens that are in the app.
 * @author Irene Fransiga, Donald Muffler, Josh Lau
 * @version Nov 10, 2017
 */
public class Tutorial2Fragment extends Fragment implements View.OnClickListener {

    /**
     * An interface that reports back to the containing activiy.
     */
    private TutorialFragmentInteractionListener mListener;

    /**
     * Constructor.
     */
    public Tutorial2Fragment() {
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
        View v = inflater.inflate(R.layout.fragment_tutorial2, container, false);

        Button b = (Button) v.findViewById(R.id.arrowTutorialLeftButton);
        b.setOnClickListener(this);
        b =  v.findViewById(R.id.arrowTutorialRightButton);
        b.setOnClickListener(this);
        b = (Button) v.findViewById(R.id.skipTutorialButton);
        b.setOnClickListener(this);

        return v ;
    }

    /**
     * Attaches a fragment to an activity.
     * @param context context of the current state.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TutorialFragmentInteractionListener) {
            mListener = (TutorialFragmentInteractionListener) context;
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
     * @param view the button that was pressed.
     */
    @Override
    public void onClick(View view) {
        if (mListener != null) {
            String frag = "";
            switch (view.getId()) {
                case R.id.skipTutorialButton:
                    frag = "Home";
                    break;
                case R.id.arrowTutorialLeftButton:
                    frag = "Tutorial1";
                    break;
                case R.id.arrowTutorialRightButton:
                    frag = "Tutorial3";
                    break;
            }
            mListener.tutorialFragmentInteraction(frag);
        }
    }

    /**
     * The interface that should be implemented by main activities
     * or any activities that contain this fragment.
     */
    public interface TutorialFragmentInteractionListener {
        // TODO: Update argument type and name
        void tutorialFragmentInteraction(String fragString);
    }
}
