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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import group6.tcss450.uw.edu.smartconvert.misc.Encryption;
import group6.tcss450.uw.edu.smartconvert.R;


/**
 * Register Fragment is a fragment that handles the registration page.
 * User who wish to create an account will go to this page
 *
 * This fragment is making one ASYNC TASK call to register the user to the database
 *
 * @author Irene Fransiga, Donald Muffler, Josh Lau
 * @version Nov 10, 2017
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    /**The URL to connect to the main database**/
    private static final String PARTIAL_URL = "http://cssgate.insttech.washington.edu/~if30/";
    /**The Listener to communicate with main activity class**/
    private RegisterFragmentInteractionListener mListener;
    /**A reference to the confirm email fragment**/
    private View mView;
    /**The field that will handle the first name edit text**/
    private EditText mFNameTextField;
    /**The field that will handle the last name edit text**/
    private EditText mLNameTextField;
    /**The field that will handle the email of the user edit text**/
    private EditText mEmailTextField;
    /**The field that will handle the password edit text**/
    private EditText mPasswordTextField;
    /**The field that will handle the confirmation password edit text**/
    private EditText mConfirmPasswordTextField;

    /**
     * Constructor.
     */
    public RegisterFragment() {}

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

        mView = inflater.inflate(R.layout.fragment_register, container, false);

        Button b = (Button) mView.findViewById(R.id.registerRegisterButton);
        b.setOnClickListener(this);

        mFNameTextField = (EditText) mView.findViewById(R.id.nameFirstField);
        mLNameTextField = (EditText) mView.findViewById(R.id.nameLastField);
        mEmailTextField = (EditText) mView.findViewById(R.id.emailField);
        mPasswordTextField = (EditText) mView.findViewById(R.id.passRegField);
        mConfirmPasswordTextField = (EditText) mView.findViewById(R.id.confirmPassRegField);

        return mView;
    }

    /**
     * Attaches a fragment to an activity.
     * @param context context of the current state.
     */
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
     * In this case, the button is doing an async task to register the user to our database
     * @param view of the item that is being clicked
     */
    @Override
    public void onClick(View view) {
        if (mListener != null) {

            if (view.getId() == R.id.registerRegisterButton) {

                String fName = mFNameTextField.getText().toString();
                String lName = mLNameTextField.getText().toString();
                String email = mEmailTextField.getText().toString();
                String pass = mPasswordTextField.getText().toString();
                String confirmPass = mConfirmPasswordTextField.getText().toString();

                Log.d("REGISTER",  fName + " " + lName + " " + email + " " + pass + " " + confirmPass);

                if (validateInput()) {
                    pass = Encryption.encodePass(pass);
                    AsyncTask<String, String, String> task = new RegisterData();
                    task.execute(PARTIAL_URL, fName, lName, email, pass);
                }


            }
        }
    }

    /**
     * The interface that should be implemented by main activities
     * or any activities that contain this fragment.
     */
    public interface RegisterFragmentInteractionListener {
        void registerFragmentInteraction(String fragString, String emailString);
    }
    /**
     * This class's job is to connect to the php file to register the user
     */
    private class RegisterData extends AsyncTask<String, String, String>{
        /**the file name to connect to. PARTIAL_URL + this file name**/
        private final String REGISTER ="registerUser.php";
        /**The field to take the user's email**/
        private String mEmail;
        /**The field to take the user's first name**/
        private String mFName;
        /**The field to take the user's last name**/
        private String mLName;
        /**The field to take the user's password**/
        private String mPass;

        /**
         * Tries to register the user based on the data inputed.
         * @param strings user data to send to database.
         * @return response from php if registration was successful.
         */
        @Override
        protected String doInBackground(String... strings){

            if (strings.length != 5) {
                Log.d("ACTIVITY" , strings.length + "");
                throw new IllegalArgumentException("Five String arguments required.");
            }
            String response = "";
            HttpURLConnection urlConnection = null;
            String url = strings[0];
            mFName = "?my_firstname=" + strings[1];
            mLName = "&my_lastname=" + strings[2];
            mEmail = "&my_email=" + strings[3];
            mPass = "&my_password=" + strings[4];
            try {
                URL urlObject = new URL(url + REGISTER + mFName + mLName + mEmail + mPass);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                Log.d("ERROR CONN", url + REGISTER + mFName + mLName + mEmail + mPass);
                response = "Unable to connect, Reason: " + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            //Log.e("REGISTER RESPONSE", response);
            return response;
        }

        /**
         * Called once doInBackground ic completed. Wraps up the aSynch task.
         * @param result the result from doInBackground - if registration was successful.
         */
        @Override
        protected void onPostExecute(String result) {
            if (result.equals("Register successful.")) {
                AsyncTask<String, String, String> cTask = new SendConfirmation();
                cTask.execute(PARTIAL_URL, mEmail);
                String confirmEmail = "Confirm Email";
                mListener.registerFragmentInteraction(confirmEmail, mEmail);
                Toast.makeText(getActivity(), "Confirmation Code sending, " +
                        "please wait for a few minutes", Toast.LENGTH_LONG).show();
            } else if (result.equals("LoginDataFailed")){
                Toast.makeText(getActivity(), "Email is already registered", Toast.LENGTH_LONG).show();
                Log.e("CONFIRMATION RESPONSE A", result);
            }

        }
    }

    /**
     * A helper class to directly send the code after the user clicked the "register" button
     * and after the database has successfully registered the user.
     */
    private class SendConfirmation extends AsyncTask<String, String, String>{
        /**the file name to connect to. PARTIAL_URL + this file name**/
        private final String SEND_CONFIRMATION ="sendConfirmation.php";

        /**
         * Sends confirmation to email.
         * @param strings confirmation to send to database.
         * @return confirmation was successful or not.
         */
        @Override
        protected String doInBackground(String... strings){
            //EXPECTED = the partial URL and the email to send the code to
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
            return response;
        }

        /**
         * Called once doInBackground ic completed. Wraps up the aSynch task.
         * @param result the result from doInBackground - confirmation was successful.
         */
        @Override
        protected void onPostExecute(String result) {
            if (result.equals("sent")) {
                Log.e("RESPONSE", result);
            } else {
                Log.e("Registration RESPONSE B", result);
            }
        }
    }

    /**
     * Validates the registration input.
     * @return true if valid, false if not.
     */
    private boolean validateInput() {
        boolean validEmail = false;
        boolean validPass = false;
        boolean validFirst = false;
        boolean validLast = false;

        String email = mEmailTextField.getText().toString();

        // Regex inspired by https://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
        // Expanded upon to fit our needs.
        // Chekcs email validity.
        if (checkString(email, "([a-zA-Z0-9!#$%&\'*+-/=?^_`{|}~])+(" +
                "\\.[a-zA-Z0-9!#$%&\'*+-/=?^_`{|}~]+)*@([a-zA-Z]{2,})(\\.[a-zA-Z]{2,})*$")) {
            validEmail = true;
        } else {
            mEmailTextField.setError("A valid email must be entered");
        }

        String password1 = mPasswordTextField.getText().toString();
        String password2 = mConfirmPasswordTextField.getText().toString();

        // Checks password validity.
        if (checkString(password1, "[a-z]")
                && checkString(password1, "[A-Z]")
                && checkLength(password1, 8)
                && checkString(password1, "[!@#$%^&*()_+-`~?.>,<;:\'\"]$")
                && password1.equals(password2)) {
            validPass = true;
        } else {
            mPasswordTextField.setError("Password must contain an uppercase, lowercase, special character, " +
                    "must be at least 8 characters in length, and must match");
        }

        String firstName = mFNameTextField.getText().toString();
        // Checks first name validity.
        if (checkString(firstName, "([a-zA-Z])$"))  {
            validFirst = true;
        } else {
            mFNameTextField.setError("Please enter a valid first name");
        }

        String lastName = mLNameTextField.getText().toString();
        // Chekcs last name validity.
        if (checkString(lastName, "([a-zA-Z])$")) {
            validLast = true;
        } else {
            mLNameTextField.setError("Please enter a valid last name");
        }

        return validEmail && validPass && validFirst && validLast;
    }

    /**
     * Function to check against a pattern.
     * @param theString the string to check against.
     * @param thePattern the pattern to check.
     * @return true if found, false if not.
     */
    private boolean checkString(String theString, String thePattern) {
        Pattern casePattern = Pattern.compile(thePattern);
        Matcher matcher = casePattern.matcher(theString);
        return matcher.find();
    }

    /**
     * checks the length of a string.
     * @param theString the string to check.
     * @param theLength the length to check against.
     * @return true if at least the length, false if less.
     */
    private boolean checkLength(String theString, int theLength) {
        return theString.length() >= theLength;
    }
}
