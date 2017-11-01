package group6.tcss450.uw.edu.smartconvert;

import android.content.Context;
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
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFrag.LoginFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class LoginFrag extends Fragment implements View.OnClickListener {

    private static final String PARTIAL_URL = "http://cssgate.insttech.washington.edu/~if30/";
    //private static final String PARTIAL_URL = "http://cssgate.insttech.washington.edu/~dmuffler/";

    private LoginFragmentInteractionListener mListener;
    private View v;
    private EditText userNameTextField;
    private EditText userPassTextField;

    public LoginFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_login, container, false);

        Button b = (Button) v.findViewById(R.id.submitButton);
        b.setOnClickListener(this);

        userNameTextField = (EditText) v.findViewById(R.id.usernameField);
        userPassTextField = (EditText) v.findViewById(R.id.passwordField);

        return v;
    }

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            if (view.getId() == R.id.submitButton) {
                AsyncTask<String, String, String> task = null;
                String username = ((EditText) v.findViewById(R.id.usernameField)).getText().toString();
                String password = ((EditText) v.findViewById(R.id.passwordField)).getText().toString();
                Log.d("LOGIN", username + " " + password);
                task = new CheckLoginData();
                task.execute(PARTIAL_URL, username, password);
                //String homeFrag = "Home";
                //mListener.loginFragmentInteraction(homeFrag);
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
    public interface LoginFragmentInteractionListener {
        // TODO: Update argument type and name
        void loginFragmentInteraction(String fragString);
    }
    private class CheckLoginData extends AsyncTask<String, String, String>{
        private final String LOGIN ="checkUserLogin.php";
        @Override
        protected String doInBackground(String... strings){

            if (strings.length != 3) {
                Log.d("ACTIVITY" , strings.length + "");
                throw new IllegalArgumentException("Three String arguments required.");
            }
            String response = "";
            HttpURLConnection urlConnection = null;
            String url = strings[0];
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
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
            if (result.equals("Login Successful")) {
                String homeFrag = "Home";
                mListener.loginFragmentInteraction(homeFrag);
            }
        }
    }
}
