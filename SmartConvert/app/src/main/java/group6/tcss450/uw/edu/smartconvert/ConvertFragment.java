package group6.tcss450.uw.edu.smartconvert;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Convert Fragment is a fragment that handles the actual converting page.
 * This is basically the main fragment that makes this app unique.
 *
 * This fragment make API calls to the amdoren.com
 *
 * @author Irene Fransiga, Donald Muffler, Josh Lau
 * @version Nov 10, 2017
 */
public class ConvertFragment extends Fragment implements View.OnClickListener{
    /**The URL to connect to the main database**/
    private static final String PARTIAL_URL = "http://cssgate.insttech.washington.edu/~if30/";
    /**The apikeyurl**/
    private final String PARTIAL_KEY_URL = "http://cssgate.insttech.washington.edu/~jtml/";
    /**The partial URL to call the Currency API**/
    private static final String API_URL = "https://www.amdoren.com/api/currency.php?api_key=";
    /**The API key to access the Currency API
     * (50 limit/month)
     * **/
    //private static final String API_KEY = "?api_key=RDyzVhu8ThK2dLnEPximNXmkGFMbNn";
    private String mApiKey = "";
    /**The API key to access the Currency API
     * (50 limit/month)
     * **/
    /*private static final String API_KEY = "?api_key=qY3C5xzDnLREziBPh8aekJ2DErsRyf";*/
    
    /**The field that will know which currency that the user wanted to convert from**/
    private String mCurrencyFrom;
    /**The field that will know which currency that the user wanted to convert to**/
    private String mCurrencyTo;
    /**The Listener to communicate with main activity class**/
    private ConvertFragmentInteractionListener mListener;
    /**A reference to the confirm email fragment**/
    private View mView;
    /**The field that refers to the amount that the user enter (FROM)**/
    private EditText mCurAEditText;
    /** the field that refers to the TEXTEDIT for the program to show the conversion result**/
    private TextView mCurBEditText;
    /** the spinner reference to the FROM available currencies**/
    private Spinner mCurASpinner;
    /** the spinner reference to the TO available currencies**/
    private Spinner mCurBSpinner;

    private String currencyCode;

    /**
     * Constructor.
     */
    public ConvertFragment() { }

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
        mView = inflater.inflate(R.layout.fragment_convert, container, false);
        mCurAEditText = mView.findViewById(R.id.NumCurrAField);
        mCurBEditText = mView.findViewById(R.id.NumCurrBField);
        mCurASpinner = mView.findViewById(R.id.CurrencyASpinner);
        mCurBSpinner = mView.findViewById(R.id.CurrencyBSpinner);

        mCurASpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String curFrom = (String) parent.getAdapter().getItem(position);
                mCurrencyFrom = curFrom;
                Toast.makeText(getActivity(), "The currency FROM is " + curFrom, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        mCurBSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String curTo = (String) parent.getAdapter().getItem(position);
                mCurrencyTo = curTo;
                Toast.makeText(getActivity(), "The currency TO is " + curTo, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        connToDatabase();
        Button b = mView.findViewById(R.id.convertCurrButton);
        b.setOnClickListener(this);

        return mView;
    }

    /**
     * A helper method to connect to an ASYNC TASK to get the string of available currencies.
     */
    private void connToDatabase(){
        AsyncTask<String, String, String> task = new GetAvailableCurrencies();
        Log.d("CONVERT PAGE", "GET AVAILABLE CURRENCIES");

        task.execute(PARTIAL_URL);
        AsyncTask<String, String, String> key = new GetApiKey();
        Log.d("CONVERT PAGE", "GET APIKEY");
        key.execute(PARTIAL_KEY_URL);
    }

    /**
     * Attaches a fragment to an activity.
     * @param context context of the current state.
     */
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

    /**
     * Detaches the fragment from the activity.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Listener for button presses.
     * @param view the button that was pressed.
     */
    @Override
    public void onClick(View view) {
        AsyncTask<String, Void, String> task = null;
        String message = "";
        if (mListener != null) {
            if (view.getId() == R.id.convertCurrButton) {
                String to_Token = "&to=";
                String from_Token ="&from=";
                String amount_Token = "&amount=";
                task = new ConvertCurrencies();
                Log.i("CONVERTING THE VALUE", API_URL + mApiKey + from_Token + mCurrencyFrom +
                        to_Token + mCurrencyTo + amount_Token + mCurAEditText.getText());
                task.execute(API_URL + mApiKey + from_Token + mCurrencyFrom + to_Token +
                        mCurrencyTo + amount_Token + mCurAEditText.getText(), message);
            }
        }
    }

    /**
     * The interface that should be implemented by main activities
     * or any activities that contain this fragment.
     */
    public interface ConvertFragmentInteractionListener {
        void convertFragmentInteraction(String string);
    }

    /**
     * This class's job is to connect to Currency API to do the necessary conversion.
     */
    private class ConvertCurrencies extends AsyncTask<String, Void, String> {

        /**
         * Gets information to convert currencies.
         * @param strings currencies to convert.
         * @return currency information.
         */
        @Override
        protected String doInBackground(String... strings) {

            String response = "";
            HttpURLConnection urlConnection = null;
            //everything that is needed for the API calls is taken care of before calling this class
            String url = strings[0];
            try {
                URL urlObject = new URL(url);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s + "\n";
                }
            } catch (Exception e) {
                Log.d("ERROR CONN", "ay");
                response = "Unable to connect, Reason: " + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            Log.e("SUCCESSFUL", "CONVERT CURRENCIES " + response);
            return response;

        }

        /**
         * Called once doInBackground ic completed. Wraps up the aSynch task.
         * @param result the result from doInBackground - a json with conversion information.
         */
        @Override
        protected void onPostExecute (String result) {
            try {
                JSONObject jObject = new JSONObject(result);
                Log.e("POST", jObject.getString("error").equals("400") + "");
                //This is needed because our API key is the free one and we are limited to 50 calls/month
                if(jObject.getString("error").equals("400")){
                    Toast.makeText(getActivity(), "API KEY has reached it's limit. Sorry", Toast.LENGTH_SHORT).show();
                } else {
                    String value = jObject.getString("amount");
                    mCurBEditText.setText(value);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * This class's job is to connect to the php file to get the available currencies to convert
     */
    private class GetAvailableCurrencies extends AsyncTask<String, String, String> {
        /**the file name to connect to. PARTIAL_URL + this file name**/
        private final String GETCURRENCIES ="availableCurrencies.php";

        private final String GETCOUNTRYCODE = "http://countryapi.gear.host/v1/Country/getCountries?pAlpha2Code=";
        //private String countryName;
        //private String countryCode;

        /**
         * Gets available currencies from database.
         * @param strings currencies to get sent to php.
         * @return currencies.
         */
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
            Log.e("SUCCESSFUL",  "GET CURRENCIES " + response);
            return response;
        }

        /**
         * Called once doInBackground ic completed. Wraps up the aSynch task.
         * @param result the result from doInBackground - currencies to convert.
         */
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONArray jObject = new JSONArray(result);
                String[] curKey = new String[jObject.length()];
                for (int i = 0; i < jObject.length(); i++) {
                    curKey[i] = jObject.getString(i).substring(2, 5);
                }
                //Creating the array for the spinners
                ArrayAdapter<String> curArray = new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_spinner_item, curKey);
                curArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mCurASpinner.setAdapter(curArray);
                mCurBSpinner.setAdapter(curArray);
                setSpinner();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void setSpinner() {
            SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.prefs), Context.MODE_PRIVATE);
            String code = preferences.getString(getString(R.string.locationCode_key), null);
            AsyncTask<String, String, String> task = null;
            task = new GetCurrencyCode();
            task.execute(code);
        }
    }
    private class GetCurrencyCode extends AsyncTask<String, String, String> {
        private final String GETCOUNTRYCODE = "http://countryapi.gear.host/v1/Country/getCountries?pAlpha2Code=";
        @Override
        protected String doInBackground(String... params) {
            String response = "";
            HttpURLConnection urlConnection = null;
            //everything that is needed for the API calls is taken care of before calling this class
            String code = params[0];
            try {
                URL urlObject = new URL(GETCOUNTRYCODE + code);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s + "\n";
                }
            } catch (Exception e) {
                Log.d("ERROR CONN", "ay");
                response = "Unable to connect, Reason: " + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            Log.e("SUCCESSFUL", "CONVERT CURRENCIES " + response);
            return response;
        }
        @Override
        protected void onPostExecute (String result) {
            try {
                JSONObject jObject = new JSONObject(result);
                JSONArray response = jObject.getJSONArray("Response");
                JSONObject respObj = response.getJSONObject(0);
                String code = respObj.getString("CurrencyCode");
                currencyCode = code;

                if (currencyCode != null) {
                    for (int i = 0; i < mCurBSpinner.getAdapter().getCount(); i++) {
                        if (mCurBSpinner.getAdapter().getItem(i).toString().equals(currencyCode)) {
                            mCurBSpinner.setSelection(i);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private class GetApiKey extends AsyncTask<String, String, String> {
        /**the file name to connect to. PARTIAL_API_URL + this file name**/
        private final String PHP ="apisql.php";

        /**
         * Connects to get api key.
         * @param strings user email and code to send to email.
         * @return boolean, confirmation code successful or not.
         */

        @Override
        protected String doInBackground(String... strings){
            //REQUIRED = PARTIAL_URL + the user email + the code

            String response = "";
            HttpURLConnection urlConnection = null;
            String url = strings[0];


            try {
                URL urlObject = new URL(url + PHP);// + email + code);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                Log.e("ERROR CONN", url + PHP);
                response = "Unable to connect, Reason: " + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }

            return response;

        }







        /**
         * Called once doInBackground ic completed. Wraps up the aSynch task.
         * @param result the result from doInBackground - a json with conversion information.
         */
        @Override
        protected void onPostExecute (String result) {
            try {
                JSONObject jObject = new JSONObject(result);

                String value = jObject.getString("api");
                mApiKey = value;

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }

    }
}
