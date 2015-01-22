package net.vannadis.testme.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import net.vannadis.testme.R;
import net.vannadis.testme.exception.TestMeException;
import net.vannadis.testme.exception.WtfException;
import net.vannadis.testme.interfaces.RegistrationListener;
import net.vannadis.testme.model.User;

/**
 * Created by viktoriala on 9/22/2014.
 */
public class RegistrationFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Button register;
    private EditText firstName;
    private EditText lastName;
    private EditText answer;
    private RegistrationListener listener;

    public static RegistrationFragment newInstance() {
        RegistrationFragment fragment = new RegistrationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public RegistrationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.reg_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.security_questions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        firstName = (EditText) rootView.findViewById(R.id.reg_first_name);
        lastName = (EditText) rootView.findViewById(R.id.reg_last_name);
        answer = (EditText) rootView.findViewById(R.id.reg_answer);

        register = (Button) rootView.findViewById(R.id.register_btn);
        register.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof RegistrationListener) {
            listener = (RegistrationListener) activity;
        } else {
            throw new WtfException("WTF??");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       if (i == 2) { // BUG-testme: id 9
           throw new TestMeException("on Spinner 3rd item selected");
       }
    }

    private boolean validate (String value, String error) {
        if (value.isEmpty()) {
            Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        String firstNameTxt = firstName.getText().toString();
        String lastNameTxt  = lastName.getText().toString();
        String answerTxt  = answer.getText().toString();

        if (!validate(firstNameTxt, "First name cannot be empty")) {
            return;
        }

        // BUG-testme: id 10 : no lastname validation

        if (!validate(answerTxt, "Security answer cannot be empty")){
            return;
        }

        if (lastNameTxt.contains(" ")){ // BUG-testme: id 11
            throw new TestMeException("Space char in the last name");
        }

        if ((answerTxt.contains("%")) || (answerTxt.contains("&")) || (answerTxt.contains("$"))){ // BUG-testme: id 12
            Toast.makeText(getActivity(), "Unable to register, error 404 (crash is found!)", Toast.LENGTH_LONG).show();
            return;
        }

        WifiManager wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(getActivity(), "Unable to connect to server. Please turn on Wi-Fi", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(getActivity(), "You have successfully registered", Toast.LENGTH_LONG).show();
        listener.onUserRegistered(new User(firstNameTxt, lastNameTxt));

        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
