package mk.klikniobrok.fragments;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mk.klikniobrok.R;
import mk.klikniobrok.fragments.listeners.OnFragmentChangeListener;
import mk.klikniobrok.fragments.listeners.TypefaceChangeListener;
import mk.klikniobrok.fragments.listeners.UserManagementListener;

/**
 * Created by gjorgjim on 11/27/16.
 */

public class RegisterFragmentTwo extends Fragment {
    private UserManagementListener userManagementListener;
    private OnFragmentChangeListener onFragmentChangeListener;
    private TypefaceChangeListener typefaceChangeListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_layout_two, container, false);

        final TextInputEditText password = (TextInputEditText) view.findViewById(R.id.registerPassword);
        final TextInputEditText repeatPassword = (TextInputEditText) view.findViewById(R.id.registerRepeatPassword);
        final TextInputEditText email = (TextInputEditText) view.findViewById(R.id.registerEmail);
        AppCompatTextView alreadyRegistered = (AppCompatTextView) view.findViewById(R.id.alreadyRegistered);
        final AppCompatButton registerButton = (AppCompatButton) view.findViewById(R.id.registerButton);

        typefaceChangeListener.changeTypeface("fonts/RobotoSlab-Regular.ttf", alreadyRegistered);

        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFragmentChangeListener.onFragmentChanged(RegisterFragmentTwo.class, false);
            }
        });

        repeatPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    registerButton.callOnClick();
                    return true;
                }
                return false;
            }
        });

        typefaceChangeListener.changeTypeface("fonts/Exo2-ExtraLight.otf", registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!password.getText().toString().isEmpty()
                        && !repeatPassword.getText().toString().isEmpty()
                        && !email.getText().toString().isEmpty()) {
                    if(isEmailValid(email.getText().toString()) && password.getText().toString().length() >= 6) {
                        if(password.getText().toString().equals(repeatPassword.getText().toString())) {
                            userManagementListener.registerUser(password.getText().toString(), email.getText().toString());
                            onFragmentChangeListener.onFragmentChanged(RegisterFragmentTwo.class, true);
                        } else {
                            repeatPassword.setError(getResources().getString(R.string.repeatPasswordError));
                        }
                    } else {
                        if(!isEmailValid(email.getText().toString())) {
                            email.setError(getResources().getString(R.string.notValidEmail));
                        }
                        if(password.getText().toString().length() < 6) {
                            password.setError(getResources().getString(R.string.shortPassword));
                        }
                    }
                } else {
                    if(password.getText().toString().isEmpty()) {
                        password.setError(getResources().getString(R.string.emptyField));
                    }
                    if(repeatPassword.getText().toString().isEmpty()) {
                        repeatPassword.setError(getResources().getString(R.string.emptyField));
                    }
                    if(email.getText().toString().isEmpty()) {
                        email.setError(getResources().getString(R.string.emptyField));
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userManagementListener = (UserManagementListener) context;
        onFragmentChangeListener = (OnFragmentChangeListener) context;
        typefaceChangeListener = (TypefaceChangeListener) context;
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
