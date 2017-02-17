package mk.klikniobrok.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import mk.klikniobrok.R;
import mk.klikniobrok.fragments.listeners.TypefaceChangeListener;
import mk.klikniobrok.fragments.listeners.UserManagementListener;

/**
 * Created by gjorgjim on 2/17/17.
 */

public class FacebookLoginFragment extends Fragment {
    private TypefaceChangeListener typefaceChangeListener;
    private UserManagementListener userManagementListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fb_login_layout, container, false);

        final AppCompatButton login = (AppCompatButton) view.findViewById(R.id.loginButton);
        typefaceChangeListener.changeTypeface("fonts/Exo2-ExtraLight.otf", login);

        final TextInputEditText username = (TextInputEditText) view.findViewById(R.id.loginUsername);
        final TextInputEditText password = (TextInputEditText) view.findViewById(R.id.loginPassword);
        final TextInputEditText editPassword = (TextInputEditText) view.findViewById(R.id.fbLoginRepeatPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    userManagementListener.registerFbUser(username.getText().toString(), password.getText().toString());
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

        editPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(getActivity().getCurrentFocus().getWindowToken() != null) {
                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                        login.callOnClick();
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        typefaceChangeListener = (TypefaceChangeListener) context;
        userManagementListener = (UserManagementListener) context;
    }
}
