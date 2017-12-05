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

import group6.tcss450.uw.edu.smartconvert.R;


/**
 * Confirm Email Fragment is a fragment that handles the confirmation page.
 * User who just registered or tried to login in without without first confirming
 * their credential will be directed to this page.
 *
 * @author Irene Fransiga, Donald Muffler, Josh Lau
 * @version Nov 10, 2017
 */
public class ConfirmEmailFragment extends Fragment implements View.OnClickListener {

    /**The URL to connect to the main database**/
    private static final String PARTIAL_URL = "http://cssgate.insttech.washington.edu/~if30/";
    /**The Listener to communicate with main activity class**/
    private ConfirmEmailFragmentInteractionListener mListener;
    /**The field to refer to the code that the user will be entering**/
    private EditText mVerificationTextField;
    /**field to store the user email for database use**/
    private String mEmail;
    /**A reference to the confirm email fragment**/
    private View mView;

    /**
     * This is just an empty constructor, to call this class
     */
    public ConfirmEmailFragment() { }

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

        mView = inflater.inflate(R.layout.fragment_confirm_email, container, false);

        Button b = (Button) mView.findViewById(R.id.enterVerificationButton);
        b.setOnClickListener(this);

        mVerificationTextField = (EditText) mView.findViewById(R.id.verificationField);
        return mView;
    }

    /**
     * To get email from Bundle
     */
    @Override
    public void onStart(){
        super.onStart();
        if(getArguments()!= null){
            String email = getArguments().getString(getString(R.string.email_key));
            //Log.d("CONFIRM EMAIL", getString(R.string.email_key) + email);
            mEmail = email;
        }
    }

    /**
     * Attaches a fragment to an activity.
     * @param context context of the current state.
     */
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
     * In this case, the button is doing an async task to verify the code entered
     * @param view of the item that is being clicked
     */
    @Override
    public void onClick(View view) {
        if (mListener != null) {
            if (view.getId() == R.id.enterVerificationButton) {
                AsyncTask<String, String, String> task = new SendConfirmation();
                String code = ((EditText) mView.findViewById(R.id.verificationField)).getText().toString();
                Log.d("CONFIRM",  mEmail + " " + code);
                task.execute(PARTIAL_URL, mEmail, code);
                //String confirmFrag = "Tutorial1";
                //mListener.confirmEmailFragmentInteraction(confirmFrag);
            }
        }
    }

    /**
     * The interface that should be implemented by main activities
     * or any activities that contain this fragment.
     */
    public interface ConfirmEmailFragmentInteractionListener {
        void confirmEmailFragmentInteraction(String fragString);
    }

    /**
     * This class's job is to connect to the php file to check if the code matches with the email ID.
     */
    private class SendConfirmation extends AsyncTask<String, String, String> {
        /**the file name to connect to. PARTIAL_URL + this file name**/
        private final String CHECK_CODE ="checkCode.php";

        /**
         * Connects to php to send confirmation email.
         * @param strings user email and code to send to email.
         * @return boolean, confirmation code successful or not.
         */
        @Override
        protected String doInBackground(String... strings){
            //REQUIRED = PARTIAL_URL + the user email + the code
            if (strings.length != 3) {
                Log.d("ACTIVITY" , strings.length + "");
                throw new IllegalArgumentException("Three String arguments required.");
            }
            String response = "";
            HttpURLConnection urlConnection = null;
            String url = strings[0];
            String email = "?my_email=" + strings[1];
            String code = "&my_code=" + strings[2];

            try {
                URL urlObject = new URL(url + CHECK_CODE + email + code);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                Log.e("ERROR CONN", url + CHECK_CODE + email + code);
                response = "Unable to connect, Reason: " + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            //Log.e("CONFIRMATION RESPONSE", response);
            return response;
        }

        /**
         * Called once doInBackground ic completed. Wraps up the aSynch task.
         * @param result the result from doInBackground - a boolean if confirmation was successful.
         */
        @Override
        protected void onPostExecute(String result) {
            if (result.equals("True")) {
                String confirmFrag = "Tutorial1";
                mListener.confirmEmailFragmentInteraction(confirmFrag);
            } else {
                Toast.makeText(getActivity(), "Incorrect Code", Toast.LENGTH_LONG).show();
            }
        }
    }
}
