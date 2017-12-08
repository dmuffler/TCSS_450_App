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
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import group6.tcss450.uw.edu.smartconvert.R;
import group6.tcss450.uw.edu.smartconvert.misc.Prefs;


/**
 * Profile Fragment that allows the user to view their profile in the app.
 *
 * @author Irene Fransiga, Donald Muffler, Josh Lau
 * @version Dec 7, 2017
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{

    /**The URL to connect to the main database**/
    private static final String PARTIAL_URL = "http://cssgate.insttech.washington.edu/~if30/";

    /**
     * Profile listener.
     */
    private ProfileOnFragmentInteractionListener mListener;

    /**
     * Email text field.
     */
    private TextView mEmail;

    /**
     * First name text field.
     */
    private TextView mFirstName;

    /**
     * Lasat name text field.
     */
    private TextView mLastName;

    /**
     * Constructor for the profile.
     */
    public ProfileFragment() { }

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
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        Button b = v.findViewById(R.id.Pedit_Button);
        b.setOnClickListener(this);

        mEmail = (TextView) v.findViewById(R.id.emailText);
        mFirstName = (TextView) v.findViewById(R.id.fName_Text);
        mLastName = (TextView) v.findViewById(R.id.lastnameText);

        String email = (String) Prefs.getFromPrefs(getActivity(), getString(R.string.prefs), getString(R.string.email_key), Prefs.STRING);

        AsyncTask<String, String, String> task = new GetProfileData();
        task.execute(PARTIAL_URL, email);

        return v;
    }

    /**
     * Attaches a fragment to an activity.
     * @param context context of the current state.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProfileOnFragmentInteractionListener) {
            mListener = (ProfileOnFragmentInteractionListener) context;
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
     * @param v of the item that is being clicked
     */
    @Override
    public void onClick(View v) {
        if (mListener != null) {
            if (v.getId() == R.id.Pedit_Button) {
                //Log.d("PROFILE", "user wants to edit profile page");
                mListener.profileOnFragmentInteraction("EditProfile");
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
    public interface ProfileOnFragmentInteractionListener {
        void profileOnFragmentInteraction(String page);
    }

    /**
     * This class's job is to connect to the php file to get user data for the profile.
     */
    private class GetProfileData extends AsyncTask<String, String, String> {

        /**the file name to connect to. PARTIAL_URL + this file name**/
        private final String INFO ="profile.php";

        /**The email to receive user data about**/
        String emailToSend;

        /**
         * Sends email to database and retrieves user info.
         * @param strings array of strings.
         * @return String user data response.
         */
        @Override
        protected String doInBackground(String... strings){
            //EXPECTED = partial url, username(email), and password
            if (strings.length != 2) {
                Log.d("ACTIVITY" , strings.length + "");
                throw new IllegalArgumentException("Three String arguments required.");
            }

            String response = "";
            HttpURLConnection urlConnection = null;
            String url = strings[0];
            emailToSend = strings[1];
            String username = "?my_email=" + strings[1];

            try {
                URL urlObject = new URL(url + INFO + username);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                Log.d("ERROR CONN", url + INFO + username);
                response = "Unable to connect, Reason: " + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            Log.e("HERE", response);
            return response;
        }

        /**
         * Called once doInBackground is completed. Wraps up the aSynch task.
         * @param result the result from doInBackground - a string denoting if login was successful.user firstname and lastname.
         */
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject object = new JSONObject(result);

                mEmail.setText((String) Prefs.getFromPrefs(getActivity(), getString(R.string.prefs), getString(R.string.email_key), Prefs.STRING));
                mFirstName.setText(object.getString("first"));
                mLastName.setText(object.getString("last"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
