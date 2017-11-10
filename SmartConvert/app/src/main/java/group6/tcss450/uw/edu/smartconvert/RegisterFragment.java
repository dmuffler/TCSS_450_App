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
import java.net.InetAddress;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    private View v;
    /**The field that will handle the first name edit text**/
    private EditText fNameTextField;
    /**The field that will handle the last name edit text**/
    private EditText lNameTextField;
    /**The field that will handle the email of the user edit text**/
    private EditText emailTextField;
    /**The field that will handle the password edit text**/
    private EditText passwordTextField;
    /**The field that will handle the confirmation password edit text**/
    private EditText confirmPasswordTextField;

    public RegisterFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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
    /**
     * Listener of items in the fragment.
     * In this case, the button is doing an async task to register the user to our database
     * @param view of the item that is being clicked
     */
    @Override
    public void onClick(View view) {
        if (mListener != null) {
            if (view.getId() == R.id.registerRegisterButton) {

                String fName = fNameTextField.getText().toString();
                String lName = lNameTextField.getText().toString();
                String email = emailTextField.getText().toString();
                String pass = passwordTextField.getText().toString();
                String confirmPass = confirmPasswordTextField.getText().toString();

                Log.d("REGISTER",  fName + " " + lName + " " + email + " " + pass + " " + confirmPass);
/*                if(email.contains("@") && email.contains(".") && pass.equals(confirmPass) &&
                        (pass.length() >= 6 && pass.length() <= 12) &&
                        !(fName.isEmpty())){
                    task = new RegisterData();
                    task.execute(PARTIAL_URL, fName, lName, email, pass);
                } else {*/

                // Inspired by https://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
                Pattern pattern = Pattern.compile("([a-zA-Z0-9!#$%&\'*+-/=?^_`{|}~])+(" +
                        "\\.[a-zA-Z0-9!#$%&\'*+-/=?^_`{|}~]+)*@([a-zA-Z]{3,})(\\.[a-zA-Z]{2,})*$");
                Matcher matcher = pattern.matcher(email);

                /**
                 * Checker to see if the input is valid
                 * We allow last name to be empty because not everyone has a last name
                 */
                if (matcher.find()&& pass.equals(confirmPass) &&
                        (pass.length() >= 6 && pass.length() <= 12) && !(fName.isEmpty())) {
                    AsyncTask<String, String, String> task = new RegisterData();
                    task.execute(PARTIAL_URL, fName, lName, email, pass);
                } else {
                    if(!(pass.length() >= 6 && pass.length() <= 12)){
                        passwordTextField.setError("Password length has to be between 6-12 characters");
                    }
                    if(!(pass.equals(confirmPass))){
                        confirmPasswordTextField.setError("Password not the same");
                    }
                    if (fName.isEmpty()){
                        fNameTextField.setError("Please enter your first name");
                    }
                    if (!(email.contains("@") && email.contains("."))){
                        emailTextField.setError("Please Enter a valid Email Address");
                    }
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
        private String email;
        /**The field to take the user's first name**/
        private String fName;
        /**The field to take the user's last name**/
        private String lName;
        /**The field to take the user's password**/
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
            //Log.e("REGISTER RESPONSE", response);
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            if (result.equals("Register successful.")) {
                AsyncTask<String, String, String> cTask = new SendConfirmation();
                cTask.execute(PARTIAL_URL, email);
                String confirmEmail = "Confirm Email";
                mListener.registerFragmentInteraction(confirmEmail, email);
            } else {
                Log.e("CONFIRMATION RESPONSE A", result);
            }
            Toast.makeText(getActivity(), "Confirmation Code sending, " +
                    "please wait for a few minutes", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * A helper class to directly send the code after the user clicked the "register" button
     * and after the database has successfully registered the user.
     */
    private class SendConfirmation extends AsyncTask<String, String, String>{
        /**the file name to connect to. PARTIAL_URL + this file name**/
        private final String SEND_CONFIRMATION ="sendConfirmation.php";

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
