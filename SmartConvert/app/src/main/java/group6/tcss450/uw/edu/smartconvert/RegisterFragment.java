package group6.tcss450.uw.edu.smartconvert;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.RegisterFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    private static final String PARTIAL_URL = "http://cssgate.insttech.washington.edu/~if30/";

    private RegisterFragmentInteractionListener mListener;
    private View v;
    private EditText fNameTextField;
    private EditText lNameTextField;
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
        v = inflater.inflate(R.layout.fragment_register, container, false);


        Button b = (Button) v.findViewById(R.id.registerRegisterButton);
        b.setOnClickListener(this);

        fNameTextField = (EditText) v.findViewById(R.id.nameFirstField);
        lNameTextField = (EditText) v.findViewById(R.id.nameLastField);
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
                AsyncTask<String, String, String> task = null;
                String fName = ((EditText) v.findViewById(R.id.nameFirstField)).getText().toString();
                String lName = ((EditText) v.findViewById(R.id.nameLastField)).getText().toString();
                String email = ((EditText) v.findViewById(R.id.emailField)).getText().toString();
                String pass = ((EditText) v.findViewById(R.id.passRegField)).getText().toString();
                String confirmPass = ((EditText) v.findViewById(R.id.confirmPassRegField)).getText().toString();
                Log.d("REGISTER",  fName + " " + lName + " " + email + " " + pass + " " + confirmPass);
                if(pass.equals(confirmPass)){
                    task = new RegisterData();
                    task.execute(PARTIAL_URL, fName, lName, email, pass);
                } else {
                    Log.d("REGISTER", "Password don't match");
                }
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
        void registerFragmentInteraction(String fragString, String emailString);
    }
    private class RegisterData extends AsyncTask<String, String, String>{
        private final String REGISTER ="registerUser.php";
        private String email;
        private String fName;
        private String lName;
        private String pass;
        @Override
        protected String doInBackground(String... strings){

            if (strings.length != 5) {
                Log.d("ACTIVITY" , strings.length + "");
                throw new IllegalArgumentException("Five String arguments required.");
            }
            String response = "";
            HttpURLConnection urlConnection = null;
            String url = strings[0];
            fName = "?my_firstname=" + strings[1];
            lName = "&my_lastname=" + strings[2];
            email = "&my_email=" + strings[3];
            pass = "&my_password=" + strings[4];
            try {
                URL urlObject = new URL(url + REGISTER + fName + lName + email + pass);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                Log.d("ERROR CONN", url + REGISTER + fName + lName + email + pass);
                response = "Unable to connect, Reason: " + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            Log.e("REGISTER RESPONSE", response);
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
            if (result.equals("Register successful.")) {
                AsyncTask<String, String, String> cTask = new SendConfirmation();
                cTask.execute(PARTIAL_URL, email);
                String confirmEmail = "Confirm Email";
                mListener.registerFragmentInteraction(confirmEmail, email);
            } else {
                Log.e("CONFIRMATION RESPONSE A", result);
            }
            Toast.makeText(getActivity(), "Confirmation Code sending, please wait for a few minutes", Toast.LENGTH_LONG).show();
        }
    }
    private class SendConfirmation extends AsyncTask<String, String, String>{
        private final String SEND_CONFIRMATION ="sendConfirmation.php";
        @Override
        protected String doInBackground(String... strings){

            if (strings.length != 2) {
                Log.d("ACTIVITY" , strings.length + "");
                throw new IllegalArgumentException("Two String arguments required.");
            }
            String response = "";
            HttpURLConnection urlConnection = null;
            String url = strings[0];
            String email = "?my_email=" + strings[1];
            try {
                URL urlObject = new URL(url + SEND_CONFIRMATION + email);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                Log.d("ERROR CONN", url + SEND_CONFIRMATION + email);
                response = "Unable to connect, Reason: " + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            //Log.e("CONFIRMATION RESPONSE", response);
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            if (result.equals("sent")) {
                Log.e("RESPONSE", result);
            } else {
                Log.e("Registration RESPONSE B", result);
            }
        }
    }
}
