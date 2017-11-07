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
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConfirmEmailFragment.ConfirmEmailFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ConfirmEmailFragment extends Fragment implements View.OnClickListener {

    private static final String PARTIAL_URL = "http://cssgate.insttech.washington.edu/~if30/";
    private ConfirmEmailFragmentInteractionListener mListener;
    private EditText verificationTextField;
    private String uEmail;
    private View v;

    public ConfirmEmailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_confirm_email, container, false);

        Button b = (Button) v.findViewById(R.id.enterVerificationButton);
        b.setOnClickListener(this);

        verificationTextField = (EditText) v.findViewById(R.id.verificationField);
        return v;
    }
    @Override
    public void onStart(){
        super.onStart();
        if(getArguments()!= null){
            String email = getArguments().getString(getString(R.string.email_key));
            Log.d("CONFIRM EMAIL", getString(R.string.email_key) + email);
            uEmail = email;
        }
    }
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            if (view.getId() == R.id.enterVerificationButton) {
                AsyncTask<String, String, String> task = new SendConfirmation();
                String code = ((EditText) v.findViewById(R.id.verificationField)).getText().toString();
                Log.d("CONFIRM",  uEmail + " " + code);
                task.execute(PARTIAL_URL, uEmail, code);
                //String confirmFrag = "Tutorial1";
                //mListener.confirmEmailFragmentInteraction(confirmFrag);
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
    public interface ConfirmEmailFragmentInteractionListener {
        // TODO: Update argument type and name
        void confirmEmailFragmentInteraction(String fragString);
    }
    private class SendConfirmation extends AsyncTask<String, String, String> {
        private final String CHECK_CODE ="checkCode.php";
        @Override
        protected String doInBackground(String... strings){

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
                Log.d("ERROR CONN", url + CHECK_CODE + email + code);
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
            if (result.equals("True")) {
                String confirmFrag = "Tutorial1";
                mListener.confirmEmailFragmentInteraction(confirmFrag);
            } else {
                Toast.makeText(getActivity(), "Incorrect Code", Toast.LENGTH_LONG).show();
            }
        }
    }
}
