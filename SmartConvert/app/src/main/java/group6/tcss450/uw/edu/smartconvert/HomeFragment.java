package group6.tcss450.uw.edu.smartconvert;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    DataPassListener mCallback;
    public interface DataPassListener {
        public void passData(String data);
    }
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try
        {
            mCallback = ()
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.convertButton) {
            mCallback.passData()
        }
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
}
