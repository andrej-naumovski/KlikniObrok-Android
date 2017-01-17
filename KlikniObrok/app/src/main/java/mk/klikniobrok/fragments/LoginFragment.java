package mk.klikniobrok.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.icu.text.DisplayContext;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
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

public class LoginFragment extends Fragment {
    private TypefaceChangeListener typefaceChangeListener;
    private UserManagementListener userManagementListener;
    private OnFragmentChangeListener fragmentChangeListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_layout, container, false);

        final AppCompatButton login = (AppCompatButton) view.findViewById(R.id.loginButton);
        AppCompatButton fbLogin = (AppCompatButton) view.findViewById(R.id.fbLoginButton);

        AppCompatTextView register = (AppCompatTextView) view.findViewById(R.id.register);
        AppCompatTextView forgotPassword = (AppCompatTextView) view.findViewById(R.id.forgotPwText);

        typefaceChangeListener.changeTypeface("fonts/RobotoSlab-Regular.ttf", register, forgotPassword);


        final TextInputEditText username = (TextInputEditText) view.findViewById(R.id.loginUsername);
        final TextInputEditText password = (TextInputEditText) view.findViewById(R.id.loginPassword);

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    login.callOnClick();
                    return true;
                }
                return false;
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    userManagementListener.loginUser(username.getText().toString(), password.getText().toString());
                } else {
                    if(username.getText().toString().isEmpty()) {
                        username.setError(getResources().getString(R.string.emptyField));
                    }
                    if(password.getText().toString().isEmpty()) {
                        password.setError(getResources().getString(R.string.emptyField));
                    }
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentChangeListener.onFragmentChanged(LoginFragment.class, false);
            }
        });

        typefaceChangeListener.changeTypeface("fonts/Exo2-ExtraLight.otf", login, fbLogin);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        typefaceChangeListener = (TypefaceChangeListener) context;
        userManagementListener = (UserManagementListener) context;
        fragmentChangeListener = (OnFragmentChangeListener) context;
    }
}
