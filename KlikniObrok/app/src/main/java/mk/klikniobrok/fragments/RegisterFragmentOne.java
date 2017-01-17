package mk.klikniobrok.fragments;

import android.content.Context;
import android.os.Bundle;
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

import mk.klikniobrok.R;
import mk.klikniobrok.fragments.listeners.OnFragmentChangeListener;
import mk.klikniobrok.fragments.listeners.TypefaceChangeListener;
import mk.klikniobrok.fragments.listeners.UserManagementListener;

/**
 * Created by gjorgjim on 11/27/16.
 */

public class RegisterFragmentOne extends Fragment {
    OnFragmentChangeListener fragmentChangeListener;
    UserManagementListener userManagementListener;
    TypefaceChangeListener typefaceChangeListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentChangeListener = (OnFragmentChangeListener) context;
        userManagementListener = (UserManagementListener) context;
        typefaceChangeListener = (TypefaceChangeListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_layout_one, container, false);

        final TextInputEditText firstName = (TextInputEditText) view.findViewById(R.id.registerFirstName);
        final TextInputEditText lastName = (TextInputEditText) view.findViewById(R.id.registerLastName);
        final TextInputEditText username = (TextInputEditText) view.findViewById(R.id.registerUsername);
        AppCompatTextView alreadyRegistered = (AppCompatTextView) view.findViewById(R.id.alreadyRegistered);
        final AppCompatButton nextButton = (AppCompatButton)view.findViewById(R.id.next);

        typefaceChangeListener.changeTypeface("fonts/RobotoSlab-Regular.ttf", alreadyRegistered);

        username.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    nextButton.callOnClick();
                    return true;
                }
                return false;
            }
        });

        typefaceChangeListener.changeTypeface("fonts/Exo2-ExtraLight.otf", nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!firstName.getText().toString().isEmpty()
                        && !lastName.getText().toString().isEmpty()
                        && !username.getText().toString().isEmpty()) {
                    userManagementListener.saveUserInfo(firstName.getText().toString(), lastName.getText().toString(), username.getText().toString());
                    fragmentChangeListener.onFragmentChanged(RegisterFragmentOne.class, true);
                } else {
                    if(firstName.getText().toString().isEmpty()) {
                        firstName.setError(getResources().getString(R.string.emptyField));
                    }
                    if(lastName.getText().toString().isEmpty()) {
                        lastName.setError(getResources().getString(R.string.emptyField));
                    }
                    if(username.getText().toString().isEmpty()) {
                        username.setError(getResources().getString(R.string.emptyField));
                    }
                }
            }
        });

        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentChangeListener.onFragmentChanged(RegisterFragmentOne.class, false);
            }
        });


        return view;
    }
}
