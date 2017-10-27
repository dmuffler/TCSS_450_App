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
 * {@link Tutorial1Fragment.TutorialFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Tutorial1Fragment extends Fragment implements View.OnClickListener {

    private TutorialFragmentInteractionListener mListener;

    public Tutorial1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tutorial1, container, false);

        Button b = (Button) v.findViewById(R.id.arrowTutorialRightButton);
        b.setOnClickListener(this);
        b = (Button) v.findViewById(R.id.skipTutorialButton);
        b.setOnClickListener(this);

        return v ;
    }

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            String frag = "";
            switch (view.getId()) {
                case R.id.skipTutorialButton:
                    frag = "Home";
                    break;
                case R.id.arrowTutorialRightButton:
                    frag = "Tutorial2";
                    break;
            }
            mListener.tutorialFragmentInteraction(frag);
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
    public interface TutorialFragmentInteractionListener {
        // TODO: Update argument type and name
        void tutorialFragmentInteraction(String fragString);
    }
}
