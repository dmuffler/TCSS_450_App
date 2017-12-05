package group6.tcss450.uw.edu.smartconvert.fragments;

import android.content.Context;
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

import group6.tcss450.uw.edu.smartconvert.misc.Encryption;
import group6.tcss450.uw.edu.smartconvert.R;


/**
 * Login Fragment is a fragment that handles the login page.
 * User already have an acount registered with the app should be able to login successfully.
 * Otherwise, it will not let user log in and they will stay in the login page
 *
 * This class is making one ASYNC CALL
 *
 * @author Irene Fransiga, Donald Muffler, Josh Lau
 * @version Nov 10, 2017
 */
public class LoginFrag extends Fragment implements View.OnClickListener {
    /**The URL to connect to the main database**/
    private static final String PARTIAL_URL = "http://cssgate.insttech.washington.edu/~if30/";
    /**The Listener to communicate with main activity class**/
    private LoginFragmentInteractionListener mListener;
    /**A reference to the confirm email fragment**/
    private View mView;
    /**The field that will handle the email text that the user will type**/
    private EditText mUserNameTextField;
    /**The field that will handle the password text that the user will type**/
    private EditText mUserPassTextField;

    /**
     * Constructor.
     */
    public LoginFrag() {}

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
        mView = inflater.inflate(R.layout.fragment_login, container, false);

        Button b = (Button) mView.findViewById(R.id.submitButton);
        b.setOnClickListener(this);

        mUserNameTextField = (EditText) mView.findViewById(R.id.usernameField);
        mUserPassTextField = (EditText) mView.findViewById(R.id.passwordField);

        return mView;
    }

    /**
     * Attaches a fragment to an activity.
     * @param context context of the current state.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragmentInteractionListener) {
            mListener = (LoginFragmentInteractionListener) context;
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
     * Listener of items in the fragment.
     * In this case, the button is doing an async task to verify the the user has already registered
     * @param view of the item that is being clicked
     */
    @Override
    public void onClick(View view) {
        if (mListener != null) {
            if (view.getId() == R.id.submitButton) {
                String username = ((EditText) mView.findViewById(R.id.usernameField)).getText().toString();
                String password = ((EditText) mView.findViewById(R.id.passwordField)).getText().toString();
                password = Encryption.encodePass(password);
                AsyncTask<String, String, String> task = new CheckLoginData();
                task.execute(PARTIAL_URL, username, password);
            }
        }
    }

    /**
     * The interface that should be implemented by main activities
     * or any activities that contain this fragment.
     */
    public interface LoginFragmentInteractionListener {
        void loginFragmentInteraction(String fragString, String emailString);
    }
    /**
     * This class's job is to connect to the php file to check if the user has already registered
     */
    private class CheckLoginData extends AsyncTask<String, String, String>{
        /**the file name to connect to. PARTIAL_URL + this file name**/
        private final String LOGIN ="checkUserLogin.php";
        /**The email to be sent to the main activity to sent to the confirmation page**/
        String emailToSend;

        /**
         * Sends user credentials to database.
         * @param strings
         * @return String, login successful or not.
         */
        @Override
        protected String doInBackground(String... strings){
            //EXPECTED = partial url, username(email), and password
            if (strings.length != 3) {
                Log.d("ACTIVITY" , strings.length + "");
                throw new IllegalArgumentException("Three String arguments required.");
            }

            String response = "";
            HttpURLConnection urlConnection = null;
            String url = strings[0];
            emailToSend = strings[1];
            String username = "?my_username=" + strings[1];
            String password = "&my_password=" + strings[2];

            try {
                URL urlObject = new URL(url + LOGIN + username + password);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                Log.d("ERROR CONN", url + LOGIN + username + password);
                response = "Unable to connect, Reason: " + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            Log.e("HERE", response);
            return response;
        }

        /**
         * Called once doInBackground ic completed. Wraps up the aSynch task.
         * @param result the result from doInBackground - a string denoting if login was successful.
         */
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
            if (result.equals("Login Successful")) {
                String homeFrag = "Home";
                mListener.loginFragmentInteraction(homeFrag, emailToSend);
            } else if (result.equals("Email not confirmed yet")){
                String homeFrag = "Confirm Email";
                Log.d("CONFIRM", emailToSend);
                mListener.loginFragmentInteraction(homeFrag, emailToSend);
            }
        }
    }
}
