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
 * {@link RegisterFragment.RegisterFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    private RegisterFragmentInteractionListener mListener;
    private EditText nameTextField;
    private EditText emailTextField;
    private EditText passwordTextField;
    private EditText confirmPasswordTextField;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);


        Button b = (Button) v.findViewById(R.id.registerRegisterButton);
        b.setOnClickListener(this);

        nameTextField = (EditText) v.findViewById(R.id.nameFirstLastField);
        emailTextField = (EditText) v.findViewById(R.id.emailField);
        passwordTextField = (EditText) v.findViewById(R.id.passRegField);
        confirmPasswordTextField = (EditText) v.findViewById(R.id.confirmPassRegField);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RegisterFragmentInteractionListener) {
            mListener = (RegisterFragmentInteractionListener) context;
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
            if (view.getId() == R.id.registerRegisterButton) {
                String tutorialFrag = "Confirm Email";
                mListener.registerFragmentInteraction(tutorialFrag);
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
    public interface RegisterFragmentInteractionListener {
        // TODO: Update argument type and name
        void registerFragmentInteraction(String fragString);
    }
}
