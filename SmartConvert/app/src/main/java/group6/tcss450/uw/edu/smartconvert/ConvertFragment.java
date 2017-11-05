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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConvertFragment.ConvertFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ConvertFragment extends Fragment implements View.OnClickListener{

    private static final String PARTIAL_URL = "http://cssgate.insttech.washington.edu/~if30/";
    private ConvertFragmentInteractionListener mListener;
    private View v;
    private String jsonResult = "hello";
    public ConvertFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_convert, container, false);
        connToDatabase();
        Button b = v.findViewById(R.id.convertCurrButton);
        b.setOnClickListener(this);
        Log.d("CONVERT",jsonResult); //Not updating yet until we click the convert button

        return v;
    }
    private void connToDatabase(){
        AsyncTask<String, String, String> task = new GetAvailableCurrencies();
        Log.d("CONVERT PAGE", "GET AVAILABLE CURRENCIES");
        task.execute(PARTIAL_URL);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ConvertFragmentInteractionListener) {
            mListener = (ConvertFragmentInteractionListener) context;
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
            if (view.getId() == R.id.convertCurrButton) {
                Log.d("CONVERT",jsonResult);
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
    public interface ConvertFragmentInteractionListener {

        void convertFragmentInteraction(String string);
    }
    public interface AsyncResponse {
        void processDone(String str);
    }
    private class GetAvailableCurrencies extends AsyncTask<String, String, String> {
        private final String GETCURRENCIES ="availableCurrencies.php";
        AsyncResponse response;
        @Override
        protected String doInBackground(String... strings){

            String response = "";
            HttpURLConnection urlConnection = null;
            String url = strings[0];
            try {
                URL urlObject = new URL(url + GETCURRENCIES);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                Log.d("ERROR CONN", url + GETCURRENCIES);
                response = "Unable to connect, Reason: " + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            Log.e("SUCCESSFUL", response);
            //jsonResult = response;
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
            jsonResult = result;
        }
    }
}
